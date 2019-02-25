package draw.algorithm;


import draw.MyImage;

import java.awt.*;

public interface DrawLine {

    void draw(Integer x0 , Integer x1 , Integer y0 , Integer y1 , MyImage image , Color color);
}
