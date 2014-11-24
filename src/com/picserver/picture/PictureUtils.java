package com.picserver.picture;

import magick.ImageInfo;    
import magick.MagickApiException;    
import magick.MagickException;    
import magick.MagickImage;    

public class PictureUtils {
	private MagickImage image;
	private ImageInfo info;
	/**
	 * 构造方法
	 * @author Jet-Muffin
	 * @param blob byte流
	 */
	public PictureUtils(byte[] blob) throws Exception{
		info=new ImageInfo();    
		image = new MagickImage(info,blob);    
	}
	
	public byte[] scaleImage (int width,int height){
		try{
	    	MagickImage scaledImage = image.scaleImage(width, height);    
	    	byte[] blobout = scaledImage.imageToBlob(info); 
	    	return blobout;		
		} catch (Exception e){	
			e.printStackTrace();
			return null;
		}
	}
}
