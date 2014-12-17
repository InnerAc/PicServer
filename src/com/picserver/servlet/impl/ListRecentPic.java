package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PictureBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.DateUtil;
import com.picserver.utils.JsonUtil;

/**
 * Servlet implementation class ListRecentPic
 */
@WebServlet("/ListRecentPic")
public class ListRecentPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListRecentPic() {
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
		String uid  = request.getParameter("uid");
		String et = DateUtil.getCurrentDateStr();
		double etime =Double.parseDouble(et);
		double stime = etime - 1000000;
		String st = String.valueOf(stime);
		HbaseReader hr = new HbaseReader();
		List<PictureBean> list = hr.getLimitPicture(uid, st, et, 5);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(list == null){
			out.write("no picture");
		}else{
			out.write(JsonUtil.createJsonString("picture", list));
		}
	}

}