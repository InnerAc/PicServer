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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 用户名
		String uid = request.getParameter("uid");
		// 页码
		String page = request.getParameter("page");
		// 每页起始row
		String row;
		String dir = "";

		String appId;
		// 使用request对象的getSession()获取session，如果session不存在则创建一个
		ServletContext application = this.getServletContext();
		if (page.equals("1")) {
			// 初次请求传0
			appId = uid + DateUtil.getCurrentDateStr().substring(7, 13);
			row = DateUtil.getCurrentDateStr();
			
			List<String> strList = new ArrayList<String>();
			String str = "null";
			strList.add(str);
			strList.add(row);
			
			
			// 下一页
			next(request, response,application, page, uid, row, appId,strList);
		} else { // 不是第一次
			// 获取多的两个参数
			appId = request.getParameter("appId");
			dir = request.getParameter("dir");
			/*
			 * 判断请求
			 */
			if (dir.equals("next")) {// 下一页请求
				// 获取row参数
				List<String> strList = new ArrayList<String>();
				strList = (List<String>) application.getAttribute(appId);
				row = strList.get(Integer.parseInt(page));
				next(request, response, application,page, uid, row, appId,strList);

			} else {// 上一页
				List<String> strList = new ArrayList<String>();
				strList = (List<String>) application.getAttribute(appId);
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				int p = Integer.parseInt(page);
				if (p == 0) {
					out.write("fisrt page");
				} else {
					String preRow = strList.get(p);
					List<LogBean> list = ph.logPage(uid, preRow, pageNum);
					LogPageBean lpb = new LogPageBean();
					lpb.setAppId(appId);
					lpb.setPage(String.valueOf(p));
					lpb.setList(list);
					out.write(JsonUtil.createJsonString("page", lpb));
				}
			}
		}
	}

	private void next(HttpServletRequest request, HttpServletResponse response,ServletContext application,
			String page, String uid, String row, String appId,List<String> strList)
			throws IOException {
		LogPageBean lpb = new LogPageBean();
		lpb.setPage(page);
		lpb.setAppId(appId);
		List<LogBean> list = ph.logPage(uid, row, pageNum);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (list == null) {
			out.write("no log");
		} else {
			int i = list.size();
			String r = list.get(i - 1).getTime();
			lpb.setList(list);
			strList.add(r);
			application.setAttribute(appId, strList);
			List<LogBean> l = ph.logPage(uid, r, 1);
			if (l == null) {
				lpb.setIfNext("false");
			} else {
				lpb.setIfNext("true");
			}
			out.write(JsonUtil.createJsonString("page", lpb));
		}
	}
}
