package com.picserver.servlet.io;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.config.SystemConfig;
import com.picserver.picture.PictureReader;
import com.picserver.picture.PictureUtils;

/**
 * Servlet implementation class WriteHd
 */
@WebServlet("/WriteHd")
public class WriteHd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HDFS_UPLOAD_ROOT = "/upload";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteHd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String imageName = request.getParameter("image");
		
		int  i_size = 512;
		
		final String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid + "/HdImage";
		
		PictureReader PReader = new PictureReader();
		try{
		    	byte [] buffer = PReader.readPicture(imageName);
		    	imageName = imageName.substring(0, imageName.lastIndexOf("."));
		    	if(buffer == null) System.out.println("null");
		    	PictureUtils image = new PictureUtils(buffer);

//		    	image.cutImg(hdfsPath, imageName, i_size);
		    	image.write_dzi(hdfsPath, i_size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
