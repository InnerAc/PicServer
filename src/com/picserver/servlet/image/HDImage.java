package com.picserver.servlet.image;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class HDImage
 */
@WebServlet("/HDImage")
public class HDImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int M = 8;
	static String str[] = new String[M*M];
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HDImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			int base_x = Integer.parseInt(request.getParameter("base_x"));
			int base_y = Integer.parseInt(request.getParameter("base_y"));
			int mouse_x = Integer.parseInt(request.getParameter("mouse_x"));
			int mouse_y = Integer.parseInt(request.getParameter("mouse_y"));
			int pre_lev = Integer.parseInt(request.getParameter("pre_lev"));
			int tar_lev = Integer.parseInt(request.getParameter("tar_lev"));
			
			int pre_wide = (int)java.lang.Math.pow(2,pre_lev);
			int tar_wide = (int)java.lang.Math.pow(2,tar_lev);
			int now_x = base_x + mouse_x;
			int now_y = base_y + mouse_y;
			double rate = (double)tar_wide/pre_wide;
			
			now_x *= rate;
			now_y *= rate;
			now_x -= (M/2);
			now_y -= (M/2);
			
			if(now_x < 0)now_x = 0;
			if(now_y < 0)now_y = 0;
			if(now_x + M > tar_wide)now_x = tar_wide - M;
			if(now_y + M > tar_wide)now_y = tar_wide - M;
			
			int lea_x = now_x;
			int lea_y = now_y;
			
			for(int i=0;i<M;i++){
				for(int j=0;j<M;j++){
					str[i*M+j] = "pic_"+tar_lev+"_"+(i+lea_x)+"_"+(j+lea_y)+".jpg";
				}
			}
			
			JSONArray jsonarray = JSONArray.fromObject(str);
			PrintWriter out = response.getWriter();
			out.println(jsonarray);
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
