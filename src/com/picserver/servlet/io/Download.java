package com.picserver.servlet.io;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;
/**
 * Servlet implementation class DownloadHDFSServlet
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String hdfsPath = request.getParameter("hdfsPath");
		String	localPath = request.getParameter("localPath");
	//	hdfsPath = new String(hdfsPath.getBytes("ISO-8859-1"),"utf-8");
		//localPath = new String(localPath.getBytes("ISO-8859-1"),"utf-8");
		HdfsUtil hdfsOperation = new HdfsUtil();	
		boolean flag =  hdfsOperation.downLoad(hdfsPath, localPath);
	    if(flag){
	    	response.sendRedirect("success.jsp");
	    }else{
	    	response.sendRedirect("failure.jsp");
	    }
	}


}
