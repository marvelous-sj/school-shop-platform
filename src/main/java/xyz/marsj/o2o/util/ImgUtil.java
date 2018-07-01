package xyz.marsj.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import xyz.marsj.o2o.dto.ImgHolder;

public class ImgUtil {
	private static final SimpleDateFormat simpleDate=new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r=new Random();
	private static String basePath=PathUtil.getImgBasePath();
	public static String generateThumbnail(ImgHolder thumbnail,String targetAddr){
		String realFileName=getRandomFileName();
		String extension=getExtension(thumbnail.getImgName());
		makeDirPath(targetAddr);
		String relativePath=targetAddr+realFileName+extension;
		
		File filePath = new File(PathUtil.getImgBasePath()+ relativePath);
		try {
			Thumbnails.of(thumbnail.getImg()).size(200, 200).watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f).outputQuality(0.8f).toFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativePath;
	}
	
	public static String generateNormalImg(ImgHolder thumbnail,String targetAddr){
		String realFileName=getRandomFileName();
		String extension=getExtension(thumbnail.getImgName());
		makeDirPath(targetAddr);
		String relativePath=targetAddr+realFileName+extension;
		
		File filePath = new File(PathUtil.getImgBasePath()+ relativePath);
		try {
			Thumbnails.of(thumbnail.getImg()).size(340, 640).watermark(Positions.BOTTOM_RIGHT,
					ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f).outputQuality(0.9f).toFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativePath;
	}
	
	
	private static void makeDirPath(String targetAddr) {
		String realFilePath=PathUtil.getImgBasePath()+targetAddr;
		File file=new File(realFilePath);
		if(!file.exists()){
			file.mkdirs();
		}
		
	}


	private static String getExtension(String fileName) {
		
		return fileName.substring(fileName.lastIndexOf("."));
	}


	public static String getRandomFileName() {
		String format = simpleDate.format(new Date());
		int randomnum=r.nextInt(8999)+10000;
		return format+randomnum;
	}


	public static void deleteFileOrPath(String storePath){
		File fileOrPath=new File(PathUtil.getImgBasePath()+storePath)	;
		if(fileOrPath.exists()){
			if(fileOrPath.isDirectory()){
				File files[]=fileOrPath.listFiles();
				for(int i=0;i<files.length;i++){
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
	
}
