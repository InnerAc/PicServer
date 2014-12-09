package com.picserver.bean;

import com.picserver.utils.DateUtil;



/**
 * 日志Bean
 * @author hadoop
 *
 */
public class LogBean {
	//主键
	String logid = "";
	
	//用户
	String user = "";
	//时间
	String time = "";
	//操作
	String operation = "";

	public LogBean() {
		super();
	}

	/**
	 * 构造方法，为了进行排序，请使用该构造方法
	 * @param user 用户id
	 * @param operation  操作
	 */
	public LogBean(String user, String operation) {
		super();
		this.user = user;
		this.operation = operation;
		try {
			this.time = DateUtil.getCurrentDateStr();
			String max = "99999999999999";
			double d1 =   Double.parseDouble(max);
			double d2 = Double.parseDouble(DateUtil.getCurrentDateStr());
			this.logid = String.valueOf(d1-d2)+user ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getLogid() {
		return logid;
	}
	
	public void setLogid(String logid) {
		this.logid = logid;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}
