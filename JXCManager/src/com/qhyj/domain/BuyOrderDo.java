package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;

public class BuyOrderDo extends BaseDo{
	
	private Integer boid;
	private Integer gid;
	private Date orderDate;
	private Integer count;
	private Date createtime;
	private Date lastupdateTime;

	
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


	@Override
	public String getInsertSql() {
		return "INSERT INTO T_BUY_ORDER(GID,ORDERDATE,COUNT,MEMO,CREATETIME,LASTUPDATETIME,SELLNUM)VALUES("+this.cid
				+","+this.gid+",'"+DateUtil.fmtDateToYMD(this.orderDate)+"',"+this.count+","+this.sumAmount
				+","+this.amount+",'"+(this.memo==null?"":this.memo)+"',GETDATE(),GETDATE(),'"+this.sellNum+"')";
	}

}
