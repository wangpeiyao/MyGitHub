package com.qhyj.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.qhyj.domain.BuyOrderDo;
import com.qhyj.domain.GoodsDo;

public class GoodsDao extends BaseDao{
	public void addGoods(GoodsDo goodsDo) {
		Integer gid = insert(goodsDo.getInsertSql());
		goodsDo.setGid(gid);
	}
	public int updateGoods(GoodsDo goodsDo) {
		return super.update("UPDATE T_GOODS SET lastupdatetime=GETDATE(),Amount="+goodsDo.getAmount()+",GNAME='"+goodsDo.getGname()+"',SPEC='"+goodsDo.getSpec()
		     +"',PLACE='"+goodsDo.getPlace()+"',ISREBATE="+goodsDo.getIsRebate()+",MEMO='"+goodsDo.getMemo()+"' WHERE GID="+goodsDo.getGid());
	}
	public List<GoodsDo> getAllGoodsList() {
		return findListBySql(new GoodsDo(), "SELECT * FROM T_GOODS");
	}
	public List getRebateGoodsList() {
		return findListBySql(new GoodsDo(), "SELECT * FROM T_GOODS WHERE ISREBATE=1");
	}
	public GoodsDo getGoodsById(Integer gid) {
		if(null==gid) {
			return null;
		}
	
		List list = findListBySql(new GoodsDo(), "SELECT * FROM T_GOODS WHERE GID="+gid);
		return null==list?null:(GoodsDo)list.get(0);
	}
	
	public List getGoodsListByMap(Map map) {
		return super.getListByMap("SELECT * FROM T_GOODS",map,new GoodsDo());
	}
	
	public int deleteGoods(int gid) {
		List buyList = findListBySql(new BuyOrderDo(), "SELECT * FROM T_BUY_ORDER WHERE GID="+gid);
		if(null!=buyList&&buyList.size()>0) {
			throw new RuntimeException("已有订单不可删除");
		}
		List sellList = findListBySql(new BuyOrderDo(), "SELECT * FROM T_SELL_ORDER WHERE GID="+gid);
		if(null!=sellList&&sellList.size()>0) {
			throw new RuntimeException("已有订单不可删除");
		}
		return super.update("DELETE T_GOODS WHERE GID="+gid);
	}
	
	public static void main(String[] args) {
		GoodsDao dao = new GoodsDao();
		GoodsDo goodsDo = dao.getGoodsById(2);
		System.out.println(goodsDo.getMemo());
	}
	

}
