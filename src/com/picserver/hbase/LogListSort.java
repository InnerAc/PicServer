package com.picserver.hbase;

import java.util.Comparator;

import com.picserver.bean.LogBean;

/**
 * 对Log进行排序
 * @author hadoop
 *
 */
public class LogListSort implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		LogBean lb2 =  (LogBean)o1;
		LogBean lb1 =  (LogBean)o2;
		//先比较时间
		int flag = lb1.getTime().compareTo(lb2.getTime());
		//时间相同的话比较操作
		if(flag == 0){
			return lb1.getOperation().compareTo(lb2.getOperation());
		}
		return flag;
	}

}
