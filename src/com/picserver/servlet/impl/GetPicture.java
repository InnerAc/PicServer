package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PictureBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.JsonUtil;

/**
 * 根据图片名得到图片信息
 */
@WebServlet("/GetPicture")
public class GetPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetPicture() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		name = new String(name.getBytes("iso-8859-1"),"utf-8");
		
		String uid = request.getParameter("uid");
		uid = new String(uid.getBytes("iso-8859-1"),"utf-8");
		
		HbaseReader hr = new HbaseReader();
		PictureBean pb = hr.getPictureBean(name+uid);
		String res = JsonUtil.createJsonString("Picture", pb);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(res);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String uid = request.getParameter("uid");
		response.setCharacterEncoding("utf-8");
		
		HbaseReader hr = new HbaseReader();
		PictureBean pb = hr.getPictureBean(name+uid);
		String res = JsonUtil.createJsonString("Picture", pb);
		PrintWriter out = response.getWriter();
		out.write(res);
	}
}
