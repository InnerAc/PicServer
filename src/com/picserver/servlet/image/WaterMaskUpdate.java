package com.picserver.servlet.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.LogBean;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;
import com.picserver.picture.PictureReader;
import com.picserver.picture.PictureUtils;
import com.picserver.picture.PictureWriter;

/**
 * Servlet implementation class WaterMask
 */
@WebServlet("/WaterMaskUpdate")
public class WaterMaskUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private  byte [] outbuffer;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WaterMaskUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageName = request.getParameter("image");
		int offsetX = Integer.parseInt(request.getParameter("offsetX"));
		int offsetY = Integer.parseInt(request.getParameter("offsetY"));			
		String MaskType = request.getParameter("type");
		String uid=request.getParameter("uid");
		String space=request.getParameter("space");
		PictureReader PReader = new PictureReader();
		
	    try{
		    
			if(MaskType.equals("image")){
		    	int width = Integer.parseInt(request.getParameter("width"));
				int height = Integer.parseInt(request.getParameter("height"));
				String LogoName = request.getParameter("logo");	
				
			    
			    byte [] buffer = PReader.readPicture(imageName,uid);
		    	byte [] mbyte = PReader.readPicture(LogoName,uid);

		    	PictureUtils image = new PictureUtils(buffer);
		    	outbuffer  = image.imgWaterMask(mbyte,width, height, offsetX, offsetY);				
			} 
			
			if(MaskType.equals("text")){
				String text = request.getParameter("text");
				int fontsize = Integer.parseInt(request.getParameter("fontsize"));
				byte [] buffer = PReader.readPicture(imageName,uid);
				PictureUtils image = new PictureUtils(buffer);
				outbuffer  = image.textWaterMask(text, fontsize,offsetX, offsetY);
			}
	    	
			PictureWriter writer=new PictureWriter();
			boolean flag=writer.uploadToHdfs(outbuffer, uid, space, imageName);
	          System.out.println("修改后的图片成功保存（hdfs和hbase）");
	            
	            if(flag){
	    			LogBean lb = new LogBean(uid, "图片水印"+imageName);
	    			HbaseWriter hw = new HbaseWriter();
	    			hw.putLogBean(lb);
	    			response.setContentType("text/html;charset=gb2312");
	    			PrintWriter out = response.getWriter();
	    			out.print("success");
	    			response.setStatus(200);
  				ServletContext application=request.getServletContext();   
				String ip = (String) application.getAttribute("ip");
	    			response.sendRedirect("http://"+ip+"/picloud/index.php/Picserver/view/" + imageName + ".html");
	    			System.out.println("Upload success!");
	    		} else {
	    			response.setContentType("text/html;charset=gb2312");
	    			PrintWriter out = response.getWriter();
	    			out.println("更改失败!");					
	    			response.setStatus(302);
	    			System.out.println("Upload failed");
	    		}
            
	    } catch(Exception e) {
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
