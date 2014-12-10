package com.picserver.hbase;

import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;

public class ValueTable {
public static void main(String args[]){
	HbaseWriter writer=new HbaseWriter();
	UserBean user=new UserBean();
	user.setUid("你好");
	user.setPwd("123");
	user.setPicNum("3");
	user.setSpaceNum("3");
	user.setTotSize("3");
	writer.putUserBean(user);
	SpaceBean space=new SpaceBean();
	space.setKey("哈哈哈"+"你好");
	space.setName("哈哈哈");
	
	writer.putSpaceBean(space);
	
}
}
