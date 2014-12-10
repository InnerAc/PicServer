package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.LogBean;
import com.picserver.bean.LogPageBean;
import com.picserver.hbase.PageHandler;
import com.picserver.utils.DateUtil;
import com.picserver.utils.JsonUtil;

/**
 * Log分页
 */
@WebServlet("/LogPage")
public class LogPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int pageNum = 10;
	PageHandler ph = new PageHandler();
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//用户名
		String uid = request.getParameter("uid");
		//页码
		String page = request.getParameter("page");
		//每页起始row
		String row;
		String dir="";
		
		String appId;
		//使用request对象的getSession()获取session，如果session不存在则创建一个
		ServletContext application=this.getServletContext();   
		if(page.equals("0")){
			//初次请求传0
			appId = uid + DateUtil.getCurrentDateStr().substring(7, 13);
			row = DateUtil.getCurrentDateStr();
			
			List<String> strList = new ArrayList<String>();
			String str = "null";
			strList.add(str);
			strList.add(row);
			application.setAttribute(appId, strList);
			//下一页
			next(request, response, page, uid, row, appId);		
		}else{	//不是第一次
			//获取多的两个参数
			appId = request.getParameter("sessionId");
			dir = request.getParameter("dir");
			System.out.println(appId);
			System.out.println(dir);
			/*
			 * 判断请求
			 */
			if(dir.equals("next")){//下一页请求
				//获取row参数
				row = request.getParameter("row");
				System.out.println(row);
				List<String> strList = new ArrayList<String>();
				strList = (List<String>) application.getAttribute(appId);
//				if(strList == null) System.out.println("null");
				strList.add(row);
				application.setAttribute("rowlist", strList);
				next(request, response, page, uid, row, appId);
			}else{//上一页
				List<String> strList = new ArrayList<String>();
				strList = (List<String>) application.getAttribute(appId);
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				int p = Integer.parseInt(page);
				if(p==0){
					out.write("fisrt page");
				}else{
					String preRow = strList.get(p);
					List<LogBean> list = ph.logPage(uid, preRow, pageNum);
					int i = list.size();
					LogPageBean lpb = new LogPageBean();
					lpb.setSessionId(appId);
					lpb.setPage(String.valueOf(p));
					lpb.setRow(list.get(i-1).getTime());
					lpb.setList(list);
					out.write(JsonUtil.createJsonString("page", lpb));
				}
			}
		}
	}
	
	private void next(HttpServletRequest request, HttpServletResponse response, String page, String uid, String row,String sessionId) throws IOException{
		LogPageBean lpb = new LogPageBean();
		int num = Integer.parseInt(page);
		num = num + 1;
		lpb.setPage(String.valueOf(num));

		lpb.setSessionId(sessionId);
		List<LogBean> list = ph.logPage(uid, row, pageNum);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(list == null){
			out.write("no log");
		}else{
			int i = list.size();
			String r = list.get(i-1).getTime();
			lpb.setRow(r);
			lpb.setList(list);
			List<LogBean> l = ph.logPage(uid, row,1);
			if(l == null){
				lpb.setIfNext("true");
			}else{
				lpb.setIfNext("false");
			}
			out.write(JsonUtil.createJsonString("page", lpb));
		}
	}
}
