package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import com.picserver.bean.*;

/**
 * 将数据库读出的数据映射到List
 * @author hadoop
 *
 */
public class ListMapping {
	
	/**
	 * 将数据库读出的数据映射到PictureBean 的 List
	 * @param rs
	 * @return
	 */
	public List<PictureBean> pictureListMapping(ResultScanner rs){
		List<PictureBean> list = new ArrayList<PictureBean>();
		for (Result r : rs) {
			PictureBean pb = new PictureBean();
			pb.setName(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
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
			list.add(pb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到SpaceBean 的 List
	 * @param rs
	 * @return
	 */
	public List<SpaceBean> spaceListMapping(ResultScanner rs){
		List<SpaceBean> list = new ArrayList<SpaceBean>();
		for (Result r : rs) {
			SpaceBean sb = new SpaceBean();
			sb.setName(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
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
			list.add(sb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到LogBean的 List
	 * @param rs
	 * @return
	 */
	public List<LogBean> logListMapping(ResultScanner rs){
		List<LogBean> list = new ArrayList<LogBean>();
		for (Result r : rs) {
			LogBean lb = new LogBean();
			lb.setLogid(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("user")) {
					lb.setUser(val);
				}
				if (v.equals("time")) {
					lb.setTime(val);
				}
				if (v.equals("operation")) {
					lb.setOperation(val);
				}
			}
			list.add(lb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到HdBean的List
	 * @param rs
	 * @return
	 */
	public List<HdBean> hdListMapping(ResultScanner rs){
		List<HdBean> list = new ArrayList<HdBean>();
		for (Result r : rs) {
			HdBean hb = new HdBean();
			hb.setName(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("uid")) {
					hb.setUid(val);
				}
				if (v.equals("createTime")) {
					hb.setCreateTime(val);
				}
				if (v.equals("size")) {
					hb.setSize(val);
				}
			}
			list.add(hb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

}
