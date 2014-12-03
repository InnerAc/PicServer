package com.picserver.hbase;

import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;

/**
 * 将Bean写入hbase
 * @author hadoop
 *
 */
public class HbaseWriter {
	
	HbaseOperation ho = new HbaseOperation();
	
	/**
	 * 将图片信息写入表cloud_picture
	 * @param pb 图片信息PictureBean
	 */
	public void putPictureBean(PictureBean pb){
		if(pb.getName()==null){
			//传值有问题，处理一下
			return ;	
		}
		ho.insertData("cloud_picture", pb.getName(), "attr", "size", pb.getSize());
		ho.insertData("cloud_picture", pb.getName(), "attr", "type", pb.getType());
		ho.insertData("cloud_picture", pb.getName(), "attr", "space", pb.getSpace());
		ho.insertData("cloud_picture", pb.getName(), "attr", "createTime", pb.getCreateTime());
		ho.insertData("cloud_picture", pb.getName(), "attr", "usr", pb.getUsr());
		ho.insertData("cloud_picture", pb.getName(), "var", "isCloud", pb.getIsCloud());
		ho.insertData("cloud_picture", pb.getName(), "var", "updateTime", pb.getUpdateTime());
		ho.insertData("cloud_picture", pb.getName(), "var", "visitCount", pb.getVisitCount());
		ho.insertData("cloud_picture", pb.getName(), "var", "visitFlow", pb.getVisitFlow());
	}
	
	/**
	 * 将空间信息写入表cloud_space
	 * @param sb 空间信息SpaceBean
	 */
	public void putSpaceBean(SpaceBean sb){
		if(sb.getName()==null){
			//传值有问题，处理一下
			return ;	
		}
		ho.insertData("cloud_space",sb.getName(), "attr", "desc", sb.getDesc());
		ho.insertData("cloud_space",sb.getName(), "attr", "cover", sb.getCover());
		ho.insertData("cloud_space",sb.getName(), "var", "storage", sb.getStorage());
		ho.insertData("cloud_space",sb.getName(), "var", "number", sb.getNumber());
		ho.insertData("cloud_space",sb.getName(), "var", "flow", sb.getFlow());
	}

}
