package com.picserver.servlet.control;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;

/**
 * Servlet implementation class DeleteImage
 */
@WebServlet("/DeleteImage")
public class DeleteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String BigFile="BigFile";
    private static final String SmallFile="SmallFile";
    private static final String LocalFile="LocalFile";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteImage() {
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
		//获取图片信息和相对应的sapce信息，并对space进行修改
		String rowkey=request.getParameter("image");
		HbaseReader reader=new HbaseReader();
		PictureBean pic= reader.getPictureBean(rowkey);
		String space_key=pic.getSpace();
		SpaceBean space=reader.getSpaceBean(space_key);
		int number=Integer.parseInt(space.getNumber())-1;
		space.setNumber(Integer.toString(number));
		int size=Integer.parseInt(space.getStorage())-Integer.parseInt(pic.getSize());
		space.setStorage(Integer.toString(size));
		//将修改后的space更新到数据库
		HbaseWriter writer=new HbaseWriter();
		writer.putSpaceBean(space);
		//根据图片状态的不同采取不同的方式
		String status=pic.getStatus();
		
		if(status==BigFile){
			HdfsUtil hd=new HdfsUtil();
			hd.deletePath(pic.getPath());
			System.out.println("大文件删除成功");
		}
		else if(status==SmallFile){
			
		}
		else if(status==LocalFile){
			File f=new File(pic.getPath());
			if(f.exists())
				f.delete();
			System.out.println("本地文件删除成功");
		}
	}

}
