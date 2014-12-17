package com.picserver.hdfs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;

import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.hbase.HbaseWriter;
import com.picserver.utils.DateUtil;

public class MapfileUtils {
	public static String hdfsUrl = "hdfs://innerpeace-PC:9000";
	
	/**
	 * 将小图片打包成mapfile进行存储
	 * @author Jet-Muffin
	 * @param items 文件数组
	 * @param filePath	map文件地址
	 * @throws IOException
	 */
	public  void packageToHdfs(File[] items,String hdfsDir,String uid , String space) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		Path path = new Path(fs.getHomeDirectory(), hdfsDir);

		BytesWritable value = new BytesWritable();
		Text key = new Text();

		MapFile.Writer writer = new MapFile.Writer(conf, fs, path.toString(),
				key.getClass(), value.getClass());
		// 通过writer向文档中写入记录
		HbaseWriter hwriter = new HbaseWriter();
		for (File item : items) {
			try {
				String filename = item.getName();
				byte buffer[] = getBytes(item);
				writer.append(new Text(filename), new BytesWritable(buffer));
				
				PictureBean image = new PictureBean(item);
				image.setKey(item.getName()+uid);
				image.setStatus("HdfsSmallFile");
				image.setPath(path.toString());
				image.setUsr(uid);
				image.setSpace(space);
				hwriter.putPictureBean(image);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			MapfileBean mb = new MapfileBean();
			
			mb.setUid(uid);
			mb.setPicNum(Integer.toString(items.length));
			mb.setFlagNum("0");
			String name = hdfsDir.substring(hdfsDir.length()-14, hdfsDir.length());
			mb.setKey(name+uid);
			mb.setName(name);
			hwriter.putMapfileBean(mb);
		}
		IOUtils.closeStream(writer);// 关闭write流
	}
	
	/**
	 * 从Mapfile中读取出图片
	 * @param filePath Mapfile文件路径
	 * @param fileName 图片名
	 * @return bytep[] 图片byte数组
	 * @throws IOException
	 */
	public  byte[] readFromHdfs(String hdfsDir, String image) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		Path path = new Path(fs.getHomeDirectory(), hdfsDir);
		Text key = new Text(image);
		BytesWritable value = new BytesWritable();
		byte[] data = null;
		try{
			MapFile.Reader reader=new MapFile.Reader(fs,path.toString(),conf);  
			if(reader.seek(key)) {
				reader.get(key, value);
				data = value.get();			
				return data;			
			} else {
				return null;
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将file对象转化为byte数组
	 * @param file
	 * @return
	 */
	private  byte[] getBytes(File file){  
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
	/**
	 * 删除文件，代码未测试
	 * @param hdfsDir map 文件地址
	 * @param images 图片文件
	 * @throws IOException
	 */
	public void deleteImage(String hdfsDir, List<PictureBean>images) throws IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		//重新生成mapfile的路径
		String date=DateUtil.getCurrentDateStr();
		String filePath = hdfsDir.substring(0, hdfsDir.length()-14) +date;
		Path path = new Path(fs.getHomeDirectory(), filePath);
		BytesWritable value = new BytesWritable();
		Text key = new Text();
		MapFile.Writer writer = new MapFile.Writer(conf, fs, path.toString(),
				key.getClass(), value.getClass());
		
		HbaseWriter hwriter = new HbaseWriter();
		//对文件进行写入
		for(PictureBean image:images){
			byte[] data = readFromHdfs(hdfsDir,image.getName());
			
			if(data==null) System.out.println("null");
			
			writer.append(new Text(image.getName()), new BytesWritable(data));
			System.out.println("重写mapfile成功！");
			
			//更新图片的地址
			image.setPath(filePath);	
			hwriter.putPictureBean(image);
			System.out.println("更新图片的地址成功");
		}
		IOUtils.closeStream(writer);// 关闭write流
		//更新mapfile数据库信息
		MapfileBean map=new MapfileBean();
		map.setKey(date+images.get(0).getUsr());
		map.setFlagNum("0");
		map.setName(date);
		map.setPicNum(Integer.toString(images.size()));
		map.setUid(images.get(0).getUsr());
		hwriter.putMapfileBean(map);
		System.out.println("更新mapfile成功！");
		
		
	}
}
	
