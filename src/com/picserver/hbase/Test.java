package com.picserver.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;

import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;

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
//		pb.setName("ha");
//		pb.setCreateTime("ssss");
//		pb.setIsCloud("fa");
//		pb.setSize("ss");
//		pb.setSpace("ss");
//		pb.setType("sdf");
//		pb.setUpdateTime("sdfff");
//		pb.setUsr("sdddd");
//		pb.setVisitCount("ss");
//		pb.setVisitFlow("ss");
//		HbaseWriter hw = new HbaseWriter();
//		hw.putPictureBean(pb);
//		
//		HbaseReader hr = new HbaseReader();
//		PictureBean p = hr.getPictureBean("haha");
//		System.out.println(p.getCreateTime());
//		System.out.println(p.getIsCloud());
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
//			List<PictureBean> list = hr.getPictureBean("attr", "size", "ss");
//			System.out.print(list.size());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		HbaseOperation ho = new HbaseOperation();
//		ho.dropTable("cloud_picture");
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
//		HbaseOperation ho = new HbaseOperation();
//		ho.deleteRow("cloud_picture", "ha");
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
		
//		SpaceBean sb = new SpaceBean();
//		sb.setCover("a");
//		sb.setDesc("b");
//		sb.setFlow("c");
//		sb.setName("d");
//		sb.setNumber("e");
//		sb.setStorage("f");
//		HbaseWriter hw = new HbaseWriter();
//		hw.putSpaceBean(sb);
//		System.out.println("hahahahahha");
		
//		HbaseReader hr = new HbaseReader();
//     SpaceBean sb1 = hr.getSpaceBean("d");
//		System.out.println(sb1.getDesc());
//		System.out.println(sb1.getCover());
//		System.out.println(sb1.getFlow());
//		System.out.println(sb1.getNumber());
//		System.out.println(sb1.getStorage());
		
		/*-------------------------------------------------------------------------------------------------------------------------------*/		
		
//		HbaseReader hr = new HbaseReader();
//		try {
//			List<SpaceBean> list = hr.getSpaceBean("attr","cover", "as");
//			System.out.print(list);
////			System.out.print(list.get(0).getNumber());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        
	}

}
