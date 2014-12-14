package com.picserver.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * 创建数据库，运行一次就可以列
 * @author hadoop
 *
 */
public class CreateHbase {
	static HbaseConf hbaseConf = new HbaseConf();
	static Configuration configuration = hbaseConf.hbaseConf();

	public static void main(String[] args) {
		
//		// 创建云图片表
//		System.out.println("2");
//		String name1 = "cloud_picture";
//		String[] column = { "attr", "var" };

//		createTable(name1, column);
//		System.out.println("1");
////
////		// 创建云空间表
//		String name2 = "cloud_s";
//		createTable(name2, column);
//		
//		//创建用户表
//		String name3 = "cloud_user";
//		String[] column1 = {"attr", "vldt", "pic", "space"};
//		createTable(name3, column1);
//		
//		//创建日志表
//		String name4 = "cloud_mapfile";
//		String[] coloum2 = {"attr"};
//		createTable(name4, coloum2);
//		
////		//创建高清图片表
//		String name6 = "cloud_hd";
//		String[] c = {"attr"};
//		createTable(name6, c);
//		
////		//创建3D图片表
//		String name7= "cloud_3d";
//		String[] c1 = {"attr"};
//		createTable(name7, c1);
////		//创建pano图片表
//		String name8= "cloud_log";
//		String[] c2 = {"attr"};
//		createTable(name8, c2);		
		
	
	}

	/**
	 * 建表
	 * @param tableName 表名
	 * @param column 族列数组
	 */
	public static void createTable(String tableName, String[] column) {
		try {
			HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);
			// 如果存在要创建的表，不做操作
			if (hBaseAdmin.tableExists(tableName)) {
				//System.out.println(tableName + " is exist....");
			} else {
				// 建列族
				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				int num = column.length;
				for (int i = 0; i < num; i++) {
					//添加列族
					tableDescriptor.addFamily(new HColumnDescriptor(column[i]));
				}
				hBaseAdmin.createTable(tableDescriptor);
				hBaseAdmin.close();
			}
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
