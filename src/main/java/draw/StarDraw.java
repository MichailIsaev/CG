package draw;



import draw.algorithm.DrawLine;
import draw.algorithm.DrawLineBr;
import draw.algorithm.DrawLineVu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StarDraw {

    public static void main(String[] args) {
        MyImage image = new MyImage(800 , 800 , BufferedImage.TYPE_INT_RGB);
        DrawLine algorithm = new DrawLineBr();
        Scanner scanner = new Scanner(System.in);
        int length;
        int count;
        System.out.println("Enter the length of a line");
        length = scanner.nextInt();
        System.out.println("Enter the count");
        count = scanner.nextInt();
        System.out.println("Enter the algorithm. 1 - Br , 2 - Vu");
        switch (scanner.nextInt()){
            case 1:
                algorithm = new DrawLineBr();
                break;
            case 2:
                algorithm = new DrawLineVu();
                break;
            default:
                throw new IllegalArgumentException("Available 1 , 2");
        }

        try {
            drawStar(image , length , count , Color.WHITE , algorithm , "src/main/resources/out.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void drawStar(MyImage image , int length , int count , Color color , DrawLine algorithm , String file) throws IOException {

        int x = 0;
        int y = 0;
        double alpha = 0;
        for(int i = 0 ; i <= count ; i++){
            y = (int) ((int)length* Math.sin(alpha));
            x = (int) ((int)length* Math.cos(alpha));
            algorithm.draw(0 , x , 0 , y , image , color);
            alpha = i * Math.PI * 2/count;

        }
        ImageIO.write(image , "png" , new File(file));
    }
}
