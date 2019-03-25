package draw;

import java.awt.image.BufferedImage;

public class MyImage extends BufferedImage
{
	public MyImage()
	{
		 super(0,0,BufferedImage.TYPE_INT_RGB);
	}
	public MyImage(int width , int height , int imageType){
		super(width , height , imageType);
		for(int i = 0 ; i < getHeight() ; i++){
			for(int j = 0 ; j  < getWidth() ; j++){
				zBuffer [i][j] =  - Double.MAX_VALUE;
			}
		}

	}

	private double[][] zBuffer = new double[getHeight()][getWidth()];

	public double getZBuffer(int i , int j){
		return this.zBuffer[this.getWidth() / 2 + i][this.getHeight() / 2 - j];
	}

	public void setZBudder(int i , int j , double element){
		zBuffer[this.getWidth() / 2 + i][this.getHeight() / 2 - j] = element;
	}

	@Override
	public void setRGB(int x , int y , int rgb){
		super.setRGB(this.getWidth() / 2 + x , this.getHeight() / 2 - y , rgb);
	}

	@Override
	public int getRGB(int x , int y){
		return super.getRGB(this.getWidth() / 2 + x , this.getHeight() / 2 - y);
	}
}