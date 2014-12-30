package com.picserver.servlet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.picserver.hdfs.MapfileUtils;


/**
 * Servlet implementation class ReadFromMap
 */
@WebServlet("/ReadFromMap")
public class ReadFromMap extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String GIF = "image/gif;charset=GB2312";// 设定输出的类型  
    private static final String  JPG = "image/jpeg;charset=GB2312";         
    private static final String PNG = "image/png;charset=GB2312";       
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadFromMap() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 从hbase中查找出mapfile的地址
		String fileName = request.getParameter("image");
		if(fileName == null){
			PrintWriter out = response.getWriter();
			out.print("Input the right image path!");		
		} else{
			MapfileUtils mu = new MapfileUtils();
			byte[] data = mu.readFromHdfs("/test/seq/test.map",
					fileName);
			if(data != null) {
				OutputStream output = response.getOutputStream();// 得到输出流
				response.setContentType(GIF);
				InputStream imageIn = new ByteArrayInputStream(data);
				BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
				BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
				byte buffer[] = new byte[4096];// 缓冲字节数
				int size = 0;
				size = bis.read(buffer);
				while (size != -1) {
					bos.write(data, 0, size);
					size = bis.read(data);
				}
				bis.close();
				bos.flush();// 清空输出缓冲流
				bos.close();			
				output.close();
			}else{
				PrintWriter out = response.getWriter();
				out.print("cannot found the image!");
			}			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
