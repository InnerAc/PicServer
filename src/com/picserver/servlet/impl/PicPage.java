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

import com.picserver.bean.PicPageBean;
import com.picserver.bean.PictureBean;
import com.picserver.hbase.PageHandler;
import com.picserver.utils.DateUtil;
import com.picserver.utils.JsonUtil;

/**
 * 图片分页
 */
@WebServlet("/PicPage")
public class PicPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//数+1
	private static final int pageNum = 3;
	PageHandler ph = new PageHandler();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PicPage() {
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
		String space = request.getParameter("space");
		String row;
		String dir = "";

		String appId;
		// 使用request对象的getSession()获取session，如果session不存在则创建一个
		ServletContext application = this.getServletContext();
		if (page.equals("1")) {
			// 初次请求传1
			appId = uid + DateUtil.getCurrentDateStr().substring(7, 13);
			row = DateUtil.getCurrentDateStr();
			List<PictureBean> list = ph.picPageByTime(uid, row,space, 1);
			if (list == null) {
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.write("no picture!");
			} else {
				PictureBean pb = list.get(0);
				row = pb.getKey();
				List<String> strList = new ArrayList<String>();
				String str = "null";
				strList.add(str);
				strList.add(row);
				// 下一页
				next(request, response, application, page, uid, row, space,appId,strList);
			}
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
				row = strList.get(Integer.parseInt(page) );
				next(request, response, application, page, uid, row, space,appId,	strList);

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
					List<PictureBean> list = ph.picPageByKey(uid, preRow,space, pageNum-1);
					int i = list.size();
					PicPageBean ppb = new PicPageBean();
					ppb.setAppId(appId);
					ppb.setPage(String.valueOf(p));
					ppb.setList(list);
					out.write(JsonUtil.createJsonString("page", ppb));
				}
			}
		}
	}

	private void next(HttpServletRequest request, HttpServletResponse response,
			ServletContext application, String page, String uid, String row,String space,
			String appId, List<String> strList) throws IOException {
		PicPageBean ppb = new PicPageBean();
		ppb.setPage(page);
		ppb.setAppId(appId);
		List<PictureBean> list = ph.picPageByKey(uid, row, space,pageNum);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (list == null) {
			out.write("no picture");
		} else {
			int i = list.size(); 
			if(i == pageNum){
				ppb.setIfNext("true");
				String r = list.get(i - 1).getKey();
				strList.add(r);
				list.remove( i-1);
			}else{
				ppb.setIfNext("false");
			}
			ppb.setList(list);
			application.setAttribute(appId, strList);
			out.write(JsonUtil.createJsonString("page", ppb));
		}
	}

}
