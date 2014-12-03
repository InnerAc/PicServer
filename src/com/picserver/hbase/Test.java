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
		
		
//		PictureBean pb = new PictureBean();
//		System.out.print(pb.getIsCloud());
//		SpaceBean sb = new SpaceBean();
//		sb.setCover("ss");
//		sb.setDesc("ss");
//		sb.setFlow("sss");
//		sb.setName("ss");
//		sb.setNumber("sss");
//		sb.setStorage("sss");
//		HbaseWriter hw = new HbaseWriter();
//		hw.putSpaceBean(sb);
//		System.out.println("hahahahahha");
		
//		HbaseReader hr = new HbaseReader();
//     SpaceBean sb1 = hr.getSpaceBean("ss");
//		System.out.println(sb1.getDesc());
		
		HbaseReader hr = new HbaseReader();
		try {
			List<SpaceBean> list = hr.getSpaceBean("cover", "ss");
			System.out.print(list.size());
			System.out.print(list.get(0).getNumber());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
