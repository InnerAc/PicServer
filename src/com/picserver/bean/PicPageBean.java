package com.picserver.bean;

import java.util.ArrayList;
import java.util.List;

public class PicPageBean {
	//页码
	String page="";
	//起始row
	String row="";
	//结果集
	List<PictureBean> list = new ArrayList<PictureBean>();
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
	public List<PictureBean> getList() {
		return list;
	}
	public void setList(List<PictureBean> list) {
		this.list = list;
	}
	
}
