package com.picserver.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;
import com.picserver.picture.PictureUtils;;

/**
 * Servlet implementation class ScaleImage
 */
@WebServlet("/ScaleImage")
public class ScaleImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScaleImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String FilePath = request.getParameter("path");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
	
		HdfsUtil hdfs = new HdfsUtil();	    
	    String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath();
	    String RealPath = hdfsPath + FilePath;
		try{
		    	byte [] buffer = hdfs.readFile(RealPath);
		    	PictureUtils image = new PictureUtils(buffer);
		    	byte [] outbuffer = image.scaleImage(width, height);
		    	
				OutputStream output = response.getOutputStream();// 得到输出流  
	            InputStream imageIn = new ByteArrayInputStream(outbuffer); 
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
		    	e.printStackTrace();
		    }		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
