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
		file = new File("src/main/resources/man.obj");
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
            double z0 = (obj.getVs().get((int)fs.getX() - 1).getZ() );
            double x1 = (obj.getVs().get((int)fs.getY() - 1).getX() )*image.getWidth()/4;
			double y1 = (obj.getVs().get((int)fs.getY() - 1).getY() )*image.getHeight()/4;
            double z1 = (obj.getVs().get((int)fs.getY() - 1).getZ() );
            double x2 = (obj.getVs().get((int)fs.getZ() - 1).getX() )*image.getWidth()/4;
			double y2 = (obj.getVs().get((int)fs.getZ() - 1).getY() )*image.getHeight()/4;
            double z2 = (obj.getVs().get((int)fs.getZ() - 1).getZ() );
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
        List<Double> normal;
        List<Double> surveillance;
        Double normalNorm,surveillanceNorm, mult;

		for(Polygon polygon: polygons){
		    z++;

		    normal = polygon.normal();
		    surveillance = polygon.surveillance();
		    normalNorm = normal.stream().reduce((a, b) -> a + b).orElse(1.);
		    normalNorm /= normal.size();
            surveillanceNorm = surveillance.stream().reduce((a, b) -> a + b).orElse(1.);
            surveillanceNorm /= surveillance.size();

            mult = 0.;

            for(int i = 0; i < normal.size(); i++){
		        mult += normal.get(i) * surveillance.get(i);
            }

		    double check = Math.abs((mult / normalNorm) / surveillanceNorm);
		    if (check >= 0 && check <= 1) continue;

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
			int r = Math.abs(random.nextInt() % 75);
			int g = Math.abs(random.nextInt() % 75);
			int b = Math.abs(random.nextInt() % 75);

			Color color = new Color(r , g , b);
			//color = color.darker();
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
						points.add(new Point(i , j, 1));
					}
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