package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.HdBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.JsonUtil;

/**
 * Servlet implementation class ListPano
 */
@WebServlet("/ListPano")
public class ListPano extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPano() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		uid = new String(uid.getBytes("iso-8859-1"),"utf-8");
		
		HbaseReader hr = new HbaseReader();
		List<HdBean> list = hr.getHdList(uid);
		PrintWriter out = response.getWriter();
		if(list == null){
			out.write("no hd");
		}else{
			out.write(JsonUtil.createJsonString("hd", list));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		
		HbaseReader hr = new HbaseReader();
		List<HdBean> list = hr.getHdList(uid);
		PrintWriter out = response.getWriter();
		if(list == null){
			out.write("no hd");
		}else{
			out.write(JsonUtil.createJsonString("hd", list));
		}
	}

}
