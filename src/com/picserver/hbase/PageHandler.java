package com.picserver.hbase;

import java.util.List;

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
	public List<PictureBean>  picPageByTime(String uid, String time, String space,int num){
		ResultScanner rs = po.picPageHandlerbyTime(time, uid, space,num);
		return lm.pictureListMapping(rs);
	}
	
	public List<PictureBean>  picPageByKey(String uid, String key, String space,int num){
		ResultScanner rs = po.picPageHandlerbyKey(key, uid,space, num);
		return lm.pictureListMapping(rs);
	}
	
}
