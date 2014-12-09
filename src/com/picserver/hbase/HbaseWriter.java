package com.picserver.hbase;

import org.apache.hadoop.io.MapFile;

import com.picserver.bean.HdBean;
import com.picserver.bean.LogBean;
import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;

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
		if((pb == null)||(pb.getName().equals(""))){
			//传值有问题，处理一下
			return ;	
		}
		ho.insertData("cloud_picture", pb.getName(), "attr", "size", pb.getSize());
		ho.insertData("cloud_picture", pb.getName(), "attr", "type", pb.getType());
		ho.insertData("cloud_picture", pb.getName(), "attr", "space", pb.getSpace());
		ho.insertData("cloud_picture", pb.getName(), "attr", "createTime", pb.getCreateTime());
		ho.insertData("cloud_picture", pb.getName(), "attr", "usr", pb.getUsr());
		ho.insertData("cloud_picture", pb.getName(), "attr", "path", pb.getPath());
		ho.insertData("cloud_picture", pb.getName(), "var", "status", pb.getStatus());
		ho.insertData("cloud_picture", pb.getName(), "var", "updateTime", pb.getUpdateTime());
		ho.insertData("cloud_picture", pb.getName(), "var", "visitCount", pb.getVisitCount());
		ho.insertData("cloud_picture", pb.getName(), "var", "visitFlow", pb.getVisitFlow());
		
	}
	
	/**
	 * 将空间信息写入表cloud_space
	 * @param sb 空间信息SpaceBean
	 */
	public void putSpaceBean(SpaceBean sb){
		if((sb == null)||(sb.getName().equals(""))){
			//传值有问题，处理一下
			return ;	
		}
		ho.insertData("cloud_space",sb.getName(), "attr", "desc", sb.getDesc());
		ho.insertData("cloud_space",sb.getName(), "attr", "cover", sb.getCover());
		ho.insertData("cloud_space",sb.getName(), "attr", "uid", sb.getUid());
		ho.insertData("cloud_space",sb.getName(), "var", "storage", sb.getStorage());
		ho.insertData("cloud_space",sb.getName(), "var", "number", sb.getNumber());
		ho.insertData("cloud_space",sb.getName(), "var", "flow", sb.getFlow());
	}
	
	/**
	 * 将用户信息写入cloud_user
	 * @param ub 
	 */
	public void putUserBean(UserBean ub){
		if((ub == null)||(ub.getUid().equals(""))){
			//传值有问题，处理一下
			return ;
		}
		ho.insertData("cloud_user", ub.getUid(), "attr", "accType", ub.getAccType());
		ho.insertData("cloud_user", ub.getUid(), "attr", "email", ub.getEmail());
		ho.insertData("cloud_user", ub.getUid(), "attr", "lastLogin", ub.getLastLogin());
		ho.insertData("cloud_user", ub.getUid(), "attr", "website", ub.getWebsite());
		ho.insertData("cloud_user", ub.getUid(), "vldt", "nickname", ub.getNickname());
		ho.insertData("cloud_user", ub.getUid(), "vldt", "pwd", ub.getPwd());
		ho.insertData("cloud_user", ub.getUid(), "pic", "picNum", ub.getPicNum());
		ho.insertData("cloud_user", ub.getUid(), "pic", "totSize", ub.getTotSize());
		ho.insertData("cloud_user", ub.getUid(), "space", "spaceNum", ub.getSpaceNum());
	}
	
	/**
	 * 将日志信息写入log表
	 * @param lb
	 */
	public void putLogBean(LogBean lb){
		if((lb == null)||(lb.getLogid().equals(""))){
			//传值有问题，处理一下
			return ;
		}
		ho.insertData("cloud_log", lb.getLogid(), "attr", "user", lb.getUser());
		ho.insertData("cloud_log", lb.getLogid(), "attr", "time", lb.getTime());
		ho.insertData("cloud_log", lb.getLogid(), "attr", "operation", lb.getOperation());
	}
	
	/**
	 * 将高清图片信息写入表
	 * @param hb
	 */
	public void putHdBean(HdBean hb){
		if((hb == null)||(hb.getName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		ho.insertData("cloud_hd", hb.getName(), "attr", "size", hb.getSize());
		ho.insertData("cloud_hd", hb.getName(), "attr", "uid", hb.getUid());
		ho.insertData("cloud_hd", hb.getName(), "attr", "createTime", hb.getCreateTime());
	}
	
	/**
	 * 将mapfile信息写入表
	 * @param mb
	 */
	public void putMapfileBean(MapfileBean mb){
		if((mb == null)||(mb.getName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		ho.insertData("cloud_mapfile",mb.getName(), "attr", "uid", mb.getUid());
		ho.insertData("cloud_mapfile",mb.getName(), "var", "flagNum", mb.getFlagNum());
		ho.insertData("cloud_mapfile",mb.getName(), "var", "picNum", mb.getPicNum());
	}
	
	/**
	 * 删除图片信息
	 * @param pic
	 */
	public void deletePictureBean(PictureBean pic){
		HbaseOperation  operation=new HbaseOperation();
		operation.deleteRow("cloud_picture", pic.getName());
	}
	/**
	 *  删除mapfile信息
	 * @param map mapfile
	 */
	public void deleteMapfileBean(MapfileBean map){
		HbaseOperation  operation=new HbaseOperation();
		operation.deleteRow("cloud_mapfile", map.getName());
	}
	
}
