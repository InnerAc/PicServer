package com.picserver.control;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;  

import javax.servlet.*;  
import javax.servlet.http.*;  

import com.picserver.hdfs.HdfsUtil;
import com.sun.image.codec.jpeg.*;//sun公司仅提供了jpg图片文件的编码api  

import javax.imageio.stream.*;  

import java.awt.*;  
import java.awt.image.BufferedImage; 

import com.picserver.picture.ScaleImageUtils;

/**
 * 读取图片
 * @author Jet-Muffin
 *
 */
@WebServlet("/ReadFile")
public class ReadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String GIF = "image/gif;charset=GB2312";// 设定输出的类型  
    private static final String  JPG = "image/jpeg;charset=GB2312";         
    private static final String PNG = "image/png;charset=GB2312";

    public ReadFile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String FilePath = request.getParameter("path");
		HdfsUtil hdfs = new HdfsUtil();	    
	    String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath();
	    String RealPath = hdfsPath + FilePath;
        
	    System.out.println(RealPath);
	    
	    try{
	    		byte[] picbyte = hdfs.readFile(RealPath);
	    		response.reset(); 
	    		OutputStream output = response.getOutputStream();// 得到输出流  
	    		
	        	if (RealPath.toLowerCase().endsWith(".png")){
	        		response.setContentType(PNG);  
	        	}
	         	
	        	if(RealPath.toLowerCase().endsWith(".gif")){
	        		response.setContentType(GIF);  
	        	}
	        	
	        	if(RealPath.toLowerCase().endsWith(".jpg")){
	        		response.setContentType(JPG);  
	        	}	
	        	
	              InputStream imageIn = new ByteArrayInputStream(picbyte); 
	              BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流  
	              BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流  
	              byte data[] = new byte[4096];// 缓冲字节数  
	              int size = 0;  
	              size = bis.read(data);  
	              while (size != -1) {  
	                  bos.write(data, 0, size);  
	                  size = bis.read(data);  
	              }  
	              bis.close();  
	              bos.flush();// 清空输出缓冲流  
	              bos.close();  
	              
	              output.close(); 
	    	
	    }catch(Exception e){
	    	PrintWriter out = response.getWriter();
	    	out.println("cannot find the file!");
	    	out.close();
	    	e.printStackTrace();
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
