package br.com.gague.imagepoc.service.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import br.com.gague.imagepoc.service.ImageConverter;

public class PDFConverter extends ImageConverter {
	
	public boolean convertToPdf(File file) {
		return true;
	}

	@SuppressWarnings("resource")
	public boolean convertToPng(File file) {
		try {
			FileChannel channel = new RandomAccessFile(file, "r").getChannel();
			ByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			PDFFile pdfFile = new PDFFile(byteBuffer);
			

			for (int pageNum = pdfFile.getNumPages(); pageNum > 0; pageNum--) {
				PDFPage page = pdfFile.getPage(pageNum - 1);
				
				Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
				BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D bufImageGraphics = bufferedImage.createGraphics();

				Image image = page.getImage(
						rect.width, //width
						rect.height, // height
						rect, // clip rect
						null, // null for the ImageObserver
						false, // fill background with white
						true // block until drawing is done
				);
				bufImageGraphics.drawImage(image, 0, 0, null);
				ImageIO.write(bufferedImage, "png", new File(getPngPathDestination(file.getName(), pageNum)));
			}

			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public void createPdf(File file, File fileTarget, boolean isPictureFile) {
		com.lowagie.text.Rectangle rectangle = new com.lowagie.text.Rectangle(14400.0f, 10000.0f);
		Document pdfDocument = new Document(rectangle);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileTarget);
			PdfWriter writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
			writer.open();
			pdfDocument.open();
			
			if (isPictureFile) {
				com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(file.getAbsolutePath());
				image.scaleAbsoluteHeight(image.getHeight());
				image.scaleAbsoluteWidth(image.getWidth());
				image.setAbsolutePosition((rectangle.getWidth()-image.getScaledWidth())/2, (rectangle.getHeight()-image.getScaledHeight())/2);
				pdfDocument.add(image);
			}
			
			else {
				String temp = null;
				pdfDocument.add(new Paragraph(org.apache.commons.io.FileUtils.readFileToString(file, temp)));
			}
			pdfDocument.close();
			writer.close();
		} catch (Exception exception) {
			System.out.println("Document Exception!" + exception);
		}
	}

	public void createPdf(File file, boolean isPictureFile) {
		File fileTarget = new File(getPdfPathDestination(file.getName()));
		createPdf(file, fileTarget, isPictureFile);
	}
}
