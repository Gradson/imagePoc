package br.com.gague.imagepoc.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

import br.com.gague.imagepoc.service.ImageConverter;

public class FileConverter {

	public boolean changeType(UploadedFile uploadFile, boolean isResize) {
		File file = copyFile(uploadFile);
		
		ImageConverter converterImage = getConverter(file);
		return converterImage.convertToPdf(file);
	}


	private ImageConverter getConverter(File file) {
		String extension = FilenameUtils.getExtension(file.getName());
		switch (extension.toLowerCase().trim()) {
		case "pdf":
			return new PDFConverter();
		case "cgm":
			return new CGMConverter();
		default:
			return new ImageMacickConverter();
		}
	}
	
	private File copyFile(UploadedFile uploadFile) {
		File file = null;
		
		try {
			String pathTarget = "C:\\Users\\mateus.brum\\Desktop\\convert\\";
			
			file = new File(pathTarget + uploadFile.getFileName());
			FileUtils.copyInputStreamToFile(uploadFile.getInputstream(), file);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}
}
