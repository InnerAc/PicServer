package com.picserver.servlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.FileBean;
import com.picserver.hdfs.HdfsUtil;

/**
 * 图片列表
 * @author Jet-Muffin
 *
 */
@WebServlet("/ListFile")
public class ListFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    HdfsUtil hdfs = new HdfsUtil();	    
	    String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath();
	    ArrayList<FileBean> fileList = hdfs.getFileList(hdfsPath);
	    for(int i =0;i<fileList.size();i++){
	    	System.out.println(fileList.get(i));
	    	System.out.println(fileList.get(i).getFileDir());
	    	System.out.println(fileList.get(i).getFileSize());
	    }	
	}
}
