package cs355.model.image;

import java.awt.image.BufferedImage;

public class myImage extends CS355Image {

	
	@Override
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				int[] d = new int[3];
				getPixel(i,j,d);
				int col = (d[0] << 16) | (d[1] << 8) | d[2];
				image.setRGB(i, j, col);
			}
		}
		return image;
	}

	@Override
	public void edgeDetection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sharpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void medianBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uniformBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void grayscale() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contrast(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void brightness(int amount) {
		// TODO Auto-generated method stub
		
	}
	
}
