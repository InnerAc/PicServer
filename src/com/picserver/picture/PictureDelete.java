package com.picserver.picture;

import java.io.File;
import java.io.IOException;

import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.thread.DeleteThread;

public class PictureDelete {

    private static final String BigFile="HdfsLargeFile";
    private static final String SmallFile="HdfsSmallFile";
    private static final String LocalFile="LocalFile";
    private static final String Deleted="deleted";
    
    /**
     * 删除图片
     * @param pic 要删除的图片
     * @return 返回成功与否的标志
     */
	public boolean deletePicture(PictureBean pic){
		String status=pic.getStatus();
		boolean flag=false;
		HbaseWriter writer=new HbaseWriter();
		HbaseReader reader=new HbaseReader();

		//根据图片状态的不同采取不同的方式
		
		try {
		if(status.equals(BigFile)){
			HdfsUtil hd;
			
				hd = new HdfsUtil();
			
			flag=hd.deletePath(pic.getPath());
			writer.deletePictureBean(pic);
			System.out.println("大文件删除成功");
			
		}
		else if(status.equals(SmallFile)){
			
			//  将小文件和对应的mapfile进行标记
			pic .setStatus(Deleted);
			String path=pic.getPath();
			String map_key=path.substring(path.length()-14, path.length())+pic.getUsr();
			MapfileBean mapfile=reader.getMapfileBean(map_key);
		   int flagnum=Integer.parseInt(mapfile.getFlagNum())+1;
			mapfile.setFlagNum(Integer.toString(flagnum));
			writer.putPictureBean(pic);
			writer.putMapfileBean(mapfile);
            flag=true;
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
		//更新用户和空间信息
		update(pic);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 直接删除图片，如果是小图片直接删掉mapfile，在删除space使用
	 * @param pic
	 * @return
	 */
	public boolean deletePictures(PictureBean pic){
		String status=pic.getStatus();
		boolean flag=false;
		HbaseWriter writer=new HbaseWriter();
		HbaseReader reader=new HbaseReader();

		//根据图片状态的不同采取不同的方式
		
		try {
		if(status.equals(BigFile)){
			HdfsUtil hd;
			
				hd = new HdfsUtil();
			
			flag=hd.deletePath(pic.getPath());
			writer.deletePictureBean(pic);
			System.out.println("大文件删除成功");
			
		}
		else if(status.equals(SmallFile)){
			
			//  将小文件和对应的mapfile进行标记
			pic .setStatus(Deleted);
			String path=pic.getPath();
			String map_key=path.substring(path.length()-14, path.length())+pic.getUsr();
			MapfileBean mapfile=reader.getMapfileBean(map_key);
		   if(mapfile!=null){
			   HdfsUtil hu=new HdfsUtil();
			   flag=hu.deletePath(path);
			   System.out.println(" 删除mapfile成功！");
		   }else{
			   flag=true;
			   System.out.println("mapfile已经被删除！");
		   }
			
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
		//更新用户和空间信息
		update(pic);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 删除图片更新用户和空间信息
	 * @param pic 删除的图片
	 */
	public void update(PictureBean pic){
		
		HbaseWriter writer=new HbaseWriter();
		HbaseReader reader=new HbaseReader();
		SpaceBean space=reader.getSpaceBean(pic.getSpace()+pic.getUsr());
		UserBean user=reader.getUserBean(pic.getUsr());
		
		//空间和用户的图片数量减1
		int number=Integer.parseInt(space.getNumber())-1;
		space.setNumber(Integer.toString(number));
		user.setPicNum(Integer.toString(number));
		
		//空间和用户的容量减少
		double size=Double.parseDouble(space.getStorage())-Double.parseDouble(pic.getSize());
		space.setStorage(Double.toString(size));
		size=Double.parseDouble(user.getTotSize())-Double.parseDouble(pic.getSize());
		user.setTotSize(Double.toString(size));
		
		//将修改后的spaceh和user更新到数据库
		writer.putSpaceBean(space);
		writer.putUserBean(user);
	}
}
