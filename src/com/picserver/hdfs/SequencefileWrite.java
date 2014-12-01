package com.picserver.hdfs;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class SequencefileWrite {
	public static String hdfsUrl = "hdfs://localhost:9000";
	
	public static void packageToHdfs( File[] items) throws IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		FSDataInputStream in = null;
		BytesWritable value = new BytesWritable();
		Text key = new Text();
		Path path = new Path(fs.getHomeDirectory(), "/test/seq/test.seq");

		SequenceFile.Writer writer = null;
		writer = SequenceFile.createWriter(fs, conf, path, key.getClass(),value.getClass());
		
		for (File item : items) {
			try {
				String filename = item.getName();
				byte buffer[] = getBytes(item);
				writer.append(new Text(filename), new BytesWritable(
						buffer));		
			} catch (Exception e) {
				System.out.println("Exception MESSAGES = " + e.getMessage());
				e.printStackTrace();
			}
		}
		writer.close();
	}
	
	private static byte[] getBytes(File file){  
        byte[] buffer = null;  
        try {  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[fis.available()];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return buffer;  
    }
}
