package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;

public class CustomDo extends BaseDo{
	private Integer cid;
	private String cname;
	private Date createtime;
	private Date lastUpdateTime;
	private Integer parentId;
	private String treeno;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getTreeno() {
		return treeno;
	}
	public void setTreeno(String treeno) {
		this.treeno = treeno;
	}
	public String getInsertSql() {
		return "INSERT INTO T_CUSTOM(CNAME,CREATETIME,LASTUPDATETIME,PARENTID,TREENO) VALUES('"+this.getCname()+"',GETDATE(),GETDATE(),"+
	          this.getParentId()+","+this.treeno+")";
	}
}
