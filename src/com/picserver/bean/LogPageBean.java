package com.picserver.bean;

import java.util.ArrayList;
import java.util.List;

public class LogPageBean {
	String appId = "";
	//页码
	String page="";
	//是否下一页
	String ifNext="";
	//Log List
	List<LogBean> list = new ArrayList<LogBean>();
	
	public String getIfNext() {
		return ifNext;
	}
	public void setIfNext(String ifNext) {
		this.ifNext = ifNext;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}

	public List<LogBean> getList() {
		return list;
	}
	public void setList(List<LogBean> list) {
		this.list = list;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
