package com.picserver.servlet.image;
import java.awt.Rectangle;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
public class Scale {
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
        	 int wide = n, high = n;
 	       
 	        	try {    
 	        		infoS = new ImageInfo(imgPath);    
 	        		image = new MagickImage(infoS);    
 	        		scaled = image.scaleImage(wide,high);
 	        		scaled.setFileName(toPath+"/"+i_lev + "/" + "0_0.jpg");    
 	        		scaled.writeImage(infoS);    
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
		cutImg(imgpath,topath,filename,255,255,0,7);
	} catch (MagickException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}
