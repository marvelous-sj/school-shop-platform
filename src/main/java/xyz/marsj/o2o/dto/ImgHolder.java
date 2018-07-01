package xyz.marsj.o2o.dto;

import java.io.InputStream;

public class ImgHolder {
	private InputStream img;
	private String imgName;
	public ImgHolder(InputStream img, String imgName) {
		super();
		this.img = img;
		this.imgName = imgName;
	}
	public InputStream getImg() {
		return img;
	}
	public void setImg(InputStream img) {
		this.img = img;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
}
