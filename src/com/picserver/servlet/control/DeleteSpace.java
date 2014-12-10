package com.picserver.servlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.picture.PictureDelete;

/**
 * Servlet implementation class DeleteSpace
 */
@WebServlet("/DeleteSpace")
public class DeleteSpace extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSpace() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String space=request.getParameter("space");
		String user=request.getParameter("user");
		HbaseReader reader=new HbaseReader();
		HbaseWriter writer=new HbaseWriter();
		List<PictureBean> piclist=reader.getPictureBean(user, space);//检索该用户该空间下的所有图片
		SpaceBean spacebean=reader.getSpaceBean(space+user);
		UserBean userbean=reader.getUserBean(user);
		PictureDelete pd=new PictureDelete();
		boolean flag=false;
		
		//删除该空间下的所有图片
		for(PictureBean pic:piclist){
			flag=pd.detelePicture(pic);
		}
		
		//从数据库删除空间信息，更改用户信息
		int num=Integer.parseInt(userbean.getSpaceNum())-1;
		userbean.setSpaceNum(Integer.toString(num));
		writer.putUserBean(userbean);
		writer.deleteSpaceBean(spacebean);
		
		if(flag){
			response.setContentType("text/html;charset=gb2312");
			PrintWriter out = response.getWriter();
			out.println("删除成功!");
			response.setStatus(200);
			System.out.println("Upload success!");
		} else {
			response.setContentType("text/html;charset=gb2312");
			PrintWriter out = response.getWriter();
			out.println("删除失败!");					
			response.setStatus(302);
			System.out.println("Upload failed");
		}
	}

}
