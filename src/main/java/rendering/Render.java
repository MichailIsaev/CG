package rendering;

import cg_models.Fs;
import cg_models.OBJ;
import cg_models.Vs;
import draw.MyImage;
import draw.algorithm.DrawLine;
import draw.algorithm.DrawLineBr;
import draw.algorithm.DrawLineVu;
import parser.Parser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Render {


    public static void main(String[] args) {

        File file = new File("src/main/resources/deer.obj");
        Rendering rendering = new RenderingImpl();
        try {
            rendering.render();
            rendering.raster();

            rendering.save(new File("out.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
