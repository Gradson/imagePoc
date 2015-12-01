package br.com.gague.imagepoc.service;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public abstract class ImageConverter {
	
	private final String dirTarget = "C:\\Users\\mateus.brum\\Desktop\\convert\\";
	
	public abstract boolean convertToPdf(File file);
	
	protected String getPngPathDestination(String fileName) {
		String baseName = FilenameUtils.getBaseName(fileName);
		return dirTarget + baseName + ".png";
	}
	
	protected String getPngPathDestination(String fileName, int pageNumber) {
		String baseName = FilenameUtils.getBaseName(fileName);
		return dirTarget + baseName + "_" + pageNumber + ".png";
	}
	
	protected String getPdfPathDestination(String fileName) {
		String baseName = FilenameUtils.getBaseName(fileName);
		return dirTarget + baseName + ".pdf";
	}
	
	protected String getDirTarget() {
		return this.dirTarget;
	}
}
