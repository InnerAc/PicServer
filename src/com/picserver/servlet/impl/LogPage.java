package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
		if(page.equals("0")){
			//初次请求传0
			String time = DateUtil.getCurrentDateMS();
			String max = "99999999999999999";
			double d1 =   Double.parseDouble(max);
			double d2 = Double.parseDouble(time);
			row = String.valueOf(d1-d2)+uid ;
		}else{
			row = request.getParameter("row");
		}
		LogPageBean lpb = new LogPageBean();
		int num = Integer.parseInt(page);
		num = num + 1;
		lpb.setPage(String.valueOf(num));
		
		PageHandler ph = new PageHandler();
		List<LogBean> list = ph.LogPage(uid, row, 2);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(list == null){
			out.write("no log");
		}else{
			int i = list.size();
			lpb.setRow(list.get(i-1).getLogid());
			lpb.setList(list);
			out.write(JsonUtil.createJsonString("page", lpb));
		}
	}
}
