package com.picserver.bean;

import java.io.File;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;

import com.picserver.utils.DateUtil;

public class PictureBean {
	//图片名称，也就是hbase的rowkey
	String name = "";
	//图片尺寸
	String size = "";
	//图片类型
	String type = "";
	//所属图片空间
	String space = "";
	//所属用户
	String usr = "";
	//创建时间
	String createTime = "";
	//图片所在路径
	String path="";
	
	//当前状态，三种状态分别用 ....  表示
	String status = "";
	
	//上次修改时间
	String updateTime = "";
	//访问次数
	String visitCount = "";
	//访问流量
	String visitFlow = "";
	
	public PictureBean() {
		
	}
	
	//构造方法 FileItem
	public PictureBean(FileItem item) throws Exception {
		this.name = item.getName();
		this.size = Double.toString((double) item.getSize() /1024 /1024);
		this.type = this.name.substring(this.name.lastIndexOf(".")+1);
		this.createTime = DateUtil.getCurrentDateStr();

	}
	
	//构造方法 File
	public PictureBean(File file) throws Exception {
		this.name = file.getName();
		this.size = Double.toString((double)  file.length() /1024 /1024);
		this.type = this.name.substring(this.name.lastIndexOf(".")+1);
		this.createTime = DateUtil.getCurrentDateStr();
	}
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
