package com.picserver.servlet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;

/**
 * Servlet implementation class Read3D
 */
@WebServlet("/Read3D")
public class Read3D extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Read3D() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String good_name = request.getParameter("name");
		String path = request.getParameter("path");
		String uid = request.getParameter("uid");
		HdfsUtil hdfs = new HdfsUtil();
		String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath();
		String RealPath = hdfsPath + '/' + uid + "/3D/" + good_name +'/'+path;
		System.out.println(RealPath);
		byte[] picbyte;
		try {
			picbyte = hdfs.readFile(RealPath);
			response.reset();
			OutputStream output = response.getOutputStream();// 得到输出流
			response.setContentType("image/jpeg;charset=GB2312");  
			response.setHeader("Access-Control-Allow-Origin",
					"http://localhost:81");

			InputStream imageIn = new ByteArrayInputStream(picbyte);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();
			output.close();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
