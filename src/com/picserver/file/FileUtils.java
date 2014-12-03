package com.picserver.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.hadoop.io.SequenceFile;

import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;
import com.picserver.hdfs.SequencefileUtils;

public class FileUtils {
	private static double MAX_SYNC_SIZE = 2.0;
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
	 *            <FileItem> items 文件集合
	 * @param fileName
	 *            HDFS中文件夹名
	 * @return
	 */
	public static boolean uploadToHdfs(FileItem item, String FileName) {
		try {
			boolean flag;
			InputStream uploadedStream = item.getInputStream();
			HdfsUtil hdfs = new HdfsUtil();
			String hdfsPath = com.picserver.hdfs.HdfsConfig.getHDFSPath()
					+ item.getName();
			System.out.println(hdfsPath);
			flag = hdfs.upLoad(uploadedStream, hdfsPath);
			// TODO hbase操作
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *  将图片缓存至本地
	 * @param List <FileItem> items 文件集合
	 * @param File 本地文件对象
	 * @return
	 */
	public static boolean uploadToLocal(FileItem  item, String LocalPath) {
		try {	
			if (item.isFormField()) {
				String name = item.getFieldName();
				System.out.println(name);
			} else {
				String fileName = item.getName();
				File file = new File(LocalPath, fileName);
				System.out.println(file.getPath());
				if (file.exists()) {
					System.out.println("文件已存在！（本地）");
					return false;
				} else {
					item.write(file);
					System.out.println("已写入本地缓存");
				}
				// TODO hdfs操作
			}
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
	public static void  localDirSync(String LocalPath) throws IOException {
		File LocalDir = new File(LocalPath);
		double DirSize = getDirSize(LocalDir);
		//System.out.println(LocalPath);
		if(DirSize > MAX_SYNC_SIZE) {
            File[] items = LocalDir.listFiles();    
            Arrays.sort(items,new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            String filePath =  "/test/seq/test.map";
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
	private static void deleteFile(File file) {
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
	public static double getDirSize(File file) {     
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
}


