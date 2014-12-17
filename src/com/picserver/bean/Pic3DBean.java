package com.picserver.bean;

/**
 * 3D图片的Bean
 * @author hadoop
 *
 */
public class Pic3DBean {
	//主键
	String key="";
	//图片名称
	String name = "";

	//所属用户
	String uid = "";
	//上传时间
	String createTime = "";
	//图片大小
	String size = "";
	//图片数量
	String num="";
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
