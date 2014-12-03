package com.picserver.servlet.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Ajax
 */
@WebServlet("/Ajax")
public class Ajax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajax() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     request.setCharacterEncoding("UTF-8"); 
	        response.setContentType("text/html;charset=UTF-8");  
	        response.setHeader("Cache-Control","no-cache");  
	        response.setStatus(response.SC_OK);
	        response.setContentLength(1000);
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        PrintWriter out = response.getWriter();
	        out.print("aaabbbccc");
	        out.flush();  
	        out.close(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     request.setCharacterEncoding("UTF-8"); 
	        response.setContentType("text/html;charset=UTF-8");  
	        response.setHeader("Cache-Control","no-cache");  
	        response.setStatus(response.SC_OK);
	        response.setContentLength(1000);
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        PrintWriter out = response.getWriter();
	        out.print("aaabbbccc");
	        out.flush();  
	        out.close(); 
	}

}
