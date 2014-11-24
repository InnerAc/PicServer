package com.picserver.picture;

import java.awt.Dimension;    
import java.awt.Rectangle;    
import java.text.SimpleDateFormat;    
import java.util.Date;

import magick.CompositeOperator;    
import magick.CompressionType;    
import magick.DrawInfo;    
import magick.ImageInfo;    
import magick.MagickException;    
import magick.MagickImage;    
import magick.PixelPacket;    
import magick.PreviewType;  

public class PictureUtils {
	private MagickImage image;
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
}
