package com.qhyj.dao;

import java.util.Date;
import java.util.List;

import com.qhyj.domain.CustomDo;
import com.qhyj.domain.StockDo;
import com.qhyj.util.DateUtil;

public class StockDao extends BaseDao{

	/**
	 * 更新或插入库存 flag：1增加 flag:2减少
	 * @param stockDo
	 * @param flag
	 */
	public void addOrUpdateStock(StockDo stockDo,int flag) {
		if(null==stockDo||null==stockDo.getGid()||null==stockDo.getSdate()) {
			return ;
		}
		StockDo stockDo1 = getStockByGidDate(stockDo.getGid(),stockDo.getSdate());
		if(null==stockDo1) {
			int currStock = getCurrStockByGid(stockDo.getGid());
			if(1==flag) {
				stockDo.setLavenum(currStock+stockDo.getLavenum());
			}else if(2==flag) {
				if(stockDo1.getLavenum()<stockDo.getLavenum()) {
					throw new RuntimeException("库存不够");
				}
				stockDo.setLavenum(currStock-stockDo.getLavenum());
			}
			Integer id = super.insert(stockDo.getInsertSql());
			stockDo.setSid(id);
		}else {
			if(1==flag) {
				stockDo1.setLavenum(stockDo1.getLavenum()+stockDo.getLavenum());
			}else if(2==flag) {
				if(stockDo1.getLavenum()<stockDo.getLavenum()) {
					throw new RuntimeException("库存不够");
				}
				stockDo1.setLavenum(stockDo1.getLavenum()-stockDo.getLavenum());
			}
		
			updateStockDoByGidDate(stockDo1);
			stockDo.setSid(stockDo1.getSid());
		}
	}
	public StockDo getStockByGidDate(int gid,Date sDate) {
		List list = findListBySql(new StockDo(), "SELECT * FROM T_STOCK WHERE GID="+gid+" AND SDATE='"+DateUtil.fmtDateToYMD(sDate)+"'");
		return null==list||list.size()<1?null:(StockDo) list.get(0);
	}
	public Integer getCurrStockByGid(int gid) {
		List<StockDo> list = findListBySql(new StockDo(), "SELECT * FROM T_STOCK WHERE GID="+gid+" ORDER BY SDATE DESC");
		if(null==list||list.size()<1) {
			return 0;
		}
		return list.get(0).getLavenum();
	}
	public void updateStockDoByGidDate(StockDo stockDo) {
		if(null==stockDo||null==stockDo.getGid()||null==stockDo.getSdate()) {
			return ;
		}
		update("UPDATE T_STOCK SET LAVENUM="+stockDo.getLavenum()+" WHERE GID="+stockDo.getGid()+" AND SDATE='"+DateUtil.fmtDateToYMD(stockDo.getSdate())+"'");
	}
}
