package rendering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Rendering
{
	void render() throws FileNotFoundException;
	void raster() throws FileNotFoundException;
	void save(File file) throws IOException;
}