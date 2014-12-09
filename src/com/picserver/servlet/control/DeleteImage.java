package com.picserver.servlet.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;
import com.picserver.thread.DeleteThread;

/**
 * Servlet implementation class DeleteImage
 */
@WebServlet("/DeleteImage")
public class DeleteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String BigFile="HdfsLargeFile";
    private static final String SmallFile="HdfsSmallFile";
    private static final String LocalFile="LocalFile";
    private static final String Deleted="deleted";
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
		System.out.println(pic.getName());
		System.out.println(pic.getSpace());
		String space_key=pic.getSpace();
		SpaceBean space=reader.getSpaceBean(space_key);
		int number=Integer.parseInt(space.getNumber())-1;
		space.setNumber(Integer.toString(number));
		double size=Double.parseDouble(space.getStorage())-Double.parseDouble(pic.getSize());
		space.setStorage(Double.toString(size));
		//将修改后的space更新到数据库
		HbaseWriter writer=new HbaseWriter();
		writer.putSpaceBean(space);
		//根据图片状态的不同采取不同的方式
		String status=pic.getStatus();
		boolean flag=true;

		if(status.equals(BigFile)){
			HdfsUtil hd=new HdfsUtil();
			flag=hd.deletePath(pic.getPath());
			writer.deletePictureBean(pic);
			System.out.println("大文件删除成功");
			
		}
		else if(status.equals(SmallFile)){
			
			//  将小文件和对应的mapfile进行标记
			pic .setStatus(Deleted);
			String path=pic.getPath();
			String map_key=path.substring(path.length()-14, path.length());
			MapfileBean mapfile=reader.getMapfileBean(map_key);
		   int flagnum=Integer.parseInt(mapfile.getFlagNum())+1;
			mapfile.setFlagNum(Integer.toString(flagnum));
			writer.putPictureBean(pic);
			writer.putMapfileBean(mapfile);
            flag=false;
			System.out.println("成功对小文件进行标记！");
			
			//创建线程检查mapfile是否需要重写
			DeleteThread dt=new DeleteThread(mapfile,pic);
			dt.start();
			
		}
		else if(status.equals(LocalFile)){
			File f=new File(pic.getPath(),pic.getName());
			if(f.exists())
			{
				flag=f.delete();
				writer.deletePictureBean(pic);
				System.out.println("本地文件删除成功");
			}
		}
		
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
