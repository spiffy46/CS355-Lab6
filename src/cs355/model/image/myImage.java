package cs355.model.image;

import java.awt.Color;
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
		myImage newImage = new myImage();
		newImage.setPixels(this);
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				
				int[] rgb = new int[3];
				getPixel(i,j,rgb);
				
				if(i == 0 || j == 0){
					newImage.setPixel(i,j,rgb);
				}else if(i == getWidth()-1 || j == getHeight()-1){
					newImage.setPixel(i,j,rgb);
				}else{
				
					int[] tmp = new int[3];
								
					int newRed = 0;
					int newGreen = 0;
					int newBlue = 0;
				
					for(int x = -1; x <=1; x++){
						for(int y = -1; y <=1; y++){
							getPixel(i+x,j+y,tmp);
							newRed += tmp[0];
							newGreen += tmp[1];
							newBlue += tmp[2];
						}
					}
																				
					tmp[0] = newRed/9;
					tmp[1] = newGreen/9;
					tmp[2] = newBlue/9;
				
					newImage.setPixel(i,j,tmp);
				}
			}
		}
		setPixels(newImage);
	}

	@Override
	public void grayscale() {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				int[] rgb = new int[3];
				float [] hsb = new float[3];
				
				getPixel(i,j,rgb);
								
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				
				hsb[1] = 0;
												
				Color col = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = col.getRed();
				rgb[1] = col.getGreen();
				rgb[2] = col.getBlue();
				
				setPixel(i,j,rgb);
			}
		}
	}

	@Override
	public void contrast(int amount) {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				int[] rgb = new int[3];
				float [] hsb = new float[3];
				
				getPixel(i,j,rgb);
								
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				
				float s = (float)Math.pow(((float)amount + 100f)/100f, 4) * (hsb[2] - .5f) + .5f;
				if(s > 1){s = 1;}
				if(s < 0){s = 0;}
				hsb[2] = s;
												
				Color col = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = col.getRed();
				rgb[1] = col.getGreen();
				rgb[2] = col.getBlue();
				
				setPixel(i,j,rgb);
			}
		}
	}

	@Override
	public void brightness(int amount) {
		float adjB = (float)amount / 100f;
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				int[] rgb = new int[3];
				float [] hsb = new float[3];
				
				getPixel(i,j,rgb);
								
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				
				float newB = hsb[2] + adjB;
				if (newB > 1){newB = 1;}
				if (newB < 0){newB = 0;}
				hsb[2] = newB;
												
				Color col = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = col.getRed();
				rgb[1] = col.getGreen();
				rgb[2] = col.getBlue();
				
				setPixel(i,j,rgb);
			}
		}
	}
	
}
