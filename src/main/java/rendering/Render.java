package rendering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Render {


    public static void main(String[] args) {

        File file = new File("src/main/resources/man.obj");
        Rendering rendering = new RenderingImpl();
        try
        {
            rendering.render();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            rendering.save(new File("out.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
