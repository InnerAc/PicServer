package com.picserver.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class WriteFile {
	// 大文件读入
	public static void writeBF(FileItem item) throws IOException{
		String fileName = item.getName();
		   InputStream uploadedStream = item.getInputStream();
		    HdfsUtil hdfs = new HdfsUtil();
		    String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath() + fileName;
		    hdfs.upLoad(uploadedStream, hdfsPath);	
	}
	//小文件获取SequenceFile来进行写入
		public static void writeSF(FileItem item) throws IOException {

			SequenceFile.Writer writer = SearchPath.creatPath( item);
				try {   
					
					      writer.append(new Text(item.getName()), new BytesWritable(item.get()));
				} catch (Exception e) {
					System.out.println("Exception MESSAGES = " + e.getMessage());
					e.printStackTrace();
				}
				writer.close();
			}	
		
			
				
	
}
