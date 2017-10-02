package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import negocios.Rubrica;

public class LeitorCSV {

	private File file;
	
	public LeitorCSV(String filename) {
		file = new File(filename);
	}

	private List<Rubrica> ler(){

		List<Rubrica> base= new ArrayList<>();
		Rubrica buff;
		int pontoBuf = 0;
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNext()) {
				
				String line = scan.nextLine();
				
				int pos1 = line.indexOf(';');
				int pos2 = line.indexOf(';', pos1+1);
				int pos3 = line.indexOf(';', pos2+1); 
				
				String classe = line.substring(0, pos1);
				String nome = line.substring(pos1+1, pos2);
				String cod = line.substring(pos2+1, pos3);
				String total = line.substring(pos3+1);
				
				if(0 != pos1 && pos1 != pos2-1 && pos2 != pos3-1) {
					System.out.println(classe + "    " + nome + "    " + cod + "     " + total);
					
					int pontos = classe.length() - classe.replace(".", "").length();
					
					
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base;
	}
	
	public static void main(String[] args) {
		LeitorCSV le = new LeitorCSV("PlanoBase.csv");
		List<Rubrica> ru = le.ler();
	}
}
