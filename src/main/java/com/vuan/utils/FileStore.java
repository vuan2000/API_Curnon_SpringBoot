package com.vuan.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileStore {
	public static String UPLOAD_FOLDER = "D:\\file\\curnon\\";
	
//	public static List<String> getFilePaths(List<MultipartFile> multipartFiles) {
//		List<String> images = new ArrayList<String>();
//		
//		if (multipartFiles != null) {
//			for(int i = 0;i < multipartFiles.size() ;i++) {
//				MultipartFile imagefile = multipartFiles.get(i);
//				String originalFilename = imagefile.getOriginalFilename();
//				int lastIndex = originalFilename.lastIndexOf(".");
//				String ext = originalFilename.substring(lastIndex);
//
//				String avatarFilename = System.currentTimeMillis() + ext;
//				File newfile = new File(UPLOAD_FOLDER + avatarFilename);
//				FileOutputStream fileOutputStream;
//				try {
//					fileOutputStream = new FileOutputStream(newfile);
//					fileOutputStream.write(imagefile.getBytes());
//					fileOutputStream.close();
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//				images.add(avatarFilename);
//			}
//		} 
//		return images;
//	}
	
	public static String getFilePaths(MultipartFile multipartFiles) {
		
		if (multipartFiles  != null && !multipartFiles.isEmpty()) {
				String originalFilename = multipartFiles.getOriginalFilename();
				int lastIndex = originalFilename.lastIndexOf(".");
				String ext = originalFilename.substring(lastIndex);

				String avatarFilename = System.currentTimeMillis() + ext;
				File newfile = new File(UPLOAD_FOLDER + avatarFilename);
				FileOutputStream fileOutputStream;
				try {
					fileOutputStream = new FileOutputStream(newfile);
					fileOutputStream.write(multipartFiles.getBytes());
					fileOutputStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return avatarFilename;
			
		} 
		return null;
	}
}
