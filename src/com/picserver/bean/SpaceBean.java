package com.picserver.bean;

import java.text.DecimalFormat;

public class SpaceBean {
	//就是hbase的rowkey,name+uid
	String key="";
	//空间名称
	String name = "";
	//空间描述
	String desc = "";
	//空间封面
	String cover = "";
	//所属用户
	String uid = "";
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	//已用容量
	String storage  = "";
	//图片数量
	String number = "";
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		double d = Double.parseDouble(storage);
		DecimalFormat df  = new DecimalFormat("######0.00");  
		this.storage = df.format(d);
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
