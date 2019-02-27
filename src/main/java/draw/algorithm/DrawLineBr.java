package draw.algorithm;



import cg_models.Point;
import draw.MyImage;

import java.awt.*;

public class DrawLineBr implements DrawLine {



    public void draw(Integer x0, Integer x1, Integer y0, Integer y1, MyImage image, Color color) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        double yoffset = Math.abs((double) dy / (double) dx);
        double xoffset = Math.abs((double) dx / (double) dy);
        double error = 0;
        boolean steep = false;
        if(Math.abs(y1 - y0) > Math.abs(x1 - x0)){
            steep = true;
        }

        if(!steep)
        {
            int y = y0;
            if(x1 < x0){
                for (int x = x0; x > x1; x--)
                {
                    image.setRGB(x, y , color.getRGB());
                    error += yoffset;
                    if (error >= 0.5)
                    {
                        y += y1 > y0 ? 1 : -1;
                        error -= 1;
                    }
                }
            }
            else
            {
                for (int x = x0; x < x1; x++)
                {
                    image.setRGB(x, y, color.getRGB());
                    error += yoffset;
                    if (error >= 0.5)
                    {
                        y += y1 > y0 ? 1 : -1;
                        error -= 1;
                    }
                }
            }
        }
        else {

            int x = x0;
            if(y1 < y0){
                for (int y = y0; y > y1; y--)
                {
                    image.setRGB(x, y, color.getRGB());
                    error += xoffset;
                    if (error >= 0.5)
                    {
                        x += x1 >= x0 ? 1 : -1;
                        error -= 1;
                    }
                }
            }
            else
            {
                for (int y = y0; y < y1; y++)
                {
                    image.setRGB(x, y, color.getRGB());
                    error += xoffset;
                    if (error >= 0.5)
                    {
                        x += x1 >= x0 ? 1 : -1;
                        error -= 1;
                    }
                }
            }
        }
    }
}
