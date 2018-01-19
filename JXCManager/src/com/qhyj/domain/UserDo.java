package com.qhyj.domain;

import java.util.Date;

import com.qhyj.util.AESUtil;
import com.qhyj.util.LogUtil;

public class UserDo extends BaseDo{
	private Integer uid;
	private String uname;
	private String userName;
	private String pass;
	private Integer state;
	private Date createtime;
	private Date lastupdatetime;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String getInsertSql()  {
		String str = "";
		try {
			str = "INSERT INTO T_USER (uname,username,pass,state,createtime,lastupdatetime) values('"+this.uname+"','"+this.userName+"','"+AESUtil.encrypt(this.pass)+"',1,GETDATE(),GETDATE())";
		}catch(Exception e){
			LogUtil.error("用户保存失败", e);
			throw new RuntimeException("保存失败");
		}
		return str;
	}
	
	

}
