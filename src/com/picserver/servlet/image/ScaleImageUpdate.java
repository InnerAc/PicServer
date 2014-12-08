package com.picserver.servlet.image;

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
import com.picserver.picture.PictureReader;
import com.picserver.picture.PictureUtils;
import com.picserver.picture.PictureWriter;

/**
 * Servlet implementation class ScaleImage
 */
@WebServlet("/ScaleImageUpdate")
public class ScaleImageUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ScaleImageUpdate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageName = request.getParameter("image");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		String uid=request.getParameter("uid");
		String space=request.getParameter("space");
		PictureReader PReader = new PictureReader();
		try{
		    	byte [] buffer = PReader.readPicture(imageName);
		    	PictureUtils image = new PictureUtils(buffer);
		    	byte [] outbuffer = image.scaleImage(width, height);
		    	
		    	PictureWriter writer=new PictureWriter();
	            writer.uploadToHdfs(outbuffer, uid, space, imageName);
	            
		    }catch(Exception e){
		    	e.printStackTrace();
		    }		
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
