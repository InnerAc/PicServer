package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.UserBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.utils.DateUtil;
import com.picserver.utils.MD5Util;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		request.setCharacterEncoding("utf-8");
		
		UserBean ub = new UserBean();
		ub.setUid(request.getParameter("uid"));
		ub.setEmail(request.getParameter("email"));
		ub.setNickname(request.getParameter("nickname"));
		ub.setPwd(MD5Util.getAllMD5(request.getParameter("pwd")));
		ub.setAccType(request.getParameter("accType"));
		ub.setWebsite(request.getParameter("website"));
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		HbaseReader hr = new HbaseReader();
		UserBean u = hr.getUserBean(ub.getUid());
		//用户名不存在，可以注册
		if(u == null){
			//初始化统计信息	
			ub.setLastLogin(DateUtil.getCurrentDateStr());
			ub.setSpaceNum("0");
			ub.setPicNum("0");
			ub.setTotSize("0");
			
			HbaseWriter hw = new HbaseWriter();
			hw.putUserBean(ub);
			out.write("success");
		}else{
			out.write("uid is aleady exited");
		}
	}

}
