package com.kym.ui.info;

import java.io.File;

public class UploadFile {

	private String Idcard;

	public String getIdcard() {
		return Idcard;
	}

	public void setIdcard(String idcard) {
		Idcard = idcard;
	}

	private String type; // 文件类型,取值范围 image (jpg/png/gif 图片文件)video (mp4 视频文件)
	private File file; // 文件二进制流

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
