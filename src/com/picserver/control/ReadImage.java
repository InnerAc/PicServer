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

/**
 * Servlet implementation class ReadImage
 */
@WebServlet("/ReadImage")
public class ReadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String FilePath = request.getParameter("path");
		HdfsUtil hdfs = new HdfsUtil();	    
	    String hdfsPath = com.picserver.hdfs.HDFSConfig.getHDFSPath();
	    String RealPath = hdfsPath + FilePath;
        
	    System.out.println(RealPath);
	    try{
	    	BufferedImage image = hdfs.readImage(RealPath);
        	if (RealPath.toLowerCase().endsWith(".jpg")){
        		ImageIO.write(image, "JPEG", response.getOutputStream());
        	}
        	if (RealPath.toLowerCase().endsWith(".png")){
        		ImageIO.write(image, "PNG", response.getOutputStream());
        	}
        	if (RealPath.toLowerCase().endsWith(".gif")){
        		ImageIO.write(image, "GIF", response.getOutputStream());
        	}
	    	
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
