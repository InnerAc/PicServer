package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import com.picserver.bean.LogBean;
import com.picserver.bean.PictureBean;

/**
 * 分页处理
 * @author hadoop
 *
 */
public class PageHandler {
	
	PageOperation po = new PageOperation();
	ListMapping lm = new ListMapping();
	/**
	 * 日志分页
	 * @param uid
	 * @param row
	 * @param num
	 * @return
	 */
	public List<LogBean> logPage(String uid, String row, int num){
		ResultScanner rs = po.logPageHandler(row,  uid, num);
		return lm.logListMapping(rs);
	}
	
	/**
	 * 日志分页
	 * @param uid
	 * @param time
	 * @param num
	 * @return
	 */
	public List<PictureBean>  picPage(String uid, String time, int num){
		ResultScanner rs = po.picPageHandler(time, uid, num);
		return lm.pictureListMapping(rs);
	}
	
}
