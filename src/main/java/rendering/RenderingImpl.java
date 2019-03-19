package rendering;

import cg_models.*;
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
	public RenderingImpl() throws IOException
	{
		drawLine = new DrawLineBr();
		file = new File("src/main/resources/man.obj");
		image = new MyImage(2400, 2400, BufferedImage.TYPE_INT_RGB );

		polygons = new ArrayList<>();
		textures = new MyImage(1024 , 1024 , BufferedImage.TYPE_INT_RGB);
		BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/african_head_diffuse.jpg"));
		for(int i = - bufferedImage.getWidth() / 2 + 1 ; i < bufferedImage.getWidth() / 2 - 1 ; i++){
			for(int j = - bufferedImage.getHeight() / 2 + 1 ; j < bufferedImage.getHeight() / 2 - 1; j++){
				textures.setRGB(i , j , bufferedImage.getRGB(i + bufferedImage.getWidth() / 2 - 1  , j + bufferedImage.getHeight() / 2 + 1));
			}
		}
	}

	private MyImage image;
	private DrawLine drawLine;
	private File file;
	private OBJ obj;
	private List<Polygon> polygons;
	private MyImage textures;

	public void render() throws FileNotFoundException
	{
		obj = Parser.Companion.parse(file);
		for(int i = - image.getWidth() / 2 + 1 ; i < image.getWidth() / 2 - 1 ; i++){
			for(int j = - image.getHeight() / 2 + 1 ; j < image.getHeight() / 2 - 1  ; j++){
				image.setRGB(i , j , Color.WHITE.getRGB());
			}
		}
		for(Fs fs: obj.getFs()){
			double x0 = (obj.getVs().get((int)fs.getX() - 1).getX() )*1024;
			double y0 = (obj.getVs().get((int)fs.getX() - 1).getY() )*1024;
            double z0 = (obj.getVs().get((int)fs.getX() - 1).getZ() )*1024;
            double x1 = (obj.getVs().get((int)fs.getY() - 1).getX() )*1024;
			double y1 = (obj.getVs().get((int)fs.getY() - 1).getY() )*1024;
            double z1 = (obj.getVs().get((int)fs.getY() - 1).getZ() )*1024;
            double x2 = (obj.getVs().get((int)fs.getZ() - 1).getX() )*1024;
			double y2 = (obj.getVs().get((int)fs.getZ() - 1).getY() )*1024;
            double z2 = (obj.getVs().get((int)fs.getZ() - 1).getZ() )*1024;
            Point p1 = new Point(x0 , y0, z0);
			Point p2 = new Point(x1 , y1, z1);
			Point p3 = new Point(x2 , y2, z2);
			polygons.add(new Polygon(p1 , p2 , p3));
			drawLine.draw(p1 , p2 , image , Color.BLACK);
			drawLine.draw(p1 , p3 , image , Color.BLACK);
			drawLine.draw(p2 , p3 , image , Color.BLACK);
			//break;
		}
	}

	public void raster() throws FileNotFoundException {
		//TODO: Works but must be changed
		int z = 0;
        Point normal;
        Double surveillance;
        Double normalNorm,surveillanceNorm, mult;

		for(Polygon polygon: polygons){
		    z++;

		    normal = polygon.normal();
		    surveillance = normal.scMul(new Point(0 , 0 , 1));


            mult = 0.;

            if(surveillance > 0){
            	continue;
			}

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
			List<Point> points = new ArrayList<>();
			int b1 = polygon.max_X();
			int b2 = polygon.max_Y();
			int b3 = polygon.min_X();
			int b4 = polygon.min_Y();
			double l0 = 0;
			double l1 = 0;
			double l2 = 0;
			Random random = new Random();
			int r = (int) Math.abs(surveillance * 255 % 255);
			int g = (int) Math.abs(surveillance * 255% 255);
			int b = (int) Math.abs(surveillance * 255 % 255);
			double zB = -Double.MAX_VALUE;
			Color color = new Color(r , g , b);
			//color = color.darker();
			double tx = 0;
			double ty;
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
					if(l0 < 0 || l1 < 0 || l2 < 0){
						continue;
					}
					if(polygon.getP1().getZ() * l0 + polygon.getP2().getZ() * l1 + polygon.getP3().getZ() * l2 < image.getZBuffer(i , j)){
						continue;
					}
					
					
					image.setZBudder( i , j ,polygon.getP1().getZ() * l0 + polygon.getP2().getZ() * l1 + polygon.getP3().getZ() * l2);
					points.add(new Point(i , j, polygon.getP1().getZ() * l0 + polygon.getP2().getZ() * l1 + polygon.getP3().getZ() * l2));

					
					

					

				}
			}
			for(Point point : points){
				image.setRGB((int)point.getX() , (int)point.getY() , color.getRGB());
			}
			//break;
		}
	}

	public void save(File file) throws IOException
	{
		ImageIO.write(image, "png", file);
	}
}