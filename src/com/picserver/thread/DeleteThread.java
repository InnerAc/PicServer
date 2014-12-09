package com.picserver.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.MapfileUtils;

public class DeleteThread extends Thread {

	private MapfileBean mapfile;
	private PictureBean pic;
	


	public  DeleteThread(MapfileBean mapfile,PictureBean pic){
		this.mapfile=mapfile;
		this.pic=pic;
	}
	
	public void run(){
		//如果标记的数目大于等于总文件数目的一半就对mapfile进行重写
		 int flagnum=Integer.parseInt(mapfile.getFlagNum());
		int picnum=Integer.parseInt(mapfile.getPicNum());
		if(flagnum>=picnum/2){
			//获取在同一个mapfile下的image
			HbaseReader reader=new HbaseReader();
			List<PictureBean> piclist;
			try {
				piclist = reader.getPictureBean("attr", "path", pic.getPath());
			
			List<String>images=new ArrayList<String>();
			for(PictureBean picture:piclist){
				images.add(picture.getName());
			}
			//执行重写mapfile操作
			MapfileUtils mf=new MapfileUtils();
			mf.deleteImage(pic.getPath(), images);
			
			mapfile.setFlagNum("0");
			mapfile.setPicNum(Integer.toString(picnum-flagnum));
			HbaseWriter writer=new HbaseWriter();
			writer.putMapfileBean(mapfile);
			System.out.println("删除文件成功！");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
