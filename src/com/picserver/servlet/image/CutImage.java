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
	public static void cutImg(String imgPath, String toPath, int i_side,
			int mas_wide, int mas_high) throws MagickException {
		ImageInfo infoS = null;
		MagickImage image = null;
		MagickImage cropped = null;
		Rectangle rect = null;
		MagickImage scaled = null;
		Double rate = (double) mas_high / mas_wide; // 高宽比

		// System.out.println(rate);
		File file = null;
		int min_lev = 0;
		double max_lev = 0;
		double middle_size = Math.log(i_side) / Math.log(2);
		max_lev = Math.log(mas_wide / i_side) / Math.log(2);
		max_lev += middle_size;
		infoS = new ImageInfo(imgPath);

		if (max_lev > MAX_SIZE)
			max_lev = MAX_SIZE;

		// System.out.println("the mid = "+middle_size);
		// System.out.println("the max = "+max_lev);

		int i_lev = min_lev - 1;
		while (i_lev < max_lev) {
			i_lev++;
			file = new File(toPath + "/" + i_lev);
			file.mkdirs();
			int n = (int) java.lang.Math.pow(2, i_lev);
			int m = (int) (n * rate);

			// System.out.println("m = "+m);
			if (m < 1)
				m = 1;
			if (i_lev <= middle_size) {
				image = new MagickImage(infoS);
				scaled = image.scaleImage(n, m);
				scaled.setFileName(toPath + "/" + i_lev + "/0_0.jpg");
				System.out.println(toPath + "/" + i_lev + "/0_0.jpg");
				scaled.writeImage(infoS);
			} else {
				n = n / i_side;
				int wide = n * i_side, high = (int) (wide * rate);
				m = (int) (high / i_side + 0.5);
				// int wide = n,high = n;
				// System.out.println(wide+" "+high);
				try {
					image = new MagickImage(infoS);
					scaled = image.scaleImage(wide, high);
					// scaled.setFileName(toPath+"_"+i_lev);
					// scaled.writeImage(infoS);
					int tmp_w = i_side, tmp_h = high - (m - 1) * i_side;
					for (int i = 0; i <= m; i++) {
						for (int j = 0; j < n; j++) {
							if (i == m) {
								rect = new Rectangle(j * i_side, i * i_side,
										tmp_w, tmp_h);
							} else {
								rect = new Rectangle(j * i_side, i * i_side,
										i_side, i_side);
							}

							System.out.println(toPath + "/" + i_lev + "/" + j
									+ "_" + i + ".jpg");
							cropped = scaled.cropImage(rect);
							cropped.setFileName(toPath + "/" + i_lev + "/" + j
									+ "_" + i + ".jpg");
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
	}
	/*
	 * @param	topath
	 * 					dzi文件保存的路径和+文件名
	 * @param	i_size
	 * 					元图的尺寸
	 * */
	public static void write_dzi(String topath,int i_size){
		PrintWriter writer;
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Image TileSize=\""+i_size+"\" Overlap=\"1\" Format=\"jpg\" xmlns=\"http://schemas.microsoft.com/deepzoom/2008\"><Size Width=\"2016\" Height=\"2016\"/></Image>";
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
		topath += filename;
		//System.out.println(System.getProperty("java.library.path"));
		write_dzi(topath+".dzi",512);
		try {
			cutImg(imgpath,topath+"_files",512,1366,768);
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
