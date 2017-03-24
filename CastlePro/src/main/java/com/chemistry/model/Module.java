package com.chemistry.model;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Module extends Concept{

	private CommonsMultipartFile file;
	private byte[] fileBytes;
	
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public byte[] getFileBytes() {
		return fileBytes;
	}
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
}
