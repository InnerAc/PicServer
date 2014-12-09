package com.picserver.bean;

import java.util.ArrayList;
import java.util.List;

public class LogPageBean {
	//页码
	String page="";
	//起始rowkey
	String row="";
	//Log List
	List<LogBean> list = new ArrayList<LogBean>();
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public List<LogBean> getList() {
		return list;
	}
	public void setList(List<LogBean> list) {
		this.list = list;
	}
	
}
