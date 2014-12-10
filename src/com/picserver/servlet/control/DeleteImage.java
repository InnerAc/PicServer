package com.picserver.servlet.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;
import com.picserver.picture.PictureDelete;
import com.picserver.thread.DeleteThread;

/**
 * Servlet implementation class DeleteImage
 */
@WebServlet("/DeleteImage")
public class DeleteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteImage() {
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

		// 获取图片信息和相对应的sapce信息，并对space进行修改
		String pictureName = request.getParameter("image");// 图片的名字
		String uid = request.getParameter("uid");
		String rowkey = pictureName + uid;
		HbaseReader reader = new HbaseReader();
		PictureBean pic = reader.getPictureBean(rowkey);

		System.out.println(pic.getName());
		System.out.println(pic.getSpace());
		PictureDelete pd = new PictureDelete();
		boolean flag = pd.detelePicture(pic,uid);

		if(flag){
			response.setContentType("text/html;charset=gb2312");
			PrintWriter out = response.getWriter();
			out.println("success");
			response.setStatus(200);
			System.out.println("Upload success!");
		} else {
			response.setContentType("text/html;charset=gb2312");
			PrintWriter out = response.getWriter();
			out.println("删除失败!");					
			response.setStatus(302);
			System.out.println("Upload failed");
		}
	}

}
