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
import com.picserver.utils.JsonUtil;
import com.picserver.utils.MD5Util;

/**
 * 用户登录
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String user = request.getParameter("uid");
		String pwd = request.getParameter("pwd");

		pwd = MD5Util.getAllMD5(pwd);
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		HbaseReader hr = new HbaseReader();
		UserBean ub =hr.getUserBean(user);
		if(ub == null){
			out.write("no such user");
		}else{
			if(ub.getPwd().equals(pwd)){
				//更新上次登录时间
				ub.setLastLogin(DateUtil.getCurrentDateStr());
				HbaseWriter hw = new HbaseWriter();
				hw.putUserBean(ub);
				out.write(JsonUtil.createJsonString("user", ub));
			}else{
				out.write("error psw");
			}
		}
	}

}
