package com.picserver.servlet.io;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.hadoop.conf.Configuration;

import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.SearchPath;
import com.picserver.hdfs.WriteFile;
/**
 * 上传图片
 * @author Jet-Muffin
 *
 */
@WebServlet("/Up")
public class Up extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private static final long MAX_FILE=1000000;  
    public Up() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println(isMultipart);
		if(!isMultipart){
		}else{			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);		//获取多文件	
				for(FileItem item:items){
					System.out.println(item.getName());
					if(item.getSize()<MAX_FILE){    //判断大小，小文件存入SequenceFile中,大文件直接存入
						WriteFile.writeSmallFile(item);
					}
					else {
						WriteFile.writeLargeFile(item);
					}
				}		
				}			
			catch (Exception e) {				
				e.printStackTrace();
			}
		}		
	}
	
	}
	

