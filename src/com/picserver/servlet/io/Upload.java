package com.picserver.servlet.io;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
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

import com.picserver.hdfs.HdfsUtil;

/**
 * 上传图片
 * @author Jet-Muffin
 *
 */
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Upload() {
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
				List items = upload.parseRequest(request);			
				Iterator iter = items.iterator();
				while(iter.hasNext()){
					FileItem item = (FileItem)iter.next();
					if(item.isFormField()){
						String name = item.getFieldName();
						System.out.println(name);
					}else{
					    String fieldName = item.getFieldName();
					    String fileName = item.getName();
					    String contentType = item.getContentType();
					    boolean isInMemory = item.isInMemory();
					    long sizeInBytes = item.getSize();
					    //String flag = flase;
					    InputStream uploadedStream = item.getInputStream();
					    HdfsUtil hdfs = new HdfsUtil();
					    String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath() + fileName;
					    boolean flag = hdfs.upLoad(uploadedStream, hdfsPath);	
					    if(flag){
					    	response.sendRedirect("success.jsp");
					    }else{
					    	response.sendRedirect("failure.jsp");
					    }
					}
				}
				
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}		
	}
}
