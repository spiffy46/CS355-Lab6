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
		float[][] xKernel = {{-1,0,1},{-2,0,2},{-1,0,1}};
		float[][] yKernel = {{-1,-2,-1},{0,0,0},{1,2,1}};
		
		myImage newImage = new myImage();
		newImage.setPixels(this);
		int[] rgb = new int[3];
		float [] hsb = new float[3];

		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {		
				if(i == 0 || j == 0){
					newImage.setPixel(i,j,rgb);
				}else if(i == getWidth()-1 || j == getHeight()-1){
					newImage.setPixel(i,j,rgb);
				}else{		
					float newXB = 0;
					float newYB = 0;
					
					for(int x = -1; x <=1; x++){
						for(int y = -1; y <=1; y++){
							getPixel(i+x,j+y,rgb);
							Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
							newXB += hsb[2] * xKernel[x+1][y+1];
							newYB += hsb[2] * yKernel[x+1][y+1];
						}
					}
					newXB = newXB / 8;
					newYB = newYB /8;
					
					float val = (float)Math.sqrt((newXB * newXB)+(newYB * newYB));
					
					int col = (int)(val * 255);
					System.out.println(col);

					rgb[0] = col;
					rgb[1] = col;
					rgb[2] = col;
					
					newImage.setPixel(i,j,rgb);
				}
			}
		}
		setPixels(newImage);
	}

	@Override
	public void sharpen() {
		myImage newImage = new myImage();
		newImage.setPixels(this);
		int[] rgb = new int[3];
		int[] tmp = new int[3];

		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				
				getPixel(i,j,rgb);
				
				if(i == 0 || j == 0){
					newImage.setPixel(i,j,rgb);
				}else if(i == getWidth()-1 || j == getHeight()-1){
					newImage.setPixel(i,j,rgb);
				}else{		
					int newRed = rgb[0] * 6;
					int newGreen = rgb[1] * 6;
					int newBlue = rgb[2] * 6;
				
					getPixel(i-1,j,tmp);
					newRed -= tmp[0];
					newGreen -= tmp[1];
					newBlue -= tmp[2];
					getPixel(i,j-1,tmp);
					newRed -= tmp[0];
					newGreen -= tmp[1];
					newBlue -= tmp[2];
					getPixel(i+1,j,tmp);
					newRed -= tmp[0];
					newGreen -= tmp[1];
					newBlue -= tmp[2];
					getPixel(i,j+1,tmp);
					newRed -= tmp[0];
					newGreen -= tmp[1];
					newBlue -= tmp[2];
					
					tmp[0] = newRed/2;
					tmp[1] = newGreen/2;
					tmp[2] = newBlue/2;
					
					if(tmp[0]>255){tmp[0] = 255;}
					if(tmp[1]>255){tmp[1] = 255;}
					if(tmp[2]>255){tmp[2] = 255;}
					if(tmp[0]<0){tmp[0] = 0;}
					if(tmp[1]<0){tmp[1] = 0;}
					if(tmp[2]<0){tmp[2] = 0;}


					System.out.println("R: " + tmp[0] + " G: " + tmp[1] + " B: " + tmp[2]);
				
					newImage.setPixel(i,j,tmp);
				}
			}
		}
		setPixels(newImage);
	}

	@Override
	public void medianBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uniformBlur() {
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
