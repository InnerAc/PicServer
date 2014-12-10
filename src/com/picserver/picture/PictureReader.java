package com.picserver.picture;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.picserver.bean.PictureBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;

public class PictureReader {
	
	/**
	 * 根据图片名字读取图片
	 * @param imageName
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] readPicture(String imageName,String uid) throws Exception {
		HbaseReader HReader = new HbaseReader();
		PictureBean image = HReader.getPictureBean(imageName+uid);
		if(image != null) {
			byte[] buffer = null;
			buffer = readPicture(image);
			return buffer;			
		} else {
			return null;
		}
	}
	
	/**
	 * 根据PictureBean读取图片
	 * @param image
	 * @return
	 * @throws Exception
	 */
	public byte[] readPicture(PictureBean image) throws Exception {
		byte[] buffer = null;
		if (image.getStatus().equals("LocalFile")) {
			buffer =readLocalPicture(image.getPath(),
					image.getName());
		} else if (image.getStatus().equals("HdfsSmallFile")) {
			buffer = readMapfilePicture(image.getPath(),
					image.getName());
		} else {
			buffer = readHdfsPicture(image.getPath(),
					image.getName());
		}
		return buffer;
	}
	
	/**
	 * 从本地读取图片
	 * @param path
	 * @param fileName
	 * @return
	 */
	public byte[] readLocalPicture(String path, String fileName) {
		File file = new File(path, fileName);
		byte[] content = null;
		try {
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(in);
			byte[] bytes = new byte[1024];
			int len;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((len = bis.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
			bis.close();
			content = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 从HDFS读取大图片
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public byte[] readHdfsPicture(String path, String fileName) throws Exception {
		  String RealPath = path + '/' + fileName;
		  HdfsUtil hdfs = new HdfsUtil();	    
		  byte[] content = hdfs.readFile(RealPath);
		  return content;
	}
	
	/**
	 * 从HDFS读取MAPFILE图片
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public byte[] readMapfilePicture(String path,String fileName) throws Exception {
		MapfileUtils mu = new MapfileUtils();
		byte[] content = mu.readFromHdfs(path,fileName);
		return content;
	}
}
