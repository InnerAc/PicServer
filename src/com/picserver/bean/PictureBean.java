package com.picserver.bean;

public class PictureBean {
	//图片名称，也就是hbase的rowkey
	String name = null;
	//图片尺寸
	String size = null;
	//图片类型
	String type = null;
	//所属图片空间
	String space = null;
	//所属用户
	String usr = null;
	//创建时间
	String createTime = null;
	

	//是否已经上传到云端
	String isCloud = null;
	//上次修改时间
	String updateTime = null;
	//访问次数
	String visitCount = null;
	//访问流量
	String visitFlow = null;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsCloud() {
		return isCloud;
	}
	public void setIsCloud(String isCloud) {
		this.isCloud = isCloud;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}
	public String getVisitFlow() {
		return visitFlow;
	}
	public void setVisitFlow(String visitFlow) {
		this.visitFlow = visitFlow;
	}
	
}
