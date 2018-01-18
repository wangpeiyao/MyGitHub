package com.qhyj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ArrayStack;

import com.qhyj.domain.SellOrderDo;
import com.qhyj.model.SellRebateModel;
import com.qhyj.util.DateUtil;
import com.qhyj.util.MapUtils;

public class SellOrderDao extends BaseDao{
	
	public static void main(String[] args) {
	}

	public void addSellOrder(SellOrderDo sellOrderDo) {
		int id = super.insert(sellOrderDo.getInsertSql());
		sellOrderDo.setSoid(id);
	}
	public List getAllSellOrderList() {
		return findListBySql(new SellOrderDo(), "SELECT * FROM T_SELL_ORDER");
	}
	public List getSellOrderListBySellNum(String sellNum) {
		return findListBySql(new SellOrderDo(), "SELECT * FROM T_SELL_ORDER WHERE SELLNUM="+sellNum);
	}
	public String getMaxSellNum(Date orderDate) {
		return getMainTypeTableMaxId(orderDate, "T_SELL_ORDER", "XS", "SELLNUM");
	}
	private String getSellRebateSqlNoCheild(Map map) {
		StringBuffer sb = new StringBuffer();
	    sb.append(" select a.cid,a.gid,a.count,a.sumamount from t_Sell_order a  where 1=1 ");
	    if(MapUtils.existObj(map, "cid")&&!new Integer(0).equals(MapUtils.getIntegerValByKey(map, "cid"))) {//不包含下级单位
	    	sb.append(" and cid = "+MapUtils.getIntegerValByKey(map, "cid"));
	    }
	    if(MapUtils.existObj(map, "sDate")){
			sb.append(" AND orderDate >='").append(DateUtil.fmtDateToYMD((Date) map.get("sDate"))).append("'");
		}
		if(MapUtils.existObj(map, "eDate")){
			sb.append(" AND orderDate <='").append(DateUtil.fmtDateToYMD((Date) map.get("eDate"))).append("'");
		}
		return sb.toString();
	}
	private String getSellRebateSqlCheild(Map map) {
		StringBuffer sb = new StringBuffer();
	    sb.append(" select "+MapUtils.getIntegerValByKey(map, "cid")+"as cid,a.gid,a.count,a.sumamount  from t_Sell_order a  where 1=1 ");
	    if(MapUtils.existObj(map, "sDate")){
			sb.append(" AND orderDate >='").append(DateUtil.fmtDateToYMD((Date) map.get("sDate"))).append("'");
		}
		if(MapUtils.existObj(map, "eDate")){
			sb.append(" AND orderDate <='").append(DateUtil.fmtDateToYMD((Date) map.get("eDate"))).append("'");
		}
	    if(MapUtils.existObj(map, "cid")) {//包含下级单位
	    	Integer[] cids = getChildCustomSql(MapUtils.getIntegerValByKey(map, "cid"));
	    	if(cids.length>0) {
	    		sb.append(" and cid in(").append(MapUtils.getIntegerValByKey(map, "cid"));
	    		for(Integer cid:cids) {
	    			sb.append(",").append(cid);
	    		}
	    		sb.append(")");
	    	}
	    }
		return sb.toString();
	}
	private Integer[] getChildCustomSql(int cid) {
		StringBuffer sb = new StringBuffer();
		sb.append("with temp ( [cid], [parentid]) as( ");
		sb.append("select cid, parentid from T_custom where [parentid] = "+cid);
		sb.append(	"union all select a.cid, a.parentid from T_custom a inner join temp on a.[parentid] = temp.[cid])");
		sb.append(	"  select cid from temp ");
		ResultSet rs = executeSql(sb.toString());
		List resList = new ArrayList();
		try {
			while (null!=rs&&rs.next()) {
				resList.add(rs.getInt("cid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (Integer[]) resList.toArray(new Integer[resList.size()]);
	}
	/**
	 * cheildFlag=false 查本单位数据
	 * cheildFlag=true 查本单位下级单位数据
	 * @param map
	 * @param cheildFlag
	 * @return
	 */
	public List<SellRebateModel> getSellRebateList(Map map,boolean cheildFlag){
		StringBuffer sb = new StringBuffer();
//		if (MapUtils.existObj(map, "showCount")) {
//			sb.append(" select top ").append(MapUtils.existObj(map, "showCount")).append("* from (");
//		}else {
//			sb.append("select * from (");
//		}
		sb.append("select aa.cid,aa.gid,SUM(aa.count) sumcount,SUM(aa.sumamount) sumAmount from (");
	
	    
		if(cheildFlag) {//包含下级
			sb.append(getSellRebateSqlCheild(map));
		}else{
			sb.append(getSellRebateSqlNoCheild(map));
		}
		sb.append(" ) aa group by aa.cid,aa.gid ");
//	    if (new Integer(1).equals(MapUtils.getIntegerValByKey(map, "orderUnit"))) {//按数量排序
//	    	if (new Integer(1).equals(MapUtils.getIntegerValByKey(map, "orderFlag"))) {//正序
//	    		sb.append(") aaa order by aa.count desc");
//	    	}else {
//	    		sb.append(") aaa order by aa.count ");
//	    	}
//		}else if(new Integer(2).equals(MapUtils.getIntegerValByKey(map, "orderUnit"))){//按金额排序
//			if (new Integer(1).equals(MapUtils.getIntegerValByKey(map, "orderFlag"))) {//正序
//	    		sb.append(") aaa order by aa.sumamount desc");
//	    	}else {
//	    		sb.append(") aaa order by aa.sumamount ");
//	    	}
//	    }else{
//			sb.append(" ) aaa");
//		}
	    return findListBySql(new SellRebateModel(), sb.toString());
	}
	public List<SellOrderDo> getSellOrderListByMap(Map map){
		StringBuffer sb = new StringBuffer("SELECT * FROM T_SELL_ORDER WHERE 1=1 ");
		if(MapUtils.existObj(map, "sDate")){
			sb.append(" AND orderDate >='").append(DateUtil.fmtDateToYMD((Date) map.get("sDate"))).append("'");
		}
		if(MapUtils.existObj(map, "eDate")){
			sb.append(" AND orderDate <='").append(DateUtil.fmtDateToYMD((Date) map.get("eDate"))).append("'");
		}
		if(MapUtils.existObj(map, "cid")&&0!=MapUtils.getIntegerValByKey(map, "cid")){
			sb.append(" AND  CID=").append(MapUtils.getIntegerValByKey(map, "cid"));
		}
		if(MapUtils.existObj(map, "gid")&&0!=MapUtils.getIntegerValByKey(map, "gid")){
			sb.append(" AND  GID=").append(MapUtils.getIntegerValByKey(map, "gid"));
		}
		if(MapUtils.existObj(map, "sellOrderNum")){
			sb.append(" AND  sellNum='").append(MapUtils.getStringValByKey(map, "sellOrderNum")).append("'");
		}
		return findListBySql(new SellOrderDo(), sb.toString());
	}
	public void delSellOrderBySellNum(String sellNum) {
		update("DELETE T_SELL_ORDER WHERE SELLNUM='"+sellNum+"'");
	}
}
