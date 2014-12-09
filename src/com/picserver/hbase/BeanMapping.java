package com.picserver.hbase;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;

import com.picserver.bean.MapfileBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;

/**
 * 将数据库读出的数据映射到Bean
 * @author hadoop
 *
 */
public class BeanMapping {

	/**
	 * 将数据库读出的数据映射到PictureBean
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public PictureBean pictureBeanMapping(Result rs, String rowkey){
		PictureBean pb = new PictureBean();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			pb.setName(rowkey);
			for (KeyValue keyValue : rs.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("size")) {
					pb.setSize(val);
				}
				if (v.equals("type")) {
					pb.setType(val);
				}
				if (v.equals("space")) {
					pb.setSpace(val);
				}
				if (v.equals("usr")) {
					pb.setUsr(val);
				}
				if (v.equals("createTime")) {
					pb.setCreateTime(val);
				}
				if (v.equals("path")) {
					pb.setPath(val);
				}
				if (v.equals("status")) {
					pb.setStatus(val);
				}
				if (v.equals("updateTime")) {
					pb.setUpdateTime(val);
				}
				if (v.equals("visitCount")) {
					pb.setVisitCount(val);
				}
				if (v.equals("visitFlow")) {
					pb.setVisitFlow(val);
				}
			}
		}
		return pb;
	}

	/**
	 * 将数据库读出的数据映射到SpaceBean
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public SpaceBean spaceBeanMapping(Result rs,String rowkey){
		SpaceBean sb = new SpaceBean();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			sb.setName(rowkey);
			for (KeyValue keyValue : rs.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("desc")) {
					sb.setDesc(val);
				}
				if (v.equals("cover")) {
					sb.setCover(val);
				}
				if (v.equals("uid")) {
					sb.setUid(val);
				}
				if (v.equals("storage")) {
					sb.setStorage(val);
				}
				if (v.equals("number")) {
					sb.setNumber(val);
				}
				if (v.equals("flow")) {
					sb.setFlow(val);
				}
			}
		}
		return sb;
	}
	
	/**
	 * 将数据库读出的数据映射到UserBean
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public UserBean userBeanMapping(Result rs,String rowkey){
		UserBean ub = new UserBean();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			ub.setUid(rowkey);
			for (KeyValue keyValue : rs.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("accType")) {
					ub.setAccType(val);
				}
				if (v.equals("email")) {
					ub.setEmail(val);
				}
				if (v.equals("lastLogin")) {
					ub.setLastLogin(val);
				}
				if (v.equals("website")) {
					ub.setWebsite(val);
				}
				if (v.equals("nickname")) {
					ub.setNickname(val);
				}
				if (v.equals("pwd")) {
					ub.setPwd(val);
				}
				if (v.equals("picNum")) {
					ub.setPicNum(val);
				}
				if (v.equals("totSize")) {
					ub.setTotSize(val);
				}
				if (v.equals("spaceNum")) {
					ub.setSpaceNum(val);
				}
			}
		}
		return ub;
		
	}
	
	/**
	 * 将数据库读出的数据映射到MapfileBean
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public MapfileBean mapfileMapping(Result rs,String rowkey){
		MapfileBean mb = new MapfileBean();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			mb.setName(rowkey);
			for (KeyValue keyValue : rs.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("uid")) {
					mb.setUid(val);
				}
				if (v.equals("flagNum")) {
					mb.setFlagNum(val);
				}
				if (v.equals("picNum")) {
					mb.setPicNum(val);
				}
			}
			return mb;
		}
	}
}
