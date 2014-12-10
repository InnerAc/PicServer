package com.picserver.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.TimestampsFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.picserver.utils.DateUtil;

/**
 * hbase的操作，一般来说，删除数据和删除表使用该类
 * 写入、查询等操作使用HbaseReader或HbaseWriter
 * @author hadoop
 *
 */
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
	    public void  insertData(String tableName, String rowkey, 
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
	      * @param tableName 表名
	      * @param rowkey 主键
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
	      * 根据列和值检索表
	      * @param tableName 表名
	      * @param family 列族
	      * @param column 列
	      * @param val 值
	      * @return
	      */
	     public  ResultScanner QueryByColumn(String tableName,String family, String column, String val) { 
	         try { 
	             HTablePool pool = new HTablePool(configuration, 1000); 
	            // 当列colunm的值为val时进行查询 
	             Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family),Bytes 
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
	     
	     /**
	      * 删除表
	      * @param tableName 表名
	      */
	     public void dropTable(String tableName) { 
	         try { 
	             HBaseAdmin admin = new HBaseAdmin(configuration); 
	             admin.disableTable(tableName); 
	             admin.deleteTable(tableName); 
	         } catch (MasterNotRunningException e) { 
	             e.printStackTrace(); 
	         } catch (ZooKeeperConnectionException e) { 
	             e.printStackTrace(); 
	         } catch (IOException e) { 
	             e.printStackTrace(); 
	         } 
	     }
	     
	 	/**
	 	 *  根据用户和时间范围检索日志
	 	 * @param user 用户
	 	 * @param min 起始时间
	 	 * @param max 结束时间
	 	 * @return 
	 	 */
	     public  ResultScanner QueryLog(String user, String min, String max) { 
	         try { 
	             HTablePool pool = new HTablePool(configuration, 1000); 
	             List<Filter> filters = new ArrayList<Filter>(); 
	  
	             Filter filter1 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("time"), 
	                     CompareOp.GREATER_OR_EQUAL, Bytes .toBytes(min)); 
	             filters.add(filter1); 
	             
	             Filter filter2 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("time"), 
	                     CompareOp.LESS_OR_EQUAL, Bytes .toBytes(max)); 
	             filters.add(filter2); 
	             
	             Filter filter3 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("user"), 
	                     CompareOp.EQUAL, Bytes .toBytes(user)); 
	             filters.add(filter3); 
	             
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
	     
	     /**
	      * 根据space和usr检索图片
	      * @param uid
	      * @param space
	      * @return
	      */
	     public  ResultScanner QueryPic(String usr, String space) { 
	         try { 
	             HTablePool pool = new HTablePool(configuration, 1000); 
	             List<Filter> filters = new ArrayList<Filter>(); 
	  
	             Filter filter1 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("usr"), 
	                     CompareOp.EQUAL, Bytes .toBytes(usr)); 
	             filters.add(filter1); 
	             
	             Filter filter2 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("space"), 
	                     CompareOp.EQUAL, Bytes .toBytes(space)); 
	             filters.add(filter2); 
	             
	             Filter filter3 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("var"), Bytes .toBytes("status"), 
	                     CompareOp.NOT_EQUAL, Bytes .toBytes("deleted")); 
	             filters.add(filter3); 
	             
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
	     
	    /**
	     * 查询某个用户某个时间段内上传的图片
	     * @param usr 用户id
	     * @param sTime 起始时间
 	     * @param eTime 终止时间
	     * @return
	     */
	     public  ResultScanner QueryLimitPic(String usr, String sTime, String eTime) { 
	         try { 
	             HTablePool pool = new HTablePool(configuration, 1000); 
	             List<Filter> filters = new ArrayList<Filter>(); 
	  
	             Filter filter1 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("createTime"), 
	                     CompareOp.GREATER_OR_EQUAL, Bytes .toBytes(sTime)); 
	             filters.add(filter1); 
	             
	             Filter filter2 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("createTime"), 
	                     CompareOp.LESS_OR_EQUAL, Bytes .toBytes(eTime)); 
	             filters.add(filter2); 
	             
	             Filter filter3 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("attr"), Bytes .toBytes("usr"), 
	                     CompareOp.EQUAL, Bytes .toBytes(usr)); 
	             filters.add(filter3); 
	             
	             Filter filter4 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("var"), Bytes .toBytes("status"), 
	                     CompareOp.NOT_EQUAL, Bytes .toBytes("deleted")); 
	             filters.add(filter4); 
	             
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
	     public  ResultScanner QueryPicByColumn(String family, String column, String val) { 
	         try { 
	             HTablePool pool = new HTablePool(configuration, 1000); 
	             List<Filter> filters = new ArrayList<Filter>(); 
	            // 当列colunm的值为val时进行查询 
	             Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family),Bytes 
	                     .toBytes(column), CompareOp.EQUAL, Bytes 
	                     .toBytes(val)); 
	             filters.add(filter); 
	             
	             Filter filter1 = new SingleColumnValueFilter(Bytes 
	                     .toBytes("var"), Bytes .toBytes("status"), 
	                     CompareOp.NOT_EQUAL, Bytes .toBytes("deleted")); 
	             filters.add(filter1); 
	             
	             Scan s = new Scan(); 
	             FilterList filterList = new FilterList(filters); 
	             s.setFilter(filterList); 
	             ResultScanner rs = pool.getTable("cloud_picture").getScanner(s); 
	             return rs;
	         } catch (Exception e) { 
	             e.printStackTrace(); 
	             return null;
	         } 
	     } 

}
