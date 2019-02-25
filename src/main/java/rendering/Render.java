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
        try {
            DrawLine drawLine = new DrawLineBr();
            OBJ obj = Parser.Companion.parse(file);
            int coord = 0;
            for(Vs vs: obj.getVs()){
                coord = Math.abs(vs.getX()) > coord ? (int) Math.abs(vs.getX()) : coord;
                coord = Math.abs(vs.getY()) > coord ? (int) Math.abs(vs.getY()) : coord;
            }
            coord *= 2;
            coord += 200;
            MyImage myImage = new MyImage(800, 800, BufferedImage.TYPE_INT_RGB);
            for(int i = - myImage.getWidth() / 2 + 1 ; i < myImage.getWidth() / 2 - 1 ; i++){
                for(int j = - myImage.getHeight() / 2 + 1 ; j < myImage.getHeight() / 2 - 1  ; j++){
                    myImage.setRGB(i , j , Color.WHITE.getRGB());
                }
            }
            for(Fs fs: obj.getFs()){
                double x0 = (obj.getVs().get((int)fs.getX() - 1).getX() + 1)*myImage.getWidth()/4;
                double y0 = (obj.getVs().get((int)fs.getX() - 1).getY() + 1)*myImage.getHeight()/4;
                double x1 = (obj.getVs().get((int)fs.getY() - 1).getX() + 1)*myImage.getWidth()/4;
                double y1 = (obj.getVs().get((int)fs.getY() - 1).getY() + 1)*myImage.getHeight()/4;
                double x2 = (obj.getVs().get((int)fs.getZ() - 1).getX() + 1)*myImage.getWidth()/4;
                double y2 = (obj.getVs().get((int)fs.getZ() - 1).getY() + 1)*myImage.getHeight()/4;
                drawLine.draw((int) x0 , (int) x1 , (int) y0 , (int) y1 , myImage, Color.BLACK);
                drawLine.draw((int)x0 , (int)x2 , (int)y0 ,(int) y2 , myImage, Color.BLACK );
                drawLine.draw((int)x1 , (int)x2 , (int)y1 , (int)y2 , myImage, Color.BLACK );
                //break;
            }
            ImageIO.write(myImage , "png" , new File("src/main/resources/out.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
