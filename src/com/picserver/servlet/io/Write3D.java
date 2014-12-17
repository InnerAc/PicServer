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

import com.picserver.bean.PanoBean;
import com.picserver.bean.Pic3DBean;
import com.picserver.hbase.HbaseWriter;
import com.picserver.picture.PictureWriter;
import com.picserver.utils.DateUtil;

/**
 * Servlet implementation class Write3D
 */
@WebServlet("/Write3D")
public class Write3D extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HDFS_UPLOAD_ROOT = "/upload";      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Write3D() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
				String good_name = null;
				int num = 0;
				//TODO 获取图片空间
					
	            boolean flag = false;
				while(iter.hasNext()) {
					FileItem item = (FileItem)iter.next();			
					
					if (item.isFormField()) {  		//若为普通表单
						String name = item.getFieldName();
						if(name.equals("uid")) {
							uid = item.getString();
						} else if (name.equals("name")) {
							good_name = item.getString();
						}

					} else {
						System.out.println(uid+good_name);
						String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid + "/3D/" + good_name + "/" ;
						System.out.println(hdfsPath);
						flag = PWriter.uploadToHdfs(hdfsPath,item, uid);
						num++;

				}
				}
				HbaseWriter writer=new HbaseWriter();
			    Pic3DBean p3d=new Pic3DBean();
			    p3d.setKey(good_name+uid);
			    p3d.setName(good_name);
			    p3d.setSize(String.valueOf(512));
			    p3d.setNum(Integer.toString(num));
			    p3d.setUid(uid);
			    p3d.setCreateTime(DateUtil.getCurrentDateStr());
			    writer.put3DBean(p3d);
			    System.out.println("更新数据库成功！");
			    
				if(flag){
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();
					response.setStatus(200);
					System.out.print("success");
				} else {
					response.setContentType("text/html;charset=gb2312");
					PrintWriter out = response.getWriter();
					out.print("failed");					
					response.setStatus(302);
					System.out.println("Upload failed");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
