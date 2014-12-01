package com.picserver.servlet.io;

import java.io.File;
import java.io.IOException;
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
import com.picserver.file.FileUtils;;

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
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			response.getWriter().println("没有文件域");
		}else{			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			String FileName = "/test";
			//String LocalPath = this.getServletContext().getRealPath("/test");
			final String LocalPath = "/home/had/jspworkspace/tmp/test";
			//System.out.println(LocalPath);
		    File LocalDir = new File(LocalPath);
            if(!LocalDir.exists()){
            	LocalDir.mkdir();
            }
            
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
				//final FileUtils FileUtil = new FileUtils();
				long ListLength = FileUtils.fileListLength(items);
				boolean flag = false;
				if (ListLength > MAX_FILE) {
					// 文件集合大于阈值，上传到云端
					flag = FileUtils.uploadToHdfs(items, FileName);
				} else {
					flag = FileUtils.uploadToLocal(items, LocalPath);
				}
				
				//检查同步线程
				Thread t=new Thread(){
				    public void run(){
				    	try {
							FileUtils.localDirSync(LocalPath);
						} catch (IOException e) {
							e.printStackTrace();
						}
				   }
				};
				t.start();
				
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
