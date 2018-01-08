package com.qhyj.dao;

import java.util.List;

import com.qhyj.domain.CustomDo;
import com.qhyj.domain.GoodsDo;

public class GoodsDao extends BaseDao{
	public void addGoods(GoodsDo goodsDo) {
		Integer gid = insert(goodsDo.getInsertSql());
		goodsDo.setGid(gid);
	}
	public int updateGoods(GoodsDo goodsDo) {
		return super.update("UPDATE T_GOODS SET GNAME=GETDATE(),GNAME='"+goodsDo.getGname()+"',SPEC='"+goodsDo.getSpec()
		     +"',PLACE='"+goodsDo.getPlace()+"',ISREBATE="+goodsDo.getIsRebate()+",MEMO='"+goodsDo.getMemo()+"' WHERE GID="+goodsDo.getGid());
	}
	public List getAllGoodsList() {
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
	
	public int deleteGoods(int gid) {
		return super.update("DELETE T_GOODS WHERE GID="+gid);
	}
	
	public static void main(String[] args) {
		GoodsDao dao = new GoodsDao();
		GoodsDo goodsDo = dao.getGoodsById(2);
		System.out.println(goodsDo.getMemo());
	}
	

}
