package com.picserver.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseOperation {
		HbaseConf hbaseConf = new HbaseConf();
		Configuration configuration = hbaseConf.hbaseConf();
		
		/**
		 * 插入数据
		 * @param tableName 表名
		 * @param rowkey 主键
 		 * @param rowfamily 列族
		 * @param row 列
		 * @param data 数据
		 */
	    public void insertData(String tableName, String rowkey, 
	    		String rowfamily, String row,  String data) { 
	    		HTablePool pool = new HTablePool(configuration, 1000); 
	    		// 一个put代表一行数据，再new一个put表示第二行数据,每行一个唯一的rowkey
	    		//此处rowkey为put构造方法中传入的值 
	    		Put put = new Put(rowkey.getBytes());
	    		// 本行数据的第一列 
	    		put.add(rowfamily.getBytes(), row.getBytes(), data.getBytes());
	    		try { 
	    			pool.getTable(tableName).put(put);
	    		} catch (IOException e) { 
	    			e.printStackTrace(); 
	    		} 
	    	} 
	    
	    /**
	     * 根据主键删除数据
	     * @param tablename 表名
	     * @param rowkey 主键
	     */
	     public void deleteRow(String tablename, String rowkey)  { 
	         	try { 
	         		HTable table = new HTable(configuration, tablename); 
	         		List list = new ArrayList(); 
	         		Delete delete = new Delete(rowkey.getBytes()); 
	         		list.add(delete); 
	         		table.delete(list); 
	         		table.close();
	         	} catch (IOException e) { 
	         		e.printStackTrace(); 
	         	} 
	     }
	     
	     /**
	      * 根据主键查询信息
	      * @param tableName
	      * @param rowkey
	      * @return
	      */
	     public Result QueryByRowKey(String tableName, String rowkey) { 
	         	HTablePool pool = new HTablePool(configuration, 1000); 
	         	try { 
	         		// 根据rowkey查询 
	         		Get scan = new Get(rowkey.getBytes());
	         		Result r = pool.getTable(tableName).get(scan); 
	         		return r;
	         	} catch (IOException e) { 
	         		e.printStackTrace(); 
	         		return null;
	         	} 
	     } 
	     
	     /**
	      * 根据column和val检索hbase
	      * @param tableName
	      * @param column
	      * @param val
	      * @return
	      */
	     public  ResultScanner QueryByColumn(String tableName,String column, String val) { 
	         try { 
	             HTablePool pool = new HTablePool(configuration, 1000); 
	            // 当列colunm的值为val时进行查询 
	             Filter filter = new SingleColumnValueFilter(null,Bytes 
	                     .toBytes(column), CompareOp.EQUAL, Bytes 
	                     .toBytes(val)); 
	             Scan s = new Scan(); 
	             s.setFilter(filter); 
	             ResultScanner rs = pool.getTable(tableName).getScanner(s); 
	             return rs;
	         } catch (Exception e) { 
	             e.printStackTrace(); 
	             return null;
	         } 
	  
	     } 
}
