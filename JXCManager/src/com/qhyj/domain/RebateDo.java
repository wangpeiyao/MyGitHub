package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;

public class RebateDo extends BaseDo{
	
	private Integer rid;
	private Integer gid;
	private String expression;
	private Date sdate;
	private Date edate;
	
	

	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	@Override
	public String getInsertSql() {
		
		return "INSERT INTO T_REBATE (GID,EXPRESSION,SDATE,EDATE) VALUES("
				+this.gid+",'"+this.expression+"','"+DateUtil.fmtDateToYMD(this.sdate)
				+"','"+DateUtil.fmtDateToYMD(this.edate)+"')";
	}

}
