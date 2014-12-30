package com.picserver.servlet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PanoBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.picture.PictureReader;

/**
 * Servlet implementation class ReadPano
 */
@WebServlet("/ReadPano")
public class ReadPano extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadPano() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String image = request.getParameter("image");
		String uid = request.getParameter("uid");
		try {
		HbaseReader HReader = new HbaseReader();
		PanoBean pb = HReader.getPanoBean(image+uid);
		PictureReader PReader = new PictureReader();
	
		byte[] buffer = PReader.readHdfsPicture(pb.getPath(), image);
		
		if (buffer != null) {
			ServletContext application=request.getServletContext();   
			String ip = (String) application.getAttribute("ip");
			response.setHeader("Access-Control-Allow-Origin", "http://"+ip);
			
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(buffer);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
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
		} else {
			PrintWriter out = response.getWriter();
			out.println("Please input the correct image name.");
		}
		
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