package com.picserver.servlet.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.picserver.hdfs.WriteFile;
import com.picserver.picture.PictureWriter;

/**
 * Servlet implementation class UploadQueue
 */
@WebServlet("/UploadQueue")
public class UploadQueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAX_FILE=1000000;      

    public UploadQueue() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("不支持GET");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("!");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("upload");
		if(!isMultipart){
			response.getWriter().println("没有文件域");
		}else{			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
           
			try {
				List items = upload.parseRequest(request);			
				Iterator iter = items.iterator();
				String uid = "test";
				PictureWriter fileutil = new PictureWriter();
				//TODO 获取图片空间
					
				long ListLength = PictureWriter.fileListLength(items);
				String ServerPath = this.getServletConfig().getServletContext()
						.getRealPath("/");
				String FileName = "/test";
				final String LocalPath = ServerPath + "test";
				boolean flag = false;
				
			    File LocalDir = new File(LocalPath);
	            if(!LocalDir.exists()){
	            	LocalDir.mkdir();
	            }
	            
				while(iter.hasNext()) {
					FileItem item = (FileItem)iter.next();   
					long fileLength = item.getSize();
					//文件大小判断
					if(fileLength > MAX_FILE) {
//						flag =  fileutil.uploadToHdfs(item, uid,space);
					} else {
//						flag =  fileutil.uploadToLocal(item, uid,space);
					}
						//TODO hdfs操作
				}
				
				if (flag) {
					response.sendRedirect("success.jsp");
				} else {
					response.sendRedirect("failure.jsp");
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
				response.sendRedirect("failure.jsp");
			}
		}		
	}
}
