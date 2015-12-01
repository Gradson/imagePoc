package br.com.gague.imagepoc.service.impl;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import br.com.gague.imagepoc.service.ImageConverter;
import net.sf.jcgm.core.CGM;

public class CGMConverter extends ImageConverter {

	@Override
	public boolean convertToPdf(File file) {
		try {
			IMOperation op = createOperations(file);
			
			ConvertCmd cmd = new ConvertCmd();
			cmd.run(op);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private IMOperation createOperations(File file) throws IOException {
		Dimension dimension = getDimension(file);
		IMOperation op = new IMOperation();
		op.density(300, 300);
		op.resize(dimension.width, dimension.height);
		op.quality(600d);
		op.addImage(file.getAbsolutePath()); // source file
		op.addImage(getPdfPathDestination(file.getName())); // destination file
		return op;
	}

	private Dimension getDimension(File file) throws IOException {
		Dimension dimension = new CGM(file).getSize();
		adaptDimension(dimension);
		
		return dimension;
	}
	
	private void adaptDimension(Dimension dimension) {
		if (dimension == null) {
			dimension = new Dimension(800, 600);
		} else {
		
			double size16A0 = 64022490;
			
			if ((dimension.getWidth() * dimension.getHeight()) > size16A0) {
				dimension.setSize(9513, 6730);
			}
		}
	}
}
