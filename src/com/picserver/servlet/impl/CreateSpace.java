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
import com.picserver.hbase.HbaseWriter;
import com.picserver.utils.JsonUtil;

/**
 * Servlet implementation class CreateSpace
 */
@WebServlet("/CreateSpace")
public class CreateSpace extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public CreateSpace() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	doPost(request, response);
		//创建空间不提供url服务
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");
		response.setCharacterEncoding("utf-8");
		/*
		 * 空间名判重
		 */
		HbaseReader hr = new HbaseReader();
		
		SpaceBean s = hr.getSpaceBean(name);
		if(s == null){
			SpaceBean sb= new SpaceBean();
			sb.setKey(name+uid);
			sb.setName(name);
			sb.setDesc(desc);
			sb.setUid(uid);
			sb.setNumber("0");
			sb.setStorage("0");
			//更新用户的空间数量
			UserBean user=hr.getUserBean(uid);
			int number=Integer.parseInt(user.getSpaceNum()+1);
			user.setSpaceNum(Integer.toString(number));
			HbaseWriter hw = new HbaseWriter();
			hw.putSpaceBean(sb);
			hw.putUserBean(user);
			
			String res = "success";
			PrintWriter out = response.getWriter();
			out.write(res);
		}else{
			String res = "The space name is already existed!";
			PrintWriter out = response.getWriter();

			out.write(res);
		}
		
	}

}
