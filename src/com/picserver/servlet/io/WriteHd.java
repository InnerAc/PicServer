package com.picserver.servlet.io;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.HdBean;
import com.picserver.config.SystemConfig;
import com.picserver.hbase.HbaseWriter;
import com.picserver.picture.PictureReader;
import com.picserver.picture.PictureUtils;
import com.picserver.utils.DateUtil;

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
		    	byte [] buffer = PReader.readPicture(imageName,uid);
		    	imageName = imageName.substring(0, imageName.lastIndexOf("."));
		    	if(buffer == null) System.out.println("null");
		    	PictureUtils image = new PictureUtils(buffer);
		    	boolean flag = false;
		    	flag = image.cutImg(hdfsPath, imageName, i_size);
		    	if(flag)
		    		flag = image.write_dzi(hdfsPath, imageName,i_size);
		    	if(flag){
		    		//写入数据库
		    		HdBean hb = new HdBean();
		    		hb.setName(imageName);
		    		hb.setSize(String.valueOf(i_size));
		    		hb.setUid(uid);
		    		hb.setCreateTime(DateUtil.getCurrentDateStr());
		    		HbaseWriter hw = new HbaseWriter();
		    		hw.putHdBean(hb);
		    		
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();
					out.print("success");
					response.setStatus(200);
					System.out.println("hd image create success!");
		    	} else {
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();
					out.print("failed");					
					response.setStatus(302);
					System.out.println("create  failed");
		    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
