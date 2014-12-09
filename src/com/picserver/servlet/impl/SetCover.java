package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;

/**
 * Servlet implementation class SetCover
 */
@WebServlet("/SetCover")
public class SetCover extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCover() {
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
	 * 参数图片pickey、space的spacekey
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		String pickey = request.getParameter("pickey");
		String spacekey = request.getParameter("spacekey");
		
		HbaseReader  hr = new HbaseReader();
		SpaceBean sb = hr.getSpaceBean(spacekey);
		sb.setCover(pickey);
		
		HbaseWriter hw = new HbaseWriter();
		hw.putSpaceBean(sb);
		
		//没有失败的情况
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write("success");
	}

}
