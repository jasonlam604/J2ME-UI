package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ImageHolder extends Component {
	
	private Image image;	
	private String filename;
	
	public ImageHolder() {
		
	}
	
	public void setImage(Image image) {
		this.image = image;
		updateDimensions();
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setImage(String filename) {
		this.filename = filename;
		this.image = createImage(filename);
		updateDimensions();
	}
	
	private void updateDimensions() {
		this.setWidth(this.image.getWidth());
		this.setHeight(this.image.getHeight());
		this.setDirty(true);
	}
	
	public void paint(Graphics g) {
		if(isDirty() && isVisible()) {			
			this.setDirty(false);
			g.drawImage(image, getX(),getY(), 20);
		}
	}
	
	private final static Image createImage(String filename) {
		Image image = null;
		try {
			image = Image.createImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public static Image scaleImage2(Image sourceImage, int newWidth, int newHeight) {

		// Remember image size.
		int oldWidth = sourceImage.getWidth();
		int oldHeight = sourceImage.getHeight();

		// Create buffer for input image.
		int[] inputData = new int[oldWidth * oldHeight];

		// Fill it with image data.
		sourceImage.getRGB(inputData, 0, oldWidth, 0, 0, oldWidth, oldHeight);

		// Create buffer for output image.
		int[] outputData = new int[newWidth * newHeight];

		int YD = (oldHeight / newHeight - 1) * oldWidth;
		int YR = oldHeight % newHeight;
		int XD = oldWidth / newWidth;
		int XR = oldWidth % newWidth;

		// New image buffer offset.
		int outOffset = 0;

		// Source image buffer offset.
		int inOffset = 0;

		for (int y = newHeight, YE = 0; y > 0; y--) {
			for (int x = newWidth, XE = 0; x > 0; x--) {
				// Copying pixel from old image to new.
				outputData[outOffset++] = inputData[inOffset];
				inOffset += XD;

				// Calculations for "smooth" scaling in x dimension.
				XE += XR;
				if (XE >= newWidth) {
					XE -= newWidth;
					inOffset++;
				}
			}
			inOffset += YD;

			// Calculations for "smooth" scaling in y dimension.
			YE += YR;
			if (YE >= newHeight) {
				YE -= newHeight;
				inOffset += oldWidth;
			}
		}

		// Create image from output bufer.
		return Image.createRGBImage(outputData, newWidth, newHeight, true);
	}	

}
