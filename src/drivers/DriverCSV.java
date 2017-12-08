package drivers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverCSV{

	private File csvFile;
	private String line;
	private Scanner scanner;
	private String[] fieldNames;
	
	public DriverCSV(String filename) throws FileNotFoundException {
		this.csvFile = new File(filename);
		this.scanner = new Scanner(this.csvFile);
		this.proceed();
		this.proceed();
		this.fieldNames = this.getFields();
	}
	
	public int getNumOfLineFields() {
		return line.split(",").length;
	}
	
	public void proceed() {
		this.line = this.scanner.nextLine();
	}
	
	public boolean hasNext() {
		return scanner.hasNext();
	}
	
	public String[] getFields() {
		return line.split(",");
	}
	
	public String[] getFieldNames() {
		return this.fieldNames;
	}
	
	public String[] gotoSpecificLine(int lineIndex) throws FileNotFoundException{
		scanner.close();
		scanner = new Scanner(csvFile);
		for(int i = 0; i < lineIndex; i++) {
			this.proceed();
		}
		return this.getFields();
	}
	
}
