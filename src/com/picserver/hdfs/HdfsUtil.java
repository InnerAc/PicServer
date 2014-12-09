package com.picserver.hdfs;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;


/**
 * HDFS相关操作API
 * @author Jet-Muffin
 *
 */
public class HdfsUtil {
	
	private Configuration conf;
	private FileSystem fs;
	
	/**
	 * 构造方法
	 * @throws IOException
	 */
	public HdfsUtil() throws IOException{
		conf = new Configuration();
		String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSUrl();
		fs = FileSystem.newInstance(URI.create(hdfsPath),conf);
	}
	
	/**
	 * 上传文件
	 * @param in
	 * @param hdfsPath
	 * @return boolean
	 */
	public boolean upLoad(InputStream in, String hdfsPath){
		Path p = new Path(hdfsPath);
		try{
			if(fs.exists(p)){
				System.out.println("文件已存在！(HDFS LargeFile)");
				return false;
			}

			Progressable progress = new Progressable(){
				public void progress() {					
					System.out.print(".");
				}			
			};
			FSDataOutputStream out = fs.create(p,progress);
			IOUtils.copyBytes(in, out, conf);
			
//			byte[] buffer = new byte[400];
//			int length = 0;
//			while ((length = in.read(buffer)) > 0) {
//				out.write(buffer, 0, length);
//			}
			out.flush();
			out.close();
			in.close();		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 *  下载文件
	 * @param hdfsPath
	 * @param localPath
	 * @return boolean
	 */
	public boolean downLoad(String hdfsPath,String localPath ){
		Path path = new Path(hdfsPath);
		try {
			if(!fs.exists(path)){
				System.out.println("未找到文件！");
				return false;
			}
			FSDataInputStream in =  fs.open(path);
			OutputStream out = new FileOutputStream(localPath);
			byte[] buffer = new byte[400];
			int length = 0;
			while((length = in.read(buffer))>0){
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	/**
	 * 删除文件
	 * @param hdfsPath
	 * @return boolean
	 */
	public boolean deletePath(String hdfsPath){
		Path path = new Path(hdfsPath);
		try {
			if(!fs.exists(path)){
				System.out.println("未找到文件！");
				return false;
			}
			fs.delete(path, true);
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
    /**
     * 读取文件于字节缓冲数组
     * @param hadoopFile
     * @return buffer
     */
    public  byte[] readFile(String hdfsPath) throws Exception
    {
        Configuration conf = new Configuration();
        Path path = new Path(hdfsPath);
        if ( fs.exists(path) )
        {
            FSDataInputStream in = fs.open(path);
            FileStatus stat = fs.getFileStatus(path);       
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
            in.readFully(0, buffer);
            in.close();
            fs.close();    
            return buffer;
        }
        else
        {
            throw new Exception("the file is not found .");
        }
    }
	
    /** 
     * create a file  创建一个文件 
     * @param filePath 
     * @return 
     */  
    public  void createFile(String filePath,String content) throws Exception{    
          Configuration conf= new Configuration();  
          Path path = new Path(filePath);  
          FSDataOutputStream out = fs.create(path);  
          out.write(content.getBytes());  
          out.close();  
          fs.close();  
    }  
    
    
    public BufferedImage readImage(String hdfsPath) throws Exception{
        Configuration conf = new Configuration();
        Path path = new Path(hdfsPath);
        if ( fs.exists(path) )
        {
            FSDataInputStream in = fs.open(path);
            FileStatus stat = fs.getFileStatus(path);       
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
            in.readFully(0, buffer);
            in.close();
            fs.close();    
            ByteArrayInputStream bin = new ByteArrayInputStream(buffer); 
            BufferedImage image = ImageIO.read(bin);  
            return image;
        }
        else
        {
            throw new Exception("the file is not found .");
        }
    }
}
