package com.sachin.login;

import java.io.Serializable;

public class PkLoginResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LoginState loginState; // 鐧诲綍鐘舵��?
	private String ipAddress; // 杩滅▼IP鍦板�?
	private int port; // 杩滅▼绔彛
	private int sid;// 瀛︾敓id
	private String sname;// 瀛︾敓鍚嶅瓧
	private int unid;// 瀛︽牎id
	private String unName;// 瀛︽牎鍚嶇�?
	private int cid;// 鐝骇id
	private String cname;// 鐝骇鍚嶇�?
	private String sex; // 鎬у埆
	private String contextID; // 涓婁笅鏂嘔D

	public static enum LoginState {
		LoginSuccess, AccountNotExist, PasswordError, DupeLogin, AccountDisabled;
	}

	public LoginState getLoginState() {
		return loginState;
	}

	public void setLoginState(LoginState loginState) {
		this.loginState = loginState;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getContextID() {
		return contextID;
	}

	public void setContextID(String contextID) {
		this.contextID = contextID;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getUnid() {
		return unid;
	}

	public void setUnid(int unid) {
		this.unid = unid;
	}

	public String getUnName() {
		return unName;
	}

	public void setUnName(String unName) {
		this.unName = unName;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}