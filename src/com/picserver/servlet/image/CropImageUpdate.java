package com.picserver.servlet.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;
import com.picserver.picture.PictureReader;
import com.picserver.picture.PictureUtils;
import com.picserver.picture.PictureWriter;

/**
 * Servlet implementation class CropImage
 */
@WebServlet("/CropImageUpdate")
public class CropImageUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CropImageUpdate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageName = request.getParameter("image");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		int offsetX = Integer.parseInt(request.getParameter("offsetX"));
		int offsetY = Integer.parseInt(request.getParameter("offsetY"));
		String uid=request.getParameter("uid");
		String space=request.getParameter("space");
		PictureReader PReader = new PictureReader();
	    
		try{
				byte [] buffer = PReader.readPicture(imageName,uid);
		    	PictureUtils image = new PictureUtils(buffer);
		    	byte [] outbuffer = image.cropImage(width, height, offsetX, offsetY);
				PictureWriter writer=new PictureWriter();
	            boolean flag=writer.uploadToHdfs(outbuffer, uid, space, imageName);
	            System.out.println("修改后的图片成功保存（hdfs和hbase）");
	            
	            if(flag){
	    			response.setContentType("text/html;charset=gb2312");
	    			PrintWriter out = response.getWriter();
	    			out.print("success");
	    			response.setStatus(200);
	    			System.out.println("Upload success!");
	    		} else {
	    			response.setContentType("text/html;charset=gb2312");
	    			PrintWriter out = response.getWriter();
	    			out.println("更改失败!");					
	    			response.setStatus(302);
	    			System.out.println("Upload failed");
	    		}
		    }catch(Exception e){
		    	e.printStackTrace();
		    }		
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
