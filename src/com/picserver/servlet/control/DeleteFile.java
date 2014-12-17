package com.picserver.servlet.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.hdfs.HdfsUtil;

/**
 * 删除图片
 * @author Jet-Muffin
 *
 */
@WebServlet("/DeleteFile")
public class DeleteFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteFile() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String image = request.getParameter("image");
		String fileDir = "/home/had/jspworkspace/tmp/test";
		String filePath = fileDir + '/' + image;
		File file = new File(filePath);
		boolean flag = false;

		if (!file.exists()) {
			System.out.println("文件不存在！");
		} else {
			file.delete();
			flag = true;
		}

		if (flag) {
			response.sendRedirect("success.jsp");
		} else {
			response.sendRedirect("failure.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
