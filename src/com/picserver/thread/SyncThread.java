package com.picserver.thread;

import java.io.IOException;

import com.picserver.picture.PictureWriter;

public class SyncThread extends Thread {
	private String LocalPath; 
	private String uid; 
	private String space;
	public SyncThread(String LocalPath,String uid,String space) { 
		this.LocalPath = LocalPath; 
		this.uid = uid;
		this.space = space;
	} 
	public void run() { 
		PictureWriter PWriter = new PictureWriter();
		try {
			PWriter.localDirSync(LocalPath, uid ,space);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
