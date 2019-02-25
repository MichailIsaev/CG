package draw.algorithm;



import draw.MyImage;

import java.awt.*;

public class DrawLineVu implements DrawLine {


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
            int sy = y1 > y0 ? 1 : -1;
            if(x1 < x0){
                for (int x = x0; x > x1; x--)
                {
                    Color c =  new Color((int)(255*(1-error)) , (int)(255*(1-error)) , (int)(255*(1-error)));
                    image.setRGB(x, y, new Color((int)(255*(1-error)) , (int)(255*(1-error)) , (int)(255*(1-error))).getRGB());
                    image.setRGB(x, y + sy, new Color((int)(255*error) , (int)(255*error) , (int)(255*error)).getRGB());
                    error += yoffset;
                    if (error >= 1)
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
                    image.setRGB(x, y, new Color((int)(255*(1-error)) , (int)(255*(1-error)) , (int)(255*(1-error))).getRGB());
                    image.setRGB(x, y + sy, new Color((int)(255*error) , (int)(255*error) , (int)(255*error)).getRGB());
                    error += yoffset;
                    if (error >= 1)
                    {
                        y += y1 > y0 ? 1 : -1;
                        error -= 1;
                    }
                }
            }
        }
        else {

            int x = x0;
            int sx = x1 > x0 ? 1 : -1;
            if(y1 < y0){
                for (int y = y0; y > y1; y--)
                {
                    image.setRGB(x, y, new Color((int)(255*(1-error)) , (int)(255*(1-error)) , (int)(255*(1-error))).getRGB());
                    image.setRGB(x + sx, y, new Color((int)(255*error) , (int)(255*error) , (int)(255*error)).getRGB());
                    error += xoffset;
                    if (error >= 1)
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
                    image.setRGB(x, y, new Color((int)(255*(1-error)) , (int)(255*(1-error)) , (int)(255*(1-error))).getRGB());
                    image.setRGB(x + sx, y , new Color((int)(255*error) , (int)(255*error) , (int)(255*error)).getRGB());
                    error += xoffset;
                    if (error >= 1)
                    {
                        x += x1 >= x0 ? 1 : -1;
                        error -= 1;
                    }
                }
            }
        }
    }
}
