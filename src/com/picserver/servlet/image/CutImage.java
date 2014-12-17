package com.picserver.servlet.image;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class CutImage {
	static int MAX_SIZE = 12;
	/*
	 * @param imgPath
	 * 					所需裁剪的图片路径
	 * @param toPath
	 * 					裁剪后图片保存的地址
	 * @param i_w
	 * 					元矩形宽
	 * @param i_h
	 * 					元矩形高
	 * @param mas_wide
	 * 					图片初始宽度
	 * @param mas_high
	 * 					图片初始高度
	 * */
	public static void cutImg(String imgPath, String toPath, int i_side,int mas_wide,int mas_high) throws MagickException {    
        ImageInfo infoS = null;    
        MagickImage image = null;    
        MagickImage cropped = null;    
        Rectangle rect = null;
        MagickImage scaled  = null;
        Double rate = (double) mas_high/mas_wide;			//高宽比
        
        File file=null;
        int min_lev = 0;
		int max_lev = 0;
        double tmp_size =  Math.log(mas_wide) / Math.log(2);
        
        max_lev = (int)tmp_size;
        
        if(tmp_size > max_lev)
        	max_lev++;
        
       // System.out.println(max_lev);
		infoS = new ImageInfo(imgPath);
		image = new MagickImage(infoS);
		
        if(max_lev > MAX_SIZE)
        	max_lev = MAX_SIZE;
        
        int i_lev = max_lev +1;
        double tmp_n,tmp_m;
        int n,m;
        int now_wide = mas_wide,now_high = mas_high;
        
        while(--i_lev >= 0){
        	//System.out.println(i_lev);
        	file = new File(toPath+"/"+i_lev);
        	file.mkdirs();
        	if(now_wide < 1)
        		now_wide = 1;
        	if(now_high < 1)
        		now_high = 1;
        	tmp_n = (double)now_wide/i_side;
        	tmp_m = (double)now_high/i_side;

        	n = (int)tmp_n;
        	m = (int)tmp_m;
     
        	if(tmp_n > n)n++;
        	if(tmp_m > m)m++;

        	if(m < 1)
        		m = 1;
        	if(n < 1)
        		n = 1;
        	
        	int nf_wide = now_wide - (n-1)*i_side,nf_high = now_high-(m-1)*i_side;
        	int cut_w = i_side,cut_h = i_side;
        	scaled = image.scaleImage(now_wide,now_high);
        	
        	for(int i=0;i<m;i++){
     			for(int j=0;j<n;j++){
     				cut_w = i_side;
     				cut_h = i_side;
     				if(i == m-1)
     					cut_h = nf_high;
     				if(j == n-1)
     					cut_w = nf_wide;
     				rect = new Rectangle(j*i_side, i*i_side,cut_w,cut_h);
     				cropped = scaled.cropImage(rect);    
     				byte[] blobout = cropped.imageToBlob(infoS); 
     				String fileName = toPath+"/"+i_lev+"/"+j+"_"+i+".jpg";    
     			}
     		}
        	now_wide /= 2;
        	now_high /= 2;
        		
        }
    }
	/*
	 * @param	topath
	 * 					dzi文件保存的路径和+文件名
	 * @param	i_size
	 * 					元图的尺寸
	 * */
	public static void write_dzi(String topath,int i_size,int wide,int high){
		PrintWriter writer;
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Image TileSize=\""+i_size+"\" Overlap=\"1\" Format=\"jpg\" xmlns=\"http://schemas.microsoft.com/deepzoom/2008\"><Size Width=\""+wide+"\" Height=\""+high+"\"/></Image>";
		try {
			writer = new PrintWriter(topath, "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] arg){
		String imgpath = "/home/had/1.jpg";
		String topath = "/var/www/html/picloud/Public/plugins/seadragon/";
		String filename = "test";
		int wide,high,i_size;
		wide = 1366;
		high = 768;
		i_size = 512;
		topath += filename;
		write_dzi(topath+".dzi",i_size,wide,high);
		try {
			cutImg(imgpath,topath+"_files",i_size,wide,high);
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
