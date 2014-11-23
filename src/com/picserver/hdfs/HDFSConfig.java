
package com.picserver.hdfs;

import java.io.IOException;

/**
 * HDFS设置内容
 * @author Jet-Muffin
 *
 */
public class HDFSConfig {
	
	/**
	 *HDFS路径
	 * @return String	
	 */
	public static String getHDFSUrl(){
		return "hdfs://localhost:9000";
	}
	
	/**
	 * 用户的HDFS分路径
	 * @return String
	 */
	public static String  getHDFSPath(){
		return "hdfs://localhost:9000/test/";		
	}
}
