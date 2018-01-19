package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.DateUtil;

public class CustomDo extends BaseDo{
	private Integer cid;
	private String cname;
	private String phonenum;
	private String idnm;
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
	
	
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getIdnm() {
		return idnm;
	}
	public void setIdnm(String idnm) {
		this.idnm = idnm;
	}
	public String getBirthday() {
//		if(idnm)
		if(null!=idnm&&idnm.length()>15) {
			String str1 = idnm.substring(10, 14);
			if(null!=str1&&str1.length()>3) {
				return str1.substring(0,2)+"月"+str1.substring(2,4)+"日";
			}
		}
		return "";
	}
	public static void main(String[] args) {
		String str = "410182199109134916";
		if(null==str||str.length()<15) {
			System.out.println("");
		}else {
			String str1 = str.substring(10, 14);
			
			System.out.println(str1.substring(0,2)+"月"+str1.substring(2,4)+"日");
		}
		
	}
	
	public String getInsertSql() {
		return "INSERT INTO T_CUSTOM(CNAME,CREATETIME,LASTUPDATETIME,PARENTID,TREENO,PHONENUM,IDNM) VALUES('"+this.getCname()+"',GETDATE(),GETDATE(),"+
	          this.getParentId()+","+this.treeno+",'"+this.phonenum+"','"+this.idnm+"')";
	}
}
