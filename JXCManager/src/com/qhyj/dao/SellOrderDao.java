package com.qhyj.dao;

import java.util.Date;
import java.util.List;

import com.qhyj.domain.SellOrderDo;
import com.qhyj.util.DateUtil;

public class SellOrderDao extends BaseDao{

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
	public void delSellOrderBySellNum(String sellNum) {
		update("DELETE T_SELL_ORDER WHERE SELLNUM='"+sellNum+"'");
	}
}
