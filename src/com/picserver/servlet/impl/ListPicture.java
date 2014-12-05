package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PictureBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.JsonUtil;

/**
 * Servlet implementation class PicServlet
 */
@WebServlet("/ListPicture")
public class ListPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListPicture() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		uid = new String(uid.getBytes("iso-8859-1"),"utf-8");
		String space = request.getParameter("space");
		space = new String(space.getBytes("iso-8859-1"),"utf-8");
		
		HbaseReader hr = new HbaseReader();
		List<PictureBean> list = hr.getPictureBean(uid, space);
		String res = JsonUtil.createJsonString("Picture", list);
		PrintWriter out = response.getWriter();
		out.write(res);
	}

}
