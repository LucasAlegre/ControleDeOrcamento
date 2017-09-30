package util;

import java.io.File;

public class LeitorCSV {

	private File file;
	
	public LeitorCSV(String filename) {
		file = new File(filename);
	}
}
