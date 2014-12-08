package com.picserver.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;

import com.picserver.bean.LogBean;
import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.bean.UserBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.DateUtil;
import com.picserver.utils.JsonUtil;

public class Test {

	public static void main(String[] args) {
		
//		HbaseOperation hbaseOperation = new HbaseOperation();
//		hbaseOperation.insertData("cloud_picture", "picName", "attr", "aype",
//				"jpg");
//		Result r = hbaseOperation.QueryByRowKey("cloud_picture", "piName");
//		if (r.isEmpty()) {
//			System.out.print("ss");
//		} else {
//			System.out.println("获得到rowkey:" + new String(r.getRow()));
//			for (KeyValue keyValue : r.raw()) {
//				System.out.println("列族：" + new String(keyValue.getFamily())
//						+ "  列:" + new String(keyValue.getQualifier()) + "  值:"
//						+ new String(keyValue.getValue()));
//			}
//		}
		
/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
		
//		PictureBean pb = new PictureBean();
//		pb.setName("tupian");
//		pb.setCreateTime("");
//		pb.setPath("dd");
//		pb.setStatus("dd");
//		pb.setSize("ss");
//		pb.setSpace("空间");
//		pb.setType("sdf");
//		pb.setUpdateTime("sdfff");
//		pb.setUsr("admin1");
//		pb.setVisitCount("ss");
//		pb.setVisitFlow("ss");
//		HbaseWriter hw = new HbaseWriter();
//		hw.putPictureBean(pb);
//		
//		HbaseReader hr = new HbaseReader();
//		PictureBean p = hr.getPictureBean("ha");
//		System.out.println(p.getCreateTime());
//		System.out.println(p.getStatus());
//		System.out.println(p.getPath());
//		System.out.println(p.getSize());
//		System.out.println(p.getSpace());
//		System.out.println(p.getType());
//		System.out.println(p.getUpdateTime());
//		System.out.println(p.getUsr());
//		System.out.println(p.getVisitCount());
//		System.out.println(p.getVisitFlow());

		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
//		HbaseReader hr = new HbaseReader();
//		try {
//			List<PictureBean> list = hr.getPictureBean("admin", "space");
//			System.out.print(list.size());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
//		try {
//			System.out.print(DateUtil.getCurrentDateStr());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
//		
//		HbaseOperation ho = new HbaseOperation();
//		ho.dropTable("cloud_log");
		
		HbaseOperation ho = new HbaseOperation();
		ho.dropTable("cloud_picture");
		CreateHbase ch = new CreateHbase();
		String name1 = "cloud_picture";
		String[] column = { "attr", "var" };
		ch.createTable(name1, column);
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
//		HbaseOperation ho = new HbaseOperation();
//		ho.deleteRow("cloud_picture", "ha");



		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		
//		SpaceBean sb = new SpaceBean();
//		sb.setCover("a");
//		sb.setDesc("b");
//		sb.setUid("id");
//		sb.setFlow("c");
//		sb.setName("jijij");
//		sb.setNumber("e1");
//		sb.setStorage("344.34353637");
//		HbaseWriter hw = new HbaseWriter();
//		hw.putSpaceBean(sb);
//		System.out.println("hahahahahha");
		
		
//		HbaseReader hr = new HbaseReader();
//        SpaceBean sb1 = hr.getSpaceBean("图片");
//		System.out.println(sb1.getDesc());
//		System.out.println(sb1.getCover());
//		System.out.println(sb1.getFlow());
//		System.out.println(sb1.getNumber());
//		System.out.println(sb1.getStorage());
//		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		HbaseReader hr = new HbaseReader();
//		try {
//			List<SpaceBean> list = hr.getSpaceBean("attr","uid", "id");
//			System.out.println(JsonUtil.createJsonString("space", list));
////			System.out.println(list.get(0).getDesc());
////			System.out.println(list.get(1).getDesc());
////			System.out.print(list.get(0).getNumber());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		UserBean ub = new UserBean();
//		ub.setUid("uid2");
//		ub.setAccType("acc");
//		ub.setEmail("email");
//		ub.setLastLogin("dddd");
//		ub.setNickname("name");
//		ub.setPicNum("1");
//		ub.setPwd("pwd");
//		ub.setSpaceNum("spacenum");
//		ub.setTotSize("size");
//		ub.setWebsite("web");
//		
//		HbaseWriter hw = new HbaseWriter();
//		hw.putUserBean(ub);
		
//		HbaseReader hr = new HbaseReader();
//		UserBean ub1 = hr.getUserBean("uid");
//		System.out.println(ub1.getAccType());
//		System.out.println(ub1.getEmail());
//		System.out.println(ub1.getLastLogin());
//		System.out.println(ub1.getNickname());
//		System.out.println(ub1.getPicNum());
//		System.out.println(ub1.getPwd());
//		System.out.println(ub1.getSpaceNum());
//		System.out.println(ub1.getTotSize());
//		System.out.println(ub1.getWebsite());
		
//		HbaseReader hr = new HbaseReader();
//		
//		try {
//			List<UserBean> list = hr.getUserBean("attr", "email", "email");
//			System.out.print(list.size());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		try {
//			System.out.print(DateUtil.getCurrentDateStr());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		HbaseWriter hw = new HbaseWriter();
//		LogBean lb = new LogBean("w", "oooo");
//		hw.putLogBean(lb);
		
//		HbaseReader hr = new HbaseReader();
//		try {
//			List<LogBean> list  = hr.getLogBean("wo","20141204162803","20141204163206");
//			for(int i = 0; i < list.size(); i++){
//				System.out.println(list.get(i).getUser()+list.get(i).getTime());
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
