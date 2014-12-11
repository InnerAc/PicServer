package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.JsonUtil;

/**
 * Servlet implementation class GetUser
 */
@WebServlet("/GetUser")
public class GetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		uid = new String(uid.getBytes("iso-8859-1"),"utf-8");
		response.setCharacterEncoding("utf-8");
		HbaseReader hr = new HbaseReader();
		try {
			UserBean ub = hr.getUserBean(uid);
			String res = JsonUtil.createJsonString("User", ub);
			PrintWriter out = response.getWriter();
			out.write(res);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		response.setCharacterEncoding("utf-8");
		HbaseReader hr = new HbaseReader();
		try {
			UserBean ub = hr.getUserBean(uid);
			String res = JsonUtil.createJsonString("User", ub);
			PrintWriter out = response.getWriter();
			out.write(res);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
