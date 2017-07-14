package com.gennlife.crf.bean;

/**
 * @Description: 用户登录实体类
 * @author: wangmiao
 * @Date: 2017年7月13日 下午6:29:27 
 */
public class SysOp {
	private int opId;
	private String loginName;
	private String loginPasswd;

	public SysOp() {}
	
	public SysOp(String loginName, String loginPasswd) {
		super();
		this.loginName = loginName;
		this.loginPasswd = loginPasswd;
	}


	public int getOpId() {
		return opId;
	}

	public void setOpId(int opId) {
		this.opId = opId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPasswd() {
		return loginPasswd;
	}

	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

}