package com.picserver.servlet.image;

import java.awt.Rectangle;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class CutImage {

		/*
		 * @param imgPath
		 * 					所需裁剪的图片路径
		 * @param toPath
		 * 					裁剪后图片保存的地址
		 * @param fileName
		 * 					保存后的文件名
		 * @param i_w
		 * 					元矩形宽
		 * @param i_h
		 * 					元矩形高
		 * @param min_lev
		 * 					最小切割级数
		 * @param max_lev
		 * 					最大切割级数
		 * */
		public static void cutImg(String imgPath, String toPath,String fileName, int i_w, int i_h,int min_lev,int max_lev) throws MagickException {    
		        ImageInfo infoS = null;    
		        MagickImage image = null;    
		        MagickImage cropped = null;    
		        Rectangle rect = null;
		        MagickImage scaled  = null;
		        int i_lev = min_lev-1;
		        while(i_lev<max_lev){
		        	i_lev++;
		        	 int n =(int) java.lang.Math.pow(2,i_lev);
		        	 int wide = n*i_w, high = n*i_h;
		 	       
		 	        	try {    
		 	        		infoS = new ImageInfo(imgPath);    
		 	        		image = new MagickImage(infoS);    
		 	        		scaled = image.scaleImage(wide,high);
//		 	        		scaled.setFileName(toPath+"_"+i_lev);    
//		 	        		scaled.writeImage(infoS);    
		 	        		for(int i=0;i<n;i++){
		 	        			for(int j=0;j<n;j++){
		 	        				rect = new Rectangle(j*i_w, i*i_h,i_w, i_h);    
		 	        				cropped = scaled.cropImage(rect);    
		 	        				System.out.println(toPath+(i_lev+8) +"/"+ j +"_"+i+".jpg");
		 	        				cropped.setFileName(toPath+(i_lev+8) +"/"+ j +"_"+i+".jpg");    
		 	        				cropped.writeImage(infoS);    
		 	        			}
		 	        		}
		 	        	} finally {    
		 	        		if (cropped != null) {    
		 	        			cropped.destroyImages();    
		 	        		}    
		 	        	}    
		        }
		       
		    }
		public static void main(String[] arg){
			String imgpath = "/home/had/43433.jpg";
			String topath = "/home/had/zoom/";
			String filename = "pic";
			//System.out.println(System.getProperty("java.library.path"));
			
			try {
				cutImg(imgpath,topath,filename,255,255,0,3);
			} catch (MagickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
