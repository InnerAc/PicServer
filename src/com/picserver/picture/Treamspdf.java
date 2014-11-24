package com.picserver.picture;

import magick.ImageInfo;    
import magick.MagickApiException;    
import magick.MagickException;    
import magick.MagickImage;    
      
public class Treamspdf {  

    public static byte[] resetsize(byte[] blob) throws MagickException{  

    	ImageInfo info=new ImageInfo();    
    	MagickImage image=new MagickImage(info,blob);    
    	MagickImage scaled=image.scaleImage(200, 200);    
    	byte[] blobout = scaled.imageToBlob(info);      
        return blobout;
    }  
}
