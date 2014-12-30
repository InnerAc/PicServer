package com.picserver.bean;

public class MapfileBean {
	//主键，用时间加用户表示
	String key="";
	//名称
	String name = "";
	//用户
	String uid = "";

	//总标记数
	String  flagNum = "";
	//总图片数
	String picNum = "";
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFlagNum() {
		return flagNum;
	}
	public void setFlagNum(String flagNum) {
		this.flagNum = flagNum;
	}
	public String getPicNum() {
		return picNum;
	}
	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}
	
	
}
