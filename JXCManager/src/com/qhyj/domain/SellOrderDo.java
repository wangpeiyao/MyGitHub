package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;
import com.qhyj.util.StringUtil;

public class SellOrderDo extends BaseDo{

	private Integer soid;
	private Integer cid;
	private Integer gid;
	private String sellNum;
	private Date orderDate;
	private Integer count;
	private Double amount;
	private Double sumAmount;
	private String memo;
	private Date createTime;
	private Date lastUpdateTime;
	private Integer payState;
	public Integer getSoid() {
		return soid;
	}
	public void setSoid(Integer soid) {
		this.soid = soid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
	public Double getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}
	
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	@Override
	public String getInsertSql() {
		return "INSERT INTO T_SELL_ORDER(CID,GID,ORDERDATE,COUNT,SUMAMOUNT,AMOUNT,"
				+ "MEMO,CREATETIME,LASTUPDATETIME,SELLNUM,PAYSTATE)VALUES("+this.cid
				+","+this.gid+",'"+DateUtil.fmtDateToYMD(this.orderDate)+"',"+this.count+","+this.sumAmount
				+","+this.amount+",'"+(this.memo==null?"":this.memo)+"',GETDATE(),GETDATE(),'"+this.sellNum+"',"+this.payState+")";
	}
	
	
}
