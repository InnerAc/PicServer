package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.JsonUtil;

/**
 * 根据space name获取space信息
 */
@WebServlet("/GetSpace")
public class GetSpace extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetSpace() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		name = new String(name.getBytes("iso-8859-1"),"utf-8");
		response.setCharacterEncoding("utf-8");
		HbaseReader hr = new HbaseReader();
		try {
			SpaceBean sb = hr.getSpaceBean(name);
			String res = JsonUtil.createJsonString("Space", sb);
			PrintWriter out = response.getWriter();
			out.write(res);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		HbaseReader hr = new HbaseReader();
		response.setCharacterEncoding("utf-8");
		try {
			SpaceBean sb = hr.getSpaceBean(name);
			String res = JsonUtil.createJsonString("Space", sb);
			PrintWriter out = response.getWriter();
			out.write(res);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
