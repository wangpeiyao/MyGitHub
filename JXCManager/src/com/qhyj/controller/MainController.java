package com.qhyj.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qhyj.dao.BaseDao;
import com.qhyj.dao.BuyOrderDao;
import com.qhyj.dao.CustomDao;
import com.qhyj.dao.GoodsDao;
import com.qhyj.dao.RebateDao;
import com.qhyj.dao.SellOrderDao;
import com.qhyj.dao.StockDao;
import com.qhyj.dao.UserDao;
import com.qhyj.domain.BuyOrderDo;
import com.qhyj.domain.CustomDo;
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.RebateDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.domain.StockDo;
import com.qhyj.domain.UserDo;
import com.qhyj.util.DateUtil;
import com.qhyj.util.LogUtil;

public class MainController {
	
	private static MainController instance = null;
	
	private UserDao userDao;
	
	private CustomDao customDao;
	
	private GoodsDao goodsDao;
	
	private SellOrderDao sellOrderDao;
	
	private RebateDao rebateDao;
	
	private StockDao stockDao;
	
	private BuyOrderDao buyOrderDao;
	
	public static MainController getInstance()  {
		if(null==instance) {
			try {
				instance = new MainController().getClass().newInstance();
				instance.setUserDao(new UserDao());
				instance.setCustomDao(new CustomDao());
				instance.setGoodsDao(new GoodsDao());
				instance.setRebateDao(new RebateDao());
				instance.setStockDao(new StockDao());
				instance.setSellOrderDao(new SellOrderDao());
				instance.setBuyOrderDao(new BuyOrderDao());
			} catch (InstantiationException e) {
				System.out.println("MainController初始化失败：");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("MainController初始化失败：");
				e.printStackTrace();
			}
		}
		return instance;
	}
	public Object selfTransaction(String methodName,Object obj){
		Object resObj =null;
		try {
			BaseDao.conn.setAutoCommit(false);
			if(obj instanceof ArrayList) {
				resObj = this.getClass().getMethod(methodName,List.class).invoke(this, obj);

			}
			BaseDao.conn.commit();
		}catch (Exception e) {
			try {
				BaseDao.conn.rollback();
			} catch (SQLException e1) {
				LogUtil.error("事务回滚失败",e1);
			}
			LogUtil.error("事务回滚成功",e);
			throw new RuntimeException("执行失败！");
		}
		return resObj;
	}
	
	public void addRebateList(List<RebateDo> list) {
		rebateDao.deleRebateDoByGid(list.get(0).getGid());
		for (RebateDo rebateDo : list) {
			rebateDao.addRebate(rebateDo);
		}
	}
	public void deleRebateDoByRid(int rid) {
		rebateDao.deleRebateDoByRid(rid);
	}
	public List<RebateDo> getRebateDoByGId(int gid) {
		return rebateDao.getRebateByGid(gid);
	}
	public static void main(String[] args) {
		List list = new ArrayList();
		RebateDo do1 = new RebateDo();
		do1.setGid(2);
		do1.setExpression("${amount}>1000${admoun}=11");
		list.add(do1);
		RebateDo do2 = new RebateDo();
		do2.setGid(2);
		do2.setExpression("11");
		list.add(do2);
		MainController.getInstance().selfTransaction("addRebateList",list);
		System.out.println("执行成功");
		
	}
	
	public UserDo getUser(String userName,String passWord) {
		return userDao.getUser(userName, passWord);
	}
	
	public List getCustomListByPrtId(Integer parentId) {
		return customDao.getCustomListByPrtId(parentId);
	}
	public void addCustome(CustomDo customDo) {
		customDao.addCustom(customDo);
	}
	public void deleteCustomeById(Integer cid){
		//先校验是否有销售单
		//TODO
		customDao.deleteCustom(cid);
	}
	public void updateCustomeName(Integer cid,String cname) {
		customDao.updateCustomName(cid,cname);
	}
	
	public List getAllCustomList() {
		return customDao.getAllCustomList();
	}
	public List getGoodsListByMap(Map map) {
		return goodsDao.getGoodsListByMap(map);
	}
	public void addGoods(GoodsDo goodsDo) {
		goodsDao.addGoods(goodsDo);
	}
	public int updateGoods(GoodsDo goodsDo) {
		return goodsDao.updateGoods(goodsDo);
	}
	public int deleteGoods(int gid) {
		return goodsDao.deleteGoods(gid);
	}
	/**
	 * 只取有库存的商品
	 * @return
	 */
	public List getHavStockGoodsList() {
		List<GoodsDo> list =  goodsDao.getAllGoodsList();
		for(GoodsDo goodsDo:list) {
			Integer lavenum = getCurrStockByGid(goodsDo.getGid());
			if(lavenum<1) {
				list.remove(goodsDo);
			}
		}
		return list;
	}
	public int getCurrStockByGid(int gid) {
		return stockDao.getCurrStockByGid(gid);
	}
	public String getSellMainMaxId(Date date) {
		return sellOrderDao.getMaxSellNum(date);
	}
	public String getBuyMainMaxId(Date date) {
		return buyOrderDao.getMaxBuyNum(date);
	}
	
	public List getAllGoodsList() {
		return goodsDao.getAllGoodsList();
	}
	
	public List getRebateGoodsList() {
		return goodsDao.getRebateGoodsList();
	}
	
	
	public GoodsDo getGoodsById(Integer gid) {
		return goodsDao.getGoodsById(gid);
	}
	
	public List getAllSellOrderList() {
		return sellOrderDao.getAllSellOrderList();
	}
	public List getAllBuyOrderList() {
		return buyOrderDao.getAllBuyOrderList();
	}
	public void addSellOrderList(List<SellOrderDo> list) {
		String sellNum = list.get(0).getSellNum();
		Date orderDate =list.get(0).getOrderDate();
		if(!sellNum.startsWith("XS"+DateUtil.fmtDateToYyyyMMDD(orderDate))) {
			throw new RuntimeException("销售单与销售日期不匹配");
		}
		sellOrderDao.delSellOrderBySellNum(sellNum);
		for(SellOrderDo sellOrderDo:list) {
			sellOrderDao.addSellOrder(sellOrderDo);
			StockDo stockDo = new StockDo();
			stockDo.setGid(sellOrderDo.getGid());
			stockDo.setLavenum(sellOrderDo.getCount());
			stockDo.setSdate(sellOrderDo.getOrderDate());
			stockDao.addOrUpdateStock(stockDo,2);
		}
	}
	public void addBuyOrderList(List<BuyOrderDo> list) {
		String buyNum = list.get(0).getBuyNum();
		Date orderDate =list.get(0).getOrderDate();
		if(!buyNum.startsWith("JH"+DateUtil.fmtDateToYyyyMMDD(orderDate))) {
			throw new RuntimeException("销售单与销售日期不匹配");
		}
		buyOrderDao.delBuyOrderByBuyNum(buyNum);
		for(BuyOrderDo buyOrderDo:list) {
			buyOrderDao.addBuyOrder(buyOrderDo);
			StockDo stockDo = new StockDo();
			stockDo.setGid(buyOrderDo.getGid());
			stockDo.setLavenum(buyOrderDo.getCount());
			stockDo.setSdate(buyOrderDo.getOrderDate());
			stockDao.addOrUpdateStock(stockDo,1);
		}
	}
	
	public ResultSet executeSql(String sql) {
		return customDao.executeSql(sql);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCustomDao(CustomDao customDao) {
		this.customDao = customDao;
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setSellOrderDao(SellOrderDao sellOrderDao) {
		this.sellOrderDao = sellOrderDao;
	}
	public void setRebateDao(RebateDao rebateDao) {
		this.rebateDao = rebateDao;
	}
	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}
	public void setBuyOrderDao(BuyOrderDao buyOrderDao) {
		this.buyOrderDao = buyOrderDao;
	}

	

}
