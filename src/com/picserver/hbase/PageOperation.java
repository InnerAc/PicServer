package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 分页操作
 * @author hadoop
 *
 */
public class PageOperation {
	
	HbaseConf hbaseConf = new HbaseConf();
	Configuration configuration = hbaseConf.hbaseConf();
	
	/**
	 * 日志分页查询
	 * @param table
	 * @param coloum1
	 * @param rowkey
	 * @param coloum2
	 * @param uid
	 * @param num
	 * @return
	 */
	public ResultScanner logPageHandler( String rowkey, String uid, int num){
		 try { 
	            HTablePool pool = new HTablePool(configuration, 1000); 
	            List<Filter> filters = new ArrayList<Filter>(); 
	 
	            Filter filter1 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("time"), 
	                    CompareOp.LESS, Bytes .toBytes(rowkey)); 
	            filters.add(filter1); 
	            
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("user"), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs = pool.getTable("cloud_log").getScanner(scan); 
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}

	public ResultScanner picPageHandlerbyTime(String time, String uid, int num){
		 try { 
	            HTablePool pool = new HTablePool(configuration, 1000); 
	            List<Filter> filters = new ArrayList<Filter>(); 
	 
	            Filter filter1 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("createTime"), 
	                    CompareOp.LESS, Bytes .toBytes(time)); 
	            filters.add(filter1); 
	            
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("usr"), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs = pool.getTable("cloud_picture").getScanner(scan); 
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}

	public ResultScanner picPageHandlerbyKey(String key, String uid, int num){
		 try { 
	            HTablePool pool = new HTablePool(configuration, 1000); 
	            List<Filter> filters = new ArrayList<Filter>(); 
	            Filter rf = new RowFilter(CompareOp.GREATER_OR_EQUAL, new BinaryComparator(key.getBytes()));
	            filters.add(rf);
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("usr"), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs = pool.getTable("cloud_picture").getScanner(scan); 
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}
	
}
