package com.qhyj.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qhyj.domain.BuyOrderDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.util.DateUtil;
import com.qhyj.util.MapUtils;

public class BuyOrderDao extends BaseDao{

	public void addBuyOrder(BuyOrderDo buyOrderDo) {
		int id = super.insert(buyOrderDo.getInsertSql());
		buyOrderDo.setBoid(id);
	}
	public List getAllBuyOrderList() {
		return findListBySql(new BuyOrderDo(), "SELECT * FROM T_Buy_ORDER");
	}
	public List getBuyOrderListByBuyNum(String buyNum) {
		return findListBySql(new BuyOrderDo(), "SELECT * FROM T_Buy_ORDER WHERE BuyNUM="+buyNum);
	}
	public String getMaxBuyNum(Date orderDate) {
		return getMainTypeTableMaxId(orderDate, "T_Buy_ORDER", "JH", "BuyNUM");
	}
	public List<BuyOrderDo> getBuyOrderListByMap(Map map){
		StringBuffer sb = new StringBuffer("SELECT * FROM T_Buy_ORDER WHERE 1=1 ");
		if(MapUtils.existObj(map, "sDate")){
			sb.append(" AND orderDate >='").append(DateUtil.fmtDateToYMD((Date) map.get("sDate"))).append("'");
		}
		if(MapUtils.existObj(map, "eDate")){
			sb.append(" AND orderDate <='").append(DateUtil.fmtDateToYMD((Date) map.get("eDate"))).append("'");
		}
		if(MapUtils.existObj(map, "buyOrderNum")){
			sb.append(" AND  buyOrderNum='").append(MapUtils.getStringValByKey(map, "buyOrderNum")).append("'");
		}
		if(MapUtils.existObj(map, "gid")&&0!=MapUtils.getIntegerValByKey(map, "gid")){
			sb.append(" AND  gid=").append(MapUtils.getIntegerValByKey(map, "gid"));
		}
		return findListBySql(new BuyOrderDo(),sb.toString());
	}
	public void delBuyOrderByBuyNum(String buyNum) {
		update("DELETE T_Buy_ORDER WHERE BuyNUM='"+buyNum+"'");
	}
}
