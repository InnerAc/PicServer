package com.picserver.servlet.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import com.picserver.bean.PictureBean;
import com.picserver.file.FileUtils;
import com.picserver.hdfs.HdfsUtil;

/**
 * Servlet implementation class UploadAjax
 */
@WebServlet("/UploadAjax")
public class UploadAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAX_FILE=1000000;      

	
    public UploadAjax() {
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
			try {
				List items = upload.parseRequest(request);			
				Iterator iter = items.iterator();
				
				//TODO 获取图片空间
					
				long ListLength = FileUtils.fileListLength(items);
				String FileName = "/test";
				String ServerPath = this.getServletConfig().getServletContext()
						.getRealPath("/");
				final String LocalPath = ServerPath + "test";
				boolean flag = false;
				
			    File LocalDir = new File(LocalPath);
	            if(!LocalDir.exists()){
	            	LocalDir.mkdir();
	            }	            
	            
				while(iter.hasNext()) {
					FileItem item = (FileItem)iter.next();			
					
					if (item.isFormField()) {  		//若为普通表单
						String name = item.getFieldName();
//						System.out.println(name);
					} else {
						//查询图片是否已存在
						PictureBean image = FileUtils.searchFile(item);
						if(image != null) {
							flag = false;
							continue;
						}
						
						long fileLength = item.getSize();
						//文件大小判断
						if(fileLength > MAX_FILE) {
							flag =  FileUtils.uploadToHdfs(item, FileName);
						} else {
							flag =  FileUtils.uploadToLocal(item, LocalPath);
						}
					}
						//TODO hdfs操作
				}
	            
				//同步线程
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
