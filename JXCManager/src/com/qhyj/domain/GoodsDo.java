package com.qhyj.domain;

import java.util.Date;

public class GoodsDo extends BaseDo{
	
	private Integer gid;
	private String gname;
	private String spec;
	private String place;
	private Integer isRebate;
	private Date createTime;
	private Date lastUpdateTime;
	private String memo;
	private  Double amount;
	

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getIsRebate() {
		return isRebate;
	}

	public void setIsRebate(Integer isRebate) {
		this.isRebate = isRebate;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String getInsertSql() {
		return "INSERT INTO T_GOODS(GNAME,SPEC,PLACE,ISREBATE,CREATETIME,LASTUPDATETIME,MEMO,AMOUNT) VALUES('"+this.gname+"','"+this.spec+"','"+
	          this.place+"',"+this.isRebate+",GETDATE(),GETDATE(),'"+this.memo+"',"+this.amount+")";
	}


}
