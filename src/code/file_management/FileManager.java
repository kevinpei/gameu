package code.file_management;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileManager {

	/*
	 * A function to get the path to the specified folder within the dat folder which holds
	 * all game data such as art assets and music.
	 */
	public static String getResource(String folder) {
		try {
			File parent = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String path = parent.getParent();
			path += (File.separator + "dat" + File.separator + folder + File.separator);
			return path;
		} catch (URISyntaxException e) {
			return null;
		}
	}
}
