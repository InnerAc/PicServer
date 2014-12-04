package com.picserver.bean;

/**
 * 用户Bean
 * @author hadoop
 *
 */
public class UserBean {

	/*
	 * 主键
	 */
	String uid = "";
	
	/*
	 * attr 属性列族
	 */
	//帐号类型
	String accType = "";
	//电子邮箱
	String email = "";
	//上次登录
	String lastLogin = "";
	//网站
	String website = "";
	
	/*
	 * vldt 验证列族
	 */
	//昵称
	String nickname = "";
	//密码
	String pwd = "";
	
	/*
	 * pic 图片类族
	 */
	//图片数量
	String picNum = "";
	//图片总大小
	String totSize = "";
	
	/*
	 * space 空间列族
	 */
	//空间数量
	String spaceNum = "";
	

	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public UserBean(String uid, String accType, String email, String lastLogin,
			String website, String nickname, String pwd, String picNum,
			String totSize, String spaceNum) {
		super();
		this.uid = uid;
		this.accType = accType;
		this.email = email;
		this.lastLogin = lastLogin;
		this.website = website;
		this.nickname = nickname;
		this.pwd = pwd;
		this.picNum = picNum;
		this.totSize = totSize;
		this.spaceNum = spaceNum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getTotSize() {
		return totSize;
	}

	public void setTotSize(String totSize) {
		this.totSize = totSize;
	}

	public String getSpaceNum() {
		return spaceNum;
	}

	public void setSpaceNum(String spaceNum) {
		this.spaceNum = spaceNum;
	}
	
}
