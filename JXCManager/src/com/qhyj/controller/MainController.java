package com.qhyj.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.qhyj.model.SellRebateModel;
import com.qhyj.util.DateUtil;
import com.qhyj.util.LogUtil;
import com.qhyj.util.MapUtils;

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
		Map<String,SellRebateModel> map = new HashMap();
		map.put("1", new SellRebateModel(2));
		map.put("2", new SellRebateModel(4));
		map.put("3", new SellRebateModel(5));
		map.put("6", new SellRebateModel(8));
		map.put("5", new SellRebateModel(6));
		List<SellRebateModel> list= (List<SellRebateModel>)map.entrySet().stream().map(et ->et.getValue()).collect(Collectors.toList());
		list.sort(Comparator.comparingInt(SellRebateModel::getCid));
		list.forEach(obj-> System.out.println(obj.getCid()));
	}
	
	public List<UserDo> getAllUserList(){
		return userDao.getAllUserList();
	}
	public void addUser(UserDo userDo) {
		userDao.addUser(userDo);
	}
	public UserDo getUser(String userName,String passWord) {
		return userDao.getUser(userName, passWord);
	}
	public UserDo getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}
	
	public void updateUser(UserDo userDo) {
		userDao.updateUser(userDo);
	}
	public void delUserByUname(String uname) {
		userDao.deleteUserByUname(uname);
	}
	public CustomDo getCustomById(int cid) {
		return customDao.getCustom(cid);
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
		List<GoodsDo> resList = new ArrayList();
		for(GoodsDo goodsDo:list) {
			Integer lavenum = getCurrStockByGid(goodsDo.getGid());
			if(lavenum>0) {
				resList.add(goodsDo);
			}
		}
		return resList;
	}
	public int getCurrStockByGid(int gid) {
		return stockDao.getCurrStockByGid(gid);
	}
	public String getSellMainMaxId(Date date) {
		return sellOrderDao.getMaxSellNum(date);
	}
	public List<SellOrderDo> getSellOrderListByMap(Map map){
		return sellOrderDao.getSellOrderListByMap(map);
	}
	public List<BuyOrderDo> getBuyOrderListByMap(Map map){
		return buyOrderDao.getBuyOrderListByMap(map);
	}
	public String getBuyMainMaxId(Date date) {
		return buyOrderDao.getMaxBuyNum(date);
	}
	
	public List getSellRebateListByMap(Map map) {
		List<SellRebateModel> customList = null;
		//取出每个客户每类产品买多少
		if(new Integer(1).equals(MapUtils.getIntegerValByKey(map, "isInclude"))) {//包含下级
			customList = sellOrderDao.getSellRebateList(map,false);
			if(null==customList) {
				return null;
			}
			List<SellRebateModel> includeChildCustomList = new ArrayList();
			for(int i=0;i<customList.size();i++) {
				map.put("cid", customList.get(i).getCid());
				includeChildCustomList.addAll(sellOrderDao.getSellRebateList(map,true));
			}
			customList.clear();
			customList = includeChildCustomList;
		}else {
			customList = sellOrderDao.getSellRebateList(map,false);
		}
		
		if(null==customList) {
			return null;
		}
		
		//计算每个客户每类商品返利
		Map<String,SellRebateModel> modelMap = new HashMap();
		Map expMap = new HashMap();
		for(SellRebateModel model:customList) {
			String[] exps = null;
			int gid = model.getGid();
			int cid = model.getCid();
			if(MapUtils.existObj(expMap, String.valueOf(gid))) {
				exps = (String[]) expMap.get(String.valueOf(gid));
			}else {
				exps = rebateDao.getExpsByGid(gid);
				expMap.put(String.valueOf(gid), exps);
			}
			double rebate = model.getRebate(exps);
			model.setRebateAmount(rebate);
			//汇总每个客户所有的商品
			CustomDo customDo = customDao.getCustom(cid);
			model.setCname(customDo.getCname());
			if(MapUtils.existObj(modelMap, String.valueOf(gid))) {
				SellRebateModel model1 = (SellRebateModel) modelMap.get(String.valueOf(cid));
				model1.addSumAmount(model.getSumAmount());
				model1.addSumCount(model.getSumCount());
				model1.addRebateAmount(model.getRebateAmount());
			}else {
				modelMap.put(String.valueOf(cid), model);
			}
		}
		List<SellRebateModel> sortList= (List<SellRebateModel>)modelMap.entrySet().stream().map(et->et.getValue()).collect(Collectors.toList());
		
		//根据单位排序
		if (new Integer(1).equals(MapUtils.getIntegerValByKey(map, "orderUnit"))) {// 按数量排序
			if (new Integer(1).equals(MapUtils.getIntegerValByKey(map, "orderFlag"))) {// 正序
				sortList.sort(Comparator.comparingDouble(SellRebateModel::getSumCount).reversed());
			} else {
				sortList.sort(Comparator.comparingDouble(SellRebateModel::getSumCount));
			}
		} else if (new Integer(2).equals(MapUtils.getIntegerValByKey(map, "orderUnit"))) {// 按金额排序
			if (new Integer(1).equals(MapUtils.getIntegerValByKey(map, "orderFlag"))) {// 正序
				sortList.sort(Comparator.comparingDouble(SellRebateModel::getSumAmount).reversed());
			} else {
				sortList.sort(Comparator.comparingDouble(SellRebateModel::getSumAmount));
			}
		} else {
			sortList.sort(Comparator.comparingDouble(SellRebateModel::getCid));
		}
		//取前多少名
		if(MapUtils.existObj(map, "showCount")) {
			int showCount = MapUtils.getIntegerValByKey(map, "showCount");
			if(showCount<sortList.size()) {
				List<SellRebateModel>  resList = sortList.subList(0,MapUtils.getIntegerValByKey(map, "showCount") ); 
				return resList;
			}
		}
		
		return sortList;
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
		if(!sellNum.startsWith("XS"+DateUtil.fmtDateToYyyyMMDD(orderDate))
				||sellNum.length()!=11) {
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
		if(!buyNum.startsWith("JH"+DateUtil.fmtDateToYyyyMMDD(orderDate))
				||buyNum.length()!=11) {
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
