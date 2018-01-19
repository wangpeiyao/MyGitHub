package com.qhyj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.qhyj.domain.CustomDo;

public class CustomDao extends BaseDao{
	
	public CustomDo getCustom(int cid) {
		List list = findListBySql(new CustomDo(), "SELECT * FROM T_CUSTOM WHERE CID="+cid);
		return null==list?null:(CustomDo) list.get(0);
	}

	public List getCustomListByPrtId(Integer parentId) {
		String sql = null;
		if(null==parentId) {
			sql =  "SELECT * FROM T_CUSTOM WHERE PARENTID IS NULL OR PARENTID=0";
		}else{
			sql = "SELECT * FROM T_CUSTOM WHERE PARENTID="+parentId;
		}
		return findListBySql(new CustomDo(), sql);
	}
	public List getAllCustomList() {
		return findListBySql(new CustomDo(), "SELECT * FROM T_CUSTOM");
	}
	public void deleteCustom(Integer cid) {
		update("DELETE T_CUSTOM where CID="+cid);
	}
	public void updateCustomName(CustomDo customDo) {
		update("UPDATE T_CUSTOM SET LASTUPDATETIME=GETDATE(),PHONENUM='"+customDo.getPhonenum()+"',IDNM='"+customDo.getIdnm()+"',CNAME='"+customDo.getCname()+"' WHERE CID="+customDo.getCid());
	}
	public ResultSet executeSql(String sql) {
		return super.executeSql(sql);
	}
	public List<CustomDo> getCustomListBirthday(){
		String sql = "SELECT * FROM T_custom WHERE  LEN(idnm)>15 AND idnm IS NOT NULL AND \r\n" + 
				"convert(DATETIME,DateName(year,GetDate())+SUBSTRING(idnm, 11, 4), 112)-GETDATE()<3 ";
		return findListBySql(new CustomDo(), sql);
	}
	
	public boolean checkCustomByName(String name) {
		List list = findListBySql(new CustomDo(), "SELECT * FROM T_CUSTOM WHERE CNAME='"+name+"'");
		if(null!=list&&list.size()>0) {
			return true;
		}
		return false;
	}
	
	public void addCustom(CustomDo customDo) {
		
		Integer id = insert(customDo.getInsertSql());
		customDo.setCid(id);
	}
	public static void main(String[] args) {
		CustomDao dao = new CustomDao();
		List list = dao.getCustomListByPrtId(null);
		for(int i=0;i<list.size();i++) {
			CustomDo customDo = (CustomDo) list.get(i);
			System.out.println(customDo.getCname());
		}
	}
}
