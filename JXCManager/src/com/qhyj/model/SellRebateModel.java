package com.qhyj.model;

import java.math.BigDecimal;
import java.util.Map;

import com.qhyj.util.JsonTools;
import com.qhyj.util.MapUtils;

public class SellRebateModel {

	private String cname;
	private Integer gid;
	private Integer cid;
	private Integer sumCount;
	private Double sumAmount;
	private Double rebateAmount;
	
	public SellRebateModel() {
		
	}
	public SellRebateModel(int cid) {
		this.cid=cid;
	}
	
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public Integer getCid() {
		return cid;
	}
	
	public void addSumCount(int count) {
		this.sumCount=this.sumCount+count;
	}
	public void addSumAmount(double amount) {
		BigDecimal b = new BigDecimal(this.sumAmount + amount);
		this.sumAmount= b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public void addRebateAmount(double rebateAmount) {
		BigDecimal b = new BigDecimal(this.rebateAmount + rebateAmount);
		this.rebateAmount= b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Double getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getSumCount() {
		return sumCount;
	}
	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}
	
	
	public Double getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	public double getRebate(String[] expJsons,double sumamount,int sumcount) {
		Double resD=null;
		for(int i=0;i<expJsons.length;i++) {
			resD = getRebate(expJsons[i],sumamount,sumcount);
			if(null!=resD) {
				return resD;
			}
		}
		return 0d;
	}
	private Double getRebate(String expJsonStr,double sumamount,int sumcount) {
		int count = this.sumCount;
		double amount = this.sumAmount;
		Map expMap = JsonTools.parseJSON2Map(expJsonStr);
		String bjtype = MapUtils.getStringValByKey(expMap, "bjtype");
		double bjnum = MapUtils.getDoubleValByKey(expMap, "bjnum");
		String bjsymbol = MapUtils.getStringValByKey(expMap, "bjsymbol");
		String fltype =  MapUtils.getStringValByKey(expMap, "fltype");
		double flnum =  MapUtils.getDoubleValByKey(expMap, "flnum");
		if("amount".equals(bjtype)) {
			if(">".equals(bjsymbol)) {
				if(sumamount-bjnum>=0) {
					if("amount".equals(fltype)) {
						return flnum*sumCount;
					}else if("rate".equals(fltype)) {
						BigDecimal b = new BigDecimal(amount * flnum);
						return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}else if("=".equals(bjsymbol)) {
				if(sumamount-bjnum==0) {
					if("amount".equals(fltype)) {
						return flnum*sumCount;
					}else if("rate".equals(fltype)) {
						BigDecimal b = new BigDecimal(amount * flnum);
						return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}else if("<".equals(bjsymbol)) {
				if(sumamount-bjnum<=0) {
					if("amount".equals(fltype)) {
						return flnum*sumCount;
					}else if("rate".equals(fltype)) {
						BigDecimal b = new BigDecimal(amount * flnum);
						return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}
		}else if("number".equals(bjtype)) {
			if(">".equals(bjsymbol)) {
				if(sumcount-bjnum>=0) {
					if("amount".equals(fltype)) {
						return flnum*sumCount;
					}else if("rate".equals(fltype)) {
						BigDecimal b = new BigDecimal(amount * flnum);
						return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}else if("=".equals(bjsymbol)) {
				if(sumcount-bjnum==0) {
					if("amount".equals(fltype)) {	
						return flnum*sumCount;
					}else if("rate".equals(fltype)) {
						BigDecimal b = new BigDecimal(amount * flnum);
						return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}else if("<".equals(bjsymbol)) {
				if(sumcount-bjnum<=0) {
					if("amount".equals(fltype)) {
						return flnum*sumCount;
					}else if("rate".equals(fltype)) {
						BigDecimal b = new BigDecimal(amount * flnum);
						return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
				}
			}
		}
		return null;
	}
	
}
