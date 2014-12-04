package com.picserver.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.io.SequenceFile;

import com.picserver.bean.PictureBean;
import com.picserver.config.SystemConfig;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;
import com.picserver.hdfs.SequencefileUtils;

public class FileUtils {
	private static double MAX_SYNC_SIZE = 1.0;
	private static String LOCAL_UPLOAD_ROOT  =  "/upload";
	private static String HDFS_UPLOAD_ROOT = "/upload";
	//TODO 全局变量设置
	
	public static PictureBean searchFile(FileItem item) {
		HbaseReader hr = new HbaseReader();
		String imageName = item.getName();
		PictureBean image = hr.getPictureBean(imageName);
		if(image != null){
			return image;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取文件集合大小
	 * @author Jet-Muffin
	 * @param List<FileItem> 文件集合
	 * @return
	 */
	public static  long fileListLength(List<FileItem> items){
		long TotalLength = 0;
		for(FileItem item:items){
			TotalLength += item.getSize();
		}
		return TotalLength;
	}
	
	/**
	 * 直接将文件集合传到HDFS
	 * 
	 * @author Jet-Muffin
	 * @param List
	 * @param items 文件集合
	 * @param fileName
	 *            HDFS中文件夹名
	 * @return
	 */
	public  boolean uploadToHdfs(FileItem item, String uid) {
		try {
			boolean flag;
			
			//HDFS文件名
			final String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid + "/LargeFile/" +  getDateNum();
			String filePath = hdfsPath + item.getName();
			
			InputStream uploadedStream = item.getInputStream();
			HdfsUtil hdfs = new HdfsUtil();	
			System.out.println(hdfsPath);
			flag = hdfs.upLoad(uploadedStream, hdfsPath);
			// hbase操作
			PictureBean image = new PictureBean(item);
			HbaseWriter writer = new HbaseWriter();
			image.setIsCloud("true");
			image.setPath(hdfsPath);
			writer.putPictureBean(image);
			
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *  将图片缓存至本地
	 * @param FileItem item 文件集合
	 * @param File 本地文件对象
	 * @return
	 */
	public  boolean uploadToLocal(FileItem  item,  final String uid) {
		try {		
				
				//本地目录为“根目录/用户名/时间戳"
			final String LocalPath = SystemConfig.getSystemPath()
					+ LOCAL_UPLOAD_ROOT + "/" + uid;
				
				//文件是否存在
			    File LocalDir = new File(LocalPath);
	            if(!LocalDir.exists()){
	            	LocalDir.mkdir();
	            }	 
	            
				String fileName = item.getName();
				File file = new File(LocalPath, fileName);
				System.out.println(file.getPath());
				if (file.exists()) {
					System.out.println("Local file exists!");
					return false;
				} else {
					item.write(file);
					System.out.println("Local file write success!");
				}
				
				// Hbase操作
				PictureBean image = new PictureBean(item);
				HbaseWriter writer = new HbaseWriter();
				image.setIsCloud("false");
				image.setPath(LocalPath);
				writer.putPictureBean(image);
				
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 本地与云端同步操作（线程）
	 * @author Jet-Muffin
	 * @param LocalPath
	 * @throws IOException
	 */
	public  void  localDirSync(String LocalPath, String uid) throws IOException {
		File LocalDir = new File(LocalPath);
		double DirSize = getDirSize(LocalDir);
		String filePath =  HDFS_UPLOAD_ROOT + "/" + uid + "/SmallFile/" + getSecNum();
		
		if(DirSize > MAX_SYNC_SIZE) {
            File[] items = LocalDir.listFiles();    
            //文件按文件名排序
            Arrays.sort(items,new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            
            //暂时用Mapfile处理
            MapfileUtils.packageToHdfs(items,filePath);     
            deleteFile(LocalDir);
            System.out.println("同步成功！");
            //SequencefileUtils.getSyncPosition(filePath);
		}
		System.out.println(DirSize);
	}
	
	
	/**
	 * 递归清空文件夹
	 * @author Jet-Muffin   
	 * @param file
	 */
	private  void deleteFile(File file) {
		if (file.exists()) {		// 判断文件是否存在
			if (file.isFile()) {	// 判断是否是文件
				file.delete();		// 删除文件
			} else if (file.isDirectory()) {	
				File[] files = file.listFiles();		
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
				file.delete();
			}
		} else {
			System.out.println("所删除的文件不存在");
		}
	}
	   
	/**
	 * 获取文件夹大小
	 * @author Jet-Muffin
	 * @param file
	 * @return double 文件夹大小
	 */
	public  double getDirSize(File file) {     
        if (file.exists()) {     
            if (file.isDirectory()) {     
                File[] children = file.listFiles();     
                double size = 0;     
                for (File f : children)     
                    size += getDirSize(f);     
                return size;     
            } else {				//如果是文件则直接返回其大小,以“兆”为单位   
                double size = (double) file.length() / 1024 / 1024;        
                return size;     
            }     
        } else {     
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");     
            return 0.0;     
        }     
    }     
	
	public  String getDateNum() {
		Calendar c = Calendar.getInstance();
		String year = Integer.toString(c.get(Calendar.YEAR));
		String month = Integer.toString(c.get(Calendar.MONTH));
		String date = Integer.toString(c.get(Calendar.DATE));
		return year+month+date;
	}
	
	public  String getSecNum() {
		Calendar c = Calendar.getInstance();
		String year = Integer.toString(c.get(Calendar.YEAR));
		String month = Integer.toString(c.get(Calendar.MONTH));
		String date = Integer.toString(c.get(Calendar.DATE));
		String hour = Integer.toString(c.get(Calendar.HOUR));
		String minute = Integer.toString(c.get(Calendar.MINUTE));
		String second = Integer.toString(c.get(Calendar.SECOND));
		return year+month+date+hour+minute+second;
	}
}


