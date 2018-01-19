package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;

public class StockDo extends BaseDo{

	private Integer sid;
	private Integer gid;
	private Integer lavenum;
	private Date sdate;
	
	
	public Integer getSid() {
		return sid;
	}


	public void setSid(Integer sid) {
		this.sid = sid;
	}


	public Integer getGid() {
		return gid;
	}


	public void setGid(Integer gid) {
		this.gid = gid;
	}


	public Integer getLavenum() {
		return lavenum;
	}


	public void setLavenum(Integer lavenum) {
		this.lavenum = lavenum;
	}


	public Date getSdate() {
		return sdate;
	}


	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}


	@Override
	public String getInsertSql() {
		return "INSERT INTO T_STOCK(GID,LAVENUM,SDATE) VALUES("+this.gid+","+this.lavenum+",'"+DateUtil.fmtDateToYMD(this.sdate)+"')";
	}

}
