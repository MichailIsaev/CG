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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RenderingImpl implements Rendering
{
	public RenderingImpl()
	{
		drawLine = new DrawLineBr();
		file = new File("src/main/resources/deer.obj");
		image = new MyImage(1000, 1000, BufferedImage.TYPE_INT_RGB );
		polygons = new ArrayList<>();
	}

	private MyImage image;
	private DrawLine drawLine;
	private File file;
	private OBJ obj;
	private List<Polygon> polygons;

	public void render() throws FileNotFoundException
	{
		obj = Parser.Companion.parse(file);
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
			polygons.add(new Polygon(p1 , p2 , p3));
			drawLine.draw(p1 , p2 , image , Color.BLACK);
			drawLine.draw(p1 , p3 , image , Color.BLACK);
			drawLine.draw(p2 , p3 , image , Color.BLACK);
			//break;
		}
	}

	public void raster() throws FileNotFoundException {
		// Works but must be changed.
		int z = 0;
		for(Polygon polygon: polygons){
			z++;
			/*double x0 = (obj.getVs().get((int)fs.getX() - 1).getX() )*image.getWidth()/4;
			double y0 = (obj.getVs().get((int)fs.getX() - 1).getY() )*image.getHeight()/4;
			double x1 = (obj.getVs().get((int)fs.getY() - 1).getX() )*image.getWidth()/4;
			double y1 = (obj.getVs().get((int)fs.getY() - 1).getY() )*image.getHeight()/4;
			double x2 = (obj.getVs().get((int)fs.getZ() - 1).getX() )*image.getWidth()/4;
			double y2 = (obj.getVs().get((int)fs.getZ() - 1).getY() )*image.getHeight()/4;
			*/
			/*Point p1 = new Point(x0 , y0);
			Point p2 = new Point(x1 , y1);
			Point p3 = new Point(x2 , y2);*/
			List<Point> points = new ArrayList();
			int b1 = maxX(polygon);
			int b2 = maxY(polygon);
			int b3 = minX(polygon);
			int b4 = minY(polygon);
			double l0 = 0;
			double l1 = 0;
			double l2 = 0;
			Random random = new Random();
			int r = Math.abs(random.nextInt() % 255);
			int g = Math.abs(random.nextInt() % 255);
			int b = Math.abs(random.nextInt() % 255);
			Color color = new Color(r , g , b);
			for(int i = b3 ; i <= b1 ; i++){
				for(int j = b4 ; j <= b2 ; j++){
					l0 = (((j - polygon.getP3().getY()) * (polygon.getP2().getX() - polygon.getP3().getX()) - (i - polygon.getP3().getX())
							* (polygon.getP2().getY() - polygon.getP3().getY())) / ((polygon.getP1().getY() - polygon.getP3().getY())
							* (polygon.getP2().getX() - polygon.getP3().getX()) - (polygon.getP1().getX() - polygon.getP3().getX())
							* (polygon.getP2().getY() - polygon.getP3().getY())));
					l1 =  (((j - polygon.getP1().getY()) * (polygon.getP3().getX() - polygon.getP1().getX()) - (i - polygon.getP1().getX())
							* (polygon.getP3().getY() - polygon.getP1().getY())) / ((polygon.getP2().getY() - polygon.getP1().getY())
							* (polygon.getP3().getX() - polygon.getP1().getX()) - (polygon.getP2().getX() - polygon.getP1().getX())
							* (polygon.getP3().getY() - polygon.getP1().getY())));
					l2 =  (((j - polygon.getP2().getY()) * (polygon.getP1().getX() - polygon.getP2().getX()) - (i - polygon.getP2().getX())
							* (polygon.getP1().getY() - polygon.getP2().getY())) / ((polygon.getP3().getY() - polygon.getP2().getY())
							* (polygon.getP1().getX() - polygon.getP2().getX()) - (polygon.getP3().getX() - polygon.getP2().getX())
							* (polygon.getP1().getY() - polygon.getP2().getY())));
					if(l0 >= 0 && l1 >= 0 && l2 >= 0){
						points.add(new Point(i , j));
					}
				}
			}
			for(Point point : points){
				image.setRGB((int)point.getX() , (int)point.getY() , color.getRGB());
			}
			//break;
		}
	}

	private int maxX(Point p1 , Point p2 , Point p3){
		return (int) Math.max(Math.max(p1.getX() , p2.getX()) , p3.getX());
	}
	private int maxX(Polygon p){
		return maxX(p.getP1() , p.getP2() , p.getP3());
	}

	private int maxY(Point p1 , Point p2 , Point p3){
		return (int) Math.max(Math.max(p1.getY() , p2.getY()) , p3.getY());
	}
	private int maxY(Polygon p){
		return maxY(p.getP1() , p.getP2() , p.getP3());
	}

	private int minX(Point p1 , Point p2 , Point p3){
		return (int) Math.min(Math.min(p1.getX() , p2.getX()) , p3.getX());
	}
	private int minX(Polygon p){
		return minX(p.getP1() , p.getP2() , p.getP3());
	}

	private int minY(Point p1 , Point p2 , Point p3){
		return (int) Math.min(Math.min(p1.getY() , p2.getY()) , p3.getY());
	}
	private int minY(Polygon p){
		return minY(p.getP1() , p.getP2() , p.getP3());
	}

	public void save(File file) throws IOException
	{
		ImageIO.write(image, "png", file);
	}
}