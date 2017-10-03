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

	private LinkedHashMap<Integer, Rubrica> lerPlanoBase(){
	
		LinkedHashMap<Integer, Rubrica> map = new LinkedHashMap<>();
		
		//Lista de pais para a estruturação da linhagem hierárquica
		List<Rubrica> pais= new ArrayList<>();
		Rubrica buf = null;
		int pontoBuf = 0;
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNext()) {
				Rubrica rubrica;
				
				// INTERPRETAÇÃO DA LINHA
				String line = scan.nextLine();
				
				int pos1 = line.indexOf(';');
				int pos2 = line.indexOf(';', pos1+1);
				int pos3 = line.indexOf(';', pos2+1); 
				
				String classe = line.substring(0, pos1);
				String nome = line.substring(pos1+1, pos2);
				String cod = line.substring(pos2+1, pos3);
				String total = line.substring(pos3+1);
				//
				
				
				//Caso a rúbrica for classificável
				if(0 != pos1 && pos1 != pos2-1 && pos2 != pos3-1) {
					
					//Instanciar o número de pontos de classificação da rúbrica,
					//ou seja, verificar em que nível a rúbrica está
					int pontos = classe.length() - classe.replace(".", "").length();
					
					//Caso o número de pontos seja zero, ou seja, a rubrica lida não possui pai
					//limpa-se os pais registrados e inicializa a rubrica com pai=null
					if(pontos == 0) {
						pais.clear();
						rubrica = new Rubrica(null, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
					
					//Caso a rubrica esteja classificada ao mesmo nível de sua antecessora, 
					//possui o mesmo pai que ela
					}else if(pontos == pontoBuf) {
						rubrica = new Rubrica(buf.getPai(), nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						buf.getPai().addSubRubrica(rubrica);
						
					//Caso a rubrica esteja em um nível mais profundo que a outra, adicionamos a rubrica
					//anterior na lista de pais a adicionamos como pai da rubrica atual
					}else if(pontos > pontoBuf){
						pais.add(buf);
						rubrica = new Rubrica(buf, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						buf.addSubRubrica(rubrica);
					
					//Caso tenhamos voltado um nível, deletamos a diferença de níveis da rubrica atual e da anterior,
					//assim como retomamos a posição da rubrica antes de intanciarmos ela
					}else {
						Rubrica pai = pais.get(pontos-1);
						rubrica = new Rubrica(pai, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA);
						pai.addSubRubrica(rubrica);
						for(int i = pontos; i<pais.size();i++) {
							pais.remove(i);
						}
					}
					
					//set final
					buf = rubrica;
					pontoBuf = pontos;
						
					//coloca no mapa
					map.put(Integer.valueOf(cod), rubrica);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void printPlanoBase(LinkedHashMap<Integer, Rubrica> map) {
		Rubrica rubrica;
		for(Integer cod : map.keySet()) {
			rubrica = map.get(cod);
			if(rubrica.getPai()==null) {
				this.getPlanoBaseInfo(rubrica, 0);
			}
		}
	}
	
	private void getPlanoBaseInfo(Rubrica r, int tab) {
		for(Rubrica ru : r.getSubRubricas()) {
			for(int i = 0; i<tab; i++) {
				System.out.print("    ");
			}
			System.out.print(ru.toString() + '\n');
			this.getPlanoBaseInfo(ru, tab+1);
		}
	}

	public static void main(String[] args) {
		LeitorCSV ler = new LeitorCSV("PlanoBase.csv");
		LinkedHashMap<Integer, Rubrica> map = ler.lerPlanoBase();
		ler.printPlanoBase(map);
	}
}
