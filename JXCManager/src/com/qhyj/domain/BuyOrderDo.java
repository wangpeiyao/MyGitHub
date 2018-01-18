package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;

public class BuyOrderDo extends BaseDo{
	
	private Integer boid;
	private Integer gid;
	private Date orderDate;
	private Integer count;
	private Double amount;
	private Double sumAmount;
	private String memo;
	private Date createtime;
	private Date lastupdateTime;
	private String buyNum;

	
	public Integer getBoid() {
		return boid;
	}


	public void setBoid(Integer boid) {
		this.boid = boid;
	}


	public Integer getGid() {
		return gid;
	}


	public void setGid(Integer gid) {
		this.gid = gid;
	}


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Date getLastupdateTime() {
		return lastupdateTime;
	}


	public void setLastupdateTime(Date lastupdateTime) {
		this.lastupdateTime = lastupdateTime;
	}
	

	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getBuyNum() {
		return buyNum;
	}


	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Double getSumAmount() {
		return sumAmount;
	}


	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}


	@Override
	public String getInsertSql() {
		return "INSERT INTO T_BUY_ORDER(GID,ORDERDATE,COUNT,AMOUNT,SUMAMOUNT,MEMO,CREATETIME,LASTUPDATETIME,BUYNUM)VALUES("+this.gid
				+",'"+DateUtil.fmtDateToYMD(this.orderDate)+"',"+this.count+","+this.amount+","+this.sumAmount+",'"+this.memo+"',GETDATE(),GETDATE(),'"+this.buyNum+"')";
	}

}
