package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import negocios.CategoriaRubrica;
import negocios.Rubrica;

public class LeitorCSV {

	private File file;
	
	public LeitorCSV(String filename) {
		file = new File(filename);
	}

	private LinkedHashMap<Integer, Rubrica> ler(){
	
		LinkedHashMap<Integer, Rubrica> map = new LinkedHashMap<>();
		
		List<Rubrica> base= new ArrayList<>();
		List<Rubrica> pais= new ArrayList<>();
		Rubrica buf = null;
		int pontoBuf = 0;
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNext()) {
				Rubrica rubrica;
				
				String line = scan.nextLine();
				
				int pos1 = line.indexOf(';');
				int pos2 = line.indexOf(';', pos1+1);
				int pos3 = line.indexOf(';', pos2+1); 
				
				String classe = line.substring(0, pos1);
				String nome = line.substring(pos1+1, pos2);
				String cod = line.substring(pos2+1, pos3);
				String total = line.substring(pos3+1);
				
				if(0 != pos1 && pos1 != pos2-1 && pos2 != pos3-1) {
					//System.out.println(classe + "    " + nome + "    " + cod + "     " + total);
					
					int pontos = classe.length() - classe.replace(".", "").length();
						
					if(pontos == 0) {
						pais.clear();
						rubrica = new Rubrica(null, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						base.add(rubrica);
					}else if(pontos == pontoBuf) {
						rubrica = new Rubrica(buf.getPai(), nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						buf.getPai().addSubRubrica(rubrica);
						
					}else if(pontos > pontoBuf){
						pais.add(buf);
						rubrica = new Rubrica(buf, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						buf.addSubRubrica(rubrica);
						
					}else {
						Rubrica pai = pais.get(pontos-1);
						rubrica = new Rubrica(pai, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						pai.addSubRubrica(rubrica);
						for(int i = pontos; i<pais.size();i++) {
							pais.remove(i);
						}
					}
						buf = rubrica;
						pontoBuf = pontos;
						
						map.put(Integer.valueOf(cod), rubrica);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void printInfo(Rubrica r, int tab) {
		for(Rubrica ru : r.getSubRubricas()) {
			for(int i = 0; i<tab; i++) {
				System.out.print("    ");
			}
			System.out.print(ru.toString() + '\n');
			this.printInfo(ru, tab+1);
		}
	}

	public static void main(String[] args) {
		LeitorCSV le = new LeitorCSV("PlanoBase.csv");
		LinkedHashMap<Integer, Rubrica> ru = le.ler();
		System.out.println(ru);
	}
}
