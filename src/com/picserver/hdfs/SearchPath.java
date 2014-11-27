package com.picserver.hdfs;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import com.google.common.primitives.Bytes;

public class SearchPath {
	
	private static String TableName="test";//数据库名字
	private static String ROW="row1";//行键
	private static String COLFAM="cf1";//列族
	private static String PATH="ID";//列名
	private static long BLOCK=-1000000000;//快大小
	
	//TODO 连接HBASE
	
//从hbase中查询需要写入SequenceFile的path，查询语句需修改
//private static String searchUserPath(){
//	HTable table=new HTable (HBaseConfiguration.creat(),Bytes.toBytes(TableName));
//	Get get=new Get(Bytes.toBytes(ROW));
//	get.addColumn(Bytes.toBytes(COLFAM),Bytes.toBytes(PATH));
//	Result result=table.get(get);
//	return Bytes.toString(result.list().get(0).value());
//}
////将新创建的SequenceFile的path写入数据库，返回boolean判断是否成功执行
//private static boolean addPath(String path){
//	HTable table=new HTable (HBaseConfiguration.creat(),Bytes.toBytes(TableName));
//	Put put =new Put(Bytes.toBytes(ROW));
//	if(put.add(Bytes.toBytes(COLFAM),Bytes.toBytes(PATH),Bytes.toBytes(path)))
//		return true;
//		else return false;
//}
//判断当前块剩余大小是否能够保存，否则创建一个新的文件，返回可用的SequenceFile的path
public static SequenceFile.Writer creatPath(FileItem item) throws IOException{
	
	 String hdfsUrl = "hdfs://localhost:9000";
		Configuration conf = new Configuration();
		FileSystem  fs = FileSystem.newInstance(URI.create(hdfsUrl),conf);
		
//	String s=searchUserPath();//获取当前可存入文件的path TODO 查询HBASE获得可写入的path
	String s = "path";
		//打开相应的sequencefile，判断是否能够存储
		
	Path seq_path = new Path(fs.getHomeDirectory(),s);
	fs.setReplication(seq_path, (short) 1);
	SequenceFile.Writer writer = SequenceFile.createWriter(conf,
            SequenceFile.Writer.keyClass(Text.class),
            SequenceFile.Writer.valueClass(BytesWritable.class),
            SequenceFile.Writer.stream(fs.append(seq_path)) 
            );
	if(writer.getLength()+item.getSize()<BLOCK){
		   String str=null;//定义创建文件的名字
//		   addPath(str);  //TODO 添加新块
		   //创建新的文件流，并返回
			seq_path = new Path(fs.getHomeDirectory(),str);
			fs.create(seq_path);
			   fs.close();
			   //打开SequenceFile进行写入
				 fs = FileSystem.newInstance(URI.create(hdfsUrl),conf);
			fs.setReplication(seq_path, (short) 1);
			writer = SequenceFile.createWriter(conf,
		            SequenceFile.Writer.keyClass(Text.class),
		            SequenceFile.Writer.valueClass(BytesWritable.class),
		            SequenceFile.Writer.stream(fs.append(seq_path)) 
		            ); 
			 return writer;
	}
	 return writer;
	
}


}
