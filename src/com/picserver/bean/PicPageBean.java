package com.picserver.bean;

import java.util.ArrayList;
import java.util.List;

public class PicPageBean {
	String appId = "";
	//页码
	String page="";
	//是否下一页
	String ifNext="";
	//结果集
	List<PictureBean> list = new ArrayList<PictureBean>();
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getIfNext() {
		return ifNext;
	}
	public void setIfNext(String ifNext) {
		this.ifNext = ifNext;
	}
	public List<PictureBean> getList() {
		return list;
	}
	public void setList(List<PictureBean> list) {
		this.list = list;
	}
	
}
