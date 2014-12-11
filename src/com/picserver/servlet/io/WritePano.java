package com.picserver.servlet.io;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.picserver.config.SystemConfig;
import com.picserver.picture.PictureWriter;
import com.picserver.thread.SyncThread;

/**
 * Servlet implementation class WritePano
 */
@WebServlet("/WritePano")
public class WritePano extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HDFS_UPLOAD_ROOT = "/upload";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WritePano() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			response.getWriter().println("没有文件域");
		}else{			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request);			
				Iterator iter = items.iterator();
				PictureWriter PWriter = new PictureWriter();
				String uid = null;
				String space = null;

				//TODO 获取图片空间
					
	            boolean flag = false;
				while(iter.hasNext()) {
					FileItem item = (FileItem)iter.next();			
					
					if (item.isFormField()) {  		//若为普通表单
					
						String name = item.getFieldName();
						if(name.equals("uid")) {
							uid = item.getString();
							uid = new String(uid.getBytes("iso-8859-1"),"utf-8");
						} 

					} else {
						String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid + "/Pano/" ;
						
						flag = PWriter.uploadToHdfs(hdfsPath,item, uid);
					}
				}
				
			    
			    
				if(flag){
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();
					out.println("上传成功!");
					response.setStatus(200);
					System.out.println("Upload success!");
				} else {
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();
					out.println("上传失败,文件已存在!");					
					response.setStatus(302);
					System.out.println("Upload failed");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
