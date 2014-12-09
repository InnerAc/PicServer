package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 *图片分页
 */
@WebServlet("/PicPage")
public class PicPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PicPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
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
			row = DateUtil.getCurrentDateStr();
		}else{
			row = request.getParameter("row");
		}
		PicPageBean ppb = new PicPageBean();
		int num = Integer.parseInt(page);
		num = num + 1;
		ppb.setPage(String.valueOf(num));
		
		PageHandler ph = new PageHandler();
		List<PictureBean> list = ph.picPage(uid, row, 10);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(list == null){
			out.write("no picture");
		}else{
			int i = list.size();
			ppb.setRow(list.get(i-1).getCreateTime());
			ppb.setList(list);
			out.write(JsonUtil.createJsonString("page", ppb));
		}
	}

}
