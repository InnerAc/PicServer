package com.picserver.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
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
			pb.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					pb.setName(val);
				}
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
			}
			list.add(pb);
		}
		rs.close();
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
			sb.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					sb.setName(val);
				}
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
			}
			list.add(sb);
		}
		rs.close();
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
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
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
		rs.close();
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
			hb.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					hb.setName(val);
				}
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
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

}
