package draw.algorithm;


import draw.MyImage;

import cg_models.Point;

import java.awt.*;


public interface DrawLine {

    void draw(Integer x0 , Integer x1 , Integer y0 , Integer y1 , MyImage image , Color color);
    default void draw(Point p1 , Point p2 , MyImage image , Color color){
        draw((int)p1.getX() , (int)p2.getX() , (int)p1.getY() , (int)p2.getY() , image , color);
    }
}
