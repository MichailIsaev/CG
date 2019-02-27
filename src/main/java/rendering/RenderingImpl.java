package rendering;

import cg_models.Fs;
import cg_models.OBJ;
import cg_models.Point;
import cg_models.Polygon;
import draw.MyImage;
import draw.algorithm.DrawLine;
import draw.algorithm.DrawLineBr;
import parser.Parser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RenderingImpl implements Rendering
{
	public RenderingImpl()
	{
		drawLine = new DrawLineBr();
		file = new File("src/main/resources/deer.obj");
		image = new MyImage(1000, 1000, BufferedImage.TYPE_INT_RGB );
	}

	private MyImage image;
	private DrawLine drawLine;
	private File file;
	

	public void render() throws FileNotFoundException
	{
		OBJ obj = Parser.Companion.parse(file);
		for(int i = - image.getWidth() / 2 + 1 ; i < image.getWidth() / 2 - 1 ; i++){
			for(int j = - image.getHeight() / 2 + 1 ; j < image.getHeight() / 2 - 1  ; j++){
				image.setRGB(i , j , Color.WHITE.getRGB());
			}
		}
		for(Fs fs: obj.getFs()){
			double x0 = (obj.getVs().get((int)fs.getX() - 1).getX() )*image.getWidth()/4;
			double y0 = (obj.getVs().get((int)fs.getX() - 1).getY() )*image.getHeight()/4;
			double x1 = (obj.getVs().get((int)fs.getY() - 1).getX() )*image.getWidth()/4;
			double y1 = (obj.getVs().get((int)fs.getY() - 1).getY() )*image.getHeight()/4;
			double x2 = (obj.getVs().get((int)fs.getZ() - 1).getX() )*image.getWidth()/4;
			double y2 = (obj.getVs().get((int)fs.getZ() - 1).getY() )*image.getHeight()/4;
			Point p1 = new Point(x0 , y0);
			Point p2 = new Point(x1 , y1);
			Point p3 = new Point(x2 , y2);
			drawLine.draw(p1 , p2 , image , Color.BLACK);
			drawLine.draw(p1 , p3 , image , Color.BLACK);
			drawLine.draw(p2 , p3 , image , Color.BLACK);
			//break;
		}
	}

	public void save(File file) throws IOException
	{
		ImageIO.write(image, "png", file);
	}
}