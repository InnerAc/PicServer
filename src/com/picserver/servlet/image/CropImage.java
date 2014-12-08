package com.picserver.servlet.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;
import com.picserver.picture.PictureReader;
import com.picserver.picture.PictureUtils;

/**
 * Servlet implementation class CropImage
 */
@WebServlet("/CropImage")
public class CropImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CropImage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageName = request.getParameter("image");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		int offsetX = Integer.parseInt(request.getParameter("offsetX"));
		int offsetY = Integer.parseInt(request.getParameter("offsetY"));
		
		PictureReader PReader = new PictureReader();
	    
		try{
				byte [] buffer = PReader.readPicture(imageName);
		    	PictureUtils image = new PictureUtils(buffer);
		    	byte [] outbuffer = image.cropImage(width, height, offsetX, offsetY);
		    	
		    	
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
