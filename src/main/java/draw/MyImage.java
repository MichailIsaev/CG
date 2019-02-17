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