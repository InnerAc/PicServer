package com.picserver.control;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;
import com.picserver.picture.ScaleImageUtils;

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
		String WidthStr = request.getParameter("width");
		String HeightStr = request.getParameter("height");
		String ScaleStr = request.getParameter("height");
	
		HdfsUtil hdfs = new HdfsUtil();	    
	    String hdfsPath = com.picserver.hdfs.HDFSConfig.getHDFSPath();
	    String RealPath = hdfsPath + FilePath;
	    
		if(ScaleStr != null){
			float scale = Float.parseFloat(ScaleStr);
		    try{
		    	BufferedImage image = hdfs.readImage(RealPath);
		    	image = ScaleImageUtils.resizeByScale(scale, image);
		    	ImageIO.write(image, "JPEG", response.getOutputStream());
		    	
		    }catch(Exception e){
		    	e.printStackTrace();
		    }			
		} else if (WidthStr == null){
				int height  = Integer.parseInt(request.getParameter(HeightStr));
			    try{
			    	BufferedImage image = hdfs.readImage(RealPath);
			    	image = ScaleImageUtils.resizeByHeight(height, image);
			    	ImageIO.write(image, "JPEG", response.getOutputStream());
			    	
			    }catch(Exception e){
			    	e.printStackTrace();
			    }			
		} else if (HeightStr == null){
				int width  = Integer.parseInt(request.getParameter(WidthStr));
			    try{
			    	BufferedImage image = hdfs.readImage(RealPath);
			    	image = ScaleImageUtils.resizeByWidth(width, image);
			    	ImageIO.write(image, "JPEG", response.getOutputStream());
			    	
			    }catch(Exception e){
			    	e.printStackTrace();
			    }		
		} else {
			int width  = Integer.parseInt(request.getParameter(WidthStr));
			int height  = Integer.parseInt(request.getParameter(HeightStr));	
		    try{
		    	BufferedImage image = hdfs.readImage(RealPath);
		    	image = ScaleImageUtils.resize(width, height,image);
		    	ImageIO.write(image, "JPEG", response.getOutputStream());
		    	
		    }catch(Exception e){
		    	e.printStackTrace();
		    }		
		}
		

		

	    

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
