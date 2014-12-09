package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

import com.picserver.bean.LogBean;

public class PageHandler {
	
	HbaseConf hbaseConf = new HbaseConf();
	Configuration configuration = hbaseConf.hbaseConf();
	
	public List<LogBean> LogPage(String uid, String row, int num){
		List<LogBean> list = new ArrayList<LogBean>();
		ResultScanner rs = pageHandler("cloud_log", "time", row, "user", uid, num);
		for (Result r : rs) {
			LogBean lb = new LogBean();
			lb.setLogid(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("user")) {
					lb.setUser(val);
				}
				if (v.equals("time")) {
					lb.setTime(val);
				}
				if (v.equals("operation")) {
					lb.setOperation(val);
				}
			}
			list.add(lb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	private ResultScanner pageHandler(String table, String coloum1, String rowkey, String coloum2, String uid, int num){
		 try { 
	            HTablePool pool = new HTablePool(configuration, 1000); 
	            List<Filter> filters = new ArrayList<Filter>(); 
	 
	            Filter filter1 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes(coloum1), 
	                    CompareOp.LESS, Bytes .toBytes(rowkey)); 
	            filters.add(filter1); 
	            
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes(coloum2), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs = pool.getTable(table).getScanner(scan); 
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}

}
