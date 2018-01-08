package com.qhyj.dao;

import java.util.Date;
import java.util.List;

import com.qhyj.domain.BuyOrderDo;

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
	public void delBuyOrderByBuyNum(String buyNum) {
		update("DELETE T_Buy_ORDER WHERE BuyNUM='"+buyNum+"'");
	}
}
