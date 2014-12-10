package com.picserver.picture;

import java.awt.Dimension;    
import java.awt.Rectangle;    
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;    
import java.util.Date;
import java.util.List;

import com.picserver.hdfs.HdfsUtil;

import magick.CompositeOperator;    
import magick.CompressionType;    
import magick.DrawInfo;    
import magick.ImageInfo;    
import magick.MagickException;    
import magick.MagickImage;    
import magick.PixelPacket;    
import magick.PreviewType;  

public class PictureUtils {
	private  MagickImage image;
	private ImageInfo info;
	/**
	 * 构造方法
	 * @author Jet-Muffin
	 * @param blob 源图byte流
	 */
	public PictureUtils(byte[] blob) throws Exception{
		info=new ImageInfo();    
		image = new MagickImage(info,blob);    
	}
	
	/**
	 * 缩放
	 * @author Jet-Muffin
	 * @param width 缩放宽度
	 * @param height 缩放高度
	 * @return blob 新图byte流
	 */
	public byte[] scaleImage (int width,int height) throws MagickException {
	    	MagickImage scaledImage = image.scaleImage(width, height);    
	    	byte[] blobout = scaledImage.imageToBlob(info); 
	    	return blobout;		
	}
	
	public byte[] scaleImage (int width) throws MagickException {
		Dimension dim = image.getDimension();
		double wImage = dim.getWidth();
		double hImage = dim.getHeight();
		int height = (int) Math.floor(hImage / wImage * width);
		MagickImage scaledImage = image.scaleImage(width, height);
		byte[] blobout = scaledImage.imageToBlob(info);
		return blobout;
}
	
	/**
	 * 裁剪
	 * @param width 裁剪区域宽度
	 * @param height 裁剪区域高度
	 * @param offsetX  裁剪区域右上端点x
	 * @param offsetY  裁剪区域右上端点y
	 * @return  byte[] 新图byte流
	 */
	public byte[] cropImage(int width, int height, int offsetX, int offsetY) throws MagickException{
			Rectangle rect = new Rectangle(offsetX, offsetY, width, height);    
			MagickImage croppedImage = image.cropImage(rect);  
	    	byte[] blobout = croppedImage.imageToBlob(info); 
	    	return blobout;		
	}
	
	/**
	 * 水印（图片LOGO）
	 * @author Jet-Muffin
	 * @param mbyte 水印byte流
	 * @param mwidth  水印宽度
	 * @param mheight  水印高度
	 * @param offsetX  水印偏移x
	 * @param offsetY  水印偏移y
	 * @return
	 */
	public byte[] imgWaterMask(byte mbyte[], int mwidth, int mheight, int offsetX, int offsetY) throws MagickException {
			MagickImage mask = new MagickImage(new ImageInfo(),mbyte);
			mask = mask.scaleImage(mwidth, mheight);
			image.compositeImage(CompositeOperator.AtopCompositeOp, mask, offsetX, offsetY);
			byte[] blobout = image.imageToBlob(info);
			return blobout;
	}
	
	/**
	 * 水印（文字）
	 * @param text  文字内容
	 * @param fontsize  字体大小
	 * @param offsetX  偏移位置X
	 * @param offsetY   偏移位置Y
	 * @return  byte[] 图片字节流
	 * @throws MagickException
	 */
	public byte[] textWaterMask(String text, int fontsize,int offsetX, int offsetY) throws MagickException{
		DrawInfo textInfo = new DrawInfo(info);

		textInfo.setFill(PixelPacket.queryColorDatabase("black"));
		textInfo.setUnderColor(new PixelPacket(65535, 65535, 65535, 65535));// 设置为透明颜色
		String fontPath = "http://localhost:8080/PicServer/fonts/msyh.ttf";

		textInfo.setFont(fontPath);
		textInfo.setPointsize(fontsize);   
		textInfo.setTextAntialias(true);
		textInfo.setOpacity(0);// 透明度
		textInfo.setText(text);
		textInfo.setGeometry("+" + (offsetX + "+" + offsetY));
		image.annotateImage(textInfo);

		byte[] blobout = image.imageToBlob(info);
		return blobout;
	}
	
	
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
	public  boolean  cutImg(String hdfsPath,String fileName, int i_size) throws MagickException, IOException {    

        MagickImage cropped = null;    
        Rectangle rect = null;
        MagickImage scaled  = null;
   
		Dimension dim = image.getDimension();
		int mas_wide = (int) dim.getWidth();
		int mas_high = (int) dim.getHeight();
		
		int max_lev = 0;
        double tmp_size =  Math.log(mas_wide) / Math.log(2);
        
        max_lev = (int)tmp_size;
        
        if(tmp_size > max_lev)
        	max_lev++;

        int i_lev = max_lev +1;
        double tmp_n,tmp_m;
        int n,m;
        int now_wide = mas_wide,now_high = mas_high;
        
        while(--i_lev >= 0){
        	//System.out.println(i_lev);
        	if(now_wide < 1)
        		now_wide = 1;
        	if(now_high < 1)
        		now_high = 1;
        	tmp_n = (double)now_wide/i_size;
        	tmp_m = (double)now_high/i_size;

        	n = (int)tmp_n;
        	m = (int)tmp_m;
     
        	if(tmp_n > n)n++;
        	if(tmp_m > m)m++;

        	if(m < 1)
        		m = 1;
        	if(n < 1)
        		n = 1;
        	
        	int nf_wide = now_wide - (n-1)*i_size,nf_high = now_high-(m-1)*i_size;
        	int cut_w = i_size,cut_h = i_size;
        	scaled = image.scaleImage(now_wide,now_high);
        	
        	for(int i=0;i<m;i++){
     			for(int j=0;j<n;j++){
     				cut_w = i_size;
     				cut_h = i_size;
     				if(i == m-1)
     					cut_h = nf_high;
     				if(j == n-1)
     					cut_w = nf_wide;
     				rect = new Rectangle(j*i_size, i*i_size,cut_w,cut_h);
     				System.out.println( hdfsPath + '/'  + fileName + "_files" +  "/"+i_lev+"/"+j+"_"+i+".jpg");
     				cropped = scaled.cropImage(rect);    
     				byte[] blobout = cropped.imageToBlob(info); 
     				String RealPath = hdfsPath + '/'  + fileName + "_files" +  "/"+i_lev+"/"+j+"_"+i+".jpg";
     				try{
         				InputStream in = new ByteArrayInputStream(blobout); 
         				HdfsUtil hu = new HdfsUtil();
         				hu.upLoad(in, RealPath); 					
     				} catch (Exception e) {
     					e.printStackTrace();
     					return false;
     				}				
     			}
     		}
        	now_wide /= 2;
        	now_high /= 2;		
        }
        return true;
    }
	
	/*
	 * @param	topath
	 * 					dzi文件保存的路径和+文件名
	 * @param	i_size
	 * 					元图的尺寸
	 * */
	public  boolean write_dzi(String hdfsPath,String fileName,int i_size) throws Exception{
		Dimension dim = image.getDimension();
		int mas_wide = (int) dim.getWidth();
		int mas_high = (int) dim.getHeight();
		
		String RealPath = hdfsPath  + '/' +fileName + ".dzi";
		System.out.println(RealPath);
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Image TileSize=\""+i_size+"\" Overlap=\"1\" Format=\"jpg\" xmlns=\"http://schemas.microsoft.com/deepzoom/2008\"><Size Width=\""+mas_wide+"\" Height=\""+mas_high+"\"/></Image>";
		try {
			HdfsUtil hu = new HdfsUtil();
			hu.createFile(RealPath, str);
		} catch (Exception  e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
}
