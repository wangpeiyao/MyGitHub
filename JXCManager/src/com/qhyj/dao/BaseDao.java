package com.qhyj.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.qhyj.domain.BaseDo;
import com.qhyj.util.DateUtil;

public abstract class BaseDao {
	protected static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	protected static String dbUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=JXCDB";
	protected static String dbUser = "sa";
	protected static String dbPwd = "11111111";
	public static Connection conn = null;
	static {
		try {
			if (conn == null) {
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
				if(conn!=null) {
					System.out.println("数据库连接成功");
				}else {
					throw new RuntimeException("数据库连接失败");
				}
			}
		} catch (Exception ee) {
			throw new RuntimeException(ee);
		}
	}
	protected Connection getConn() {
		return conn;
	}
	protected ResultSet executeSql(String sql) {
		ResultSet rs = null;
		List list = new ArrayList();
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return rs;
	}
	/**
	 * 获取更类主表最大ID
	 * @param date
	 * @param table
	 * @param idChar
	 * @param idName
	 * @return
	 */
	protected String getMainTypeTableMaxId(Date date, String table,
			String idChar, String idName) {
		String dateStr = DateUtil.fmtDateToYyyyMMDD(date);
		String id = idChar + dateStr;
		String sql = "select max(" + idName + ") from " + table + " where "
				+ idName + " like '" + id + "%'";
		ResultSet set = executeSql(sql);
		String baseId = null;
		try {
			if (set.next()) {
				if(null!=set.getString(1)) {
					baseId = set.getString(1).trim();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		baseId = baseId == null ? "000" : baseId.substring(baseId.length() - 3);
		int idNum = Integer.parseInt(baseId) + 1;
		id += String.format("%03d", idNum);
		return id;
	}
	public static void main(String[] args) {
		BuyOrderDao dao = new BuyOrderDao();
		System.out.println(dao.getMainTypeTableMaxId(new Date(),"T_BUY_ORDER","SH","buynum"));
		
	}
	public List getListByMap(String sql,Map map,BaseDo baseDo) {
		StringBuffer sb  = new StringBuffer(sql);
		sb.append(" WHERE 1=1");
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			if(val instanceof String&& ((String) val).indexOf("name")>-1) {
				sb.append(" AND ").append(entry.getKey()).append(" like '%").append(entry.getValue()).append("%'");
			}else if(val instanceof String) {
				sb.append(" AND ").append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
			}else if(val instanceof Date) {
				sb.append(" AND ").append(entry.getKey()).append(" = '").append(DateUtil.fmtDateToYMD((Date)entry.getValue())).append("'");
			}else {
				sb.append(" AND ").append(entry.getKey()).append(" = ").append(entry.getValue());
			}
		}
		return findListBySql(baseDo, sb.toString());
	}
	protected Integer insert(String sql){
		boolean result = false;
		Integer id = null;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.executeUpdate();
			ResultSet results = psmt.getGeneratedKeys();
			if (results.next()) {
				id = results.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return id;
	}
	public static int update(String sql) {
		int result = 0;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	protected List findListBySql(Object obj,String sql) {
		if (conn == null)
			return null;
		ResultSet rs = null;
		List list = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			
			while (null!=rs&&rs.next()) {
				Object newObj = obj.getClass().newInstance();
				Field[] fields = obj.getClass().getDeclaredFields();
				Object value = null;
				Method m = null;
				if(null==list) {
					list = new ArrayList();
				}
				for(Field field :fields) {
					String name = field.getName(); // 获取属性的名字
		            name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
				    String type = field.getGenericType().toString(); 
				    try {
					    if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
					    	value = rs.getString(name);
		                    if (value != null) {
		                    	m = newObj.getClass().getMethod("set"+name,String.class);
		                        m.invoke(newObj, value);
		                    }
		                }else if (type.equals("class java.lang.Integer")) {
		                	value = rs.getInt(name);
		                    if (value != null) {
		                        m = newObj.getClass().getMethod("set"+name,Integer.class);
		                        m.invoke(newObj, value);
		                    }
		                }else if (type.equals("class java.lang.Double")) {
		                	value = rs.getDouble(name);
		                    if (value != null) {
		                        m = newObj.getClass().getMethod("set"+name,Double.class);
		                        m.invoke(newObj, value);
		                    }
		                }else if (type.equals("class java.util.Date")) {
		                	value = rs.getTimestamp(name);
		                    if (value != null) {
		                        m = newObj.getClass().getMethod("set"+name,Date.class);
		                        m.invoke(newObj, value);
		                    }
		                }
				    }catch(SQLServerException e) {
				    	if(!((e.getMessage().indexOf("列名")>-1)
				    			&&(e.getMessage().indexOf("无效")>-1))) {
				    		throw e;
				    	}
				    }
				}
				list.add(newObj);
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
