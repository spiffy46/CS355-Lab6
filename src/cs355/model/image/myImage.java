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
		int[][] input = {{10,11,9,25,22},
						{8,10,9,26,28},
						{9,8,9,24,25},
						{11,11,12,23,22},
						{10,11,9,22,25}
		};
		float[][] xoutput = {{0,0,0,0,0,},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0}
		};
		float[][] youtput = {{0,0,0,0,0,},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0}
		};
		float[][] xmask = {{0,-1,0},{-1,5,-1},{0,-1,0}};
		float[][] ymask = {{-1,-2,-1},{0,0,0},{1,2,1}};
		
		for(int i = 0; i < input.length; i++){
			for(int j = 0; j < input.length; j++){
				if(i == 0 || j == 0){
					xoutput[i][j] = 0;
					youtput[i][j] = 0;
				}else if(i == input.length-1 || j == input.length-1){
					xoutput[i][j] = 0;
					youtput[i][j] = 0;
				}else{		
					float xtmp = 0;
					float ytmp = 0;
					for(int x = -1; x <=1; x++){
						for(int y = -1; y <=1; y++){
							xtmp += input[i+x][j+y] * xmask[x+1][y+1];
							//ytmp += input[i+x][j+y] * ymask[x+1][y+1];
						}
					}
					xtmp = xtmp;
					//ytmp = ytmp/8;
					
					
					float val = (float)Math.sqrt((xtmp * xtmp)+(ytmp * ytmp));

					youtput[i][j] = xtmp;
					System.out.print(youtput[i][j] + "  ");
				}
			}
			System.out.println();
		}
		
		
		
		
		//End TEST
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
							
							newXB += (hsb[2] * 255 * xKernel[x+1][y+1]);
							newYB += (hsb[2] * 255 * yKernel[x+1][y+1]);
						}
					}
					newXB = newXB / 8;
					newYB = newYB /8;
					
					float val = (float)Math.sqrt((newXB * newXB)+(newYB * newYB));
					
					int col = (int)(newYB * 255);
					col += 128;
					
					rgb[0] = (int)val;
					rgb[1] = (int)val;
					rgb[2] = (int)val;
					
					newImage.setPixel(i,j,rgb);
				}
			}
		}
		setPixels(newImage);
	}

	@Override
	public void sharpen() {
		//TODO Verify that this works
		
		float[][] unSharp = {{0,-.5f,0},{-.5f,3,-.5f},{0,-.5f,0}};

		
		myImage newImage = new myImage();
		newImage.setPixels(this);
		int[] rgb = new int[3];
		
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {		
				if(i == 0 || j == 0){
					newImage.setPixel(i,j,rgb);
				}else if(i == getWidth()-1 || j == getHeight()-1){
					newImage.setPixel(i,j,rgb);
				}else{		
					float newR = 0;
					float newG = 0;
					float newB = 0;
					
					for(int x = -1; x <=1; x++){
						for(int y = -1; y <=1; y++){
							getPixel(i+x,j+y,rgb);
							
							newR += (rgb[0] * unSharp[x+1][y+1]);
							newG += (rgb[1] * unSharp[x+1][y+1]);
							newB += (rgb[2] * unSharp[x+1][y+1]);
						}
					}
					
					if(newR > 255){newR = 255;}
					if(newG > 255){newG = 255;}
					if(newB > 255){newB = 255;}
					if(newR < 0){newR = 0;}
					if(newG < 0){newG = 0;}
					if(newB < 0){newB = 0;}

					rgb[0] = (int)newR;
					rgb[1] = (int)newG;
					rgb[2] = (int)newB;
					
					newImage.setPixel(i,j,rgb);
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
