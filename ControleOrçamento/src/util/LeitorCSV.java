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

	public LinkedHashMap<Integer, Rubrica> lerOrcamentoInicial(){
	
		LinkedHashMap<Integer, Rubrica> map = new LinkedHashMap<>();
		
		//Lista de pais para a estruturação da linhagem hierárquica
		List<Rubrica> pais= new ArrayList<>();
		Rubrica buf = null;
		int pontoBuf = 0;
		try {
			String[] valorPassadoMensal;
			Double[] valoresPassadosMensal;
			Scanner scan = new Scanner(file);
			while(scan.hasNext()) {
				Rubrica rubrica;
				
				// INTERPRETAÇÃO DA LINHA
				String line = scan.nextLine();
				
				int pos1 = line.indexOf(',');
				int pos2 = line.indexOf(',', pos1+1);
				int pos3 = line.indexOf(',', pos2+1); 
				
				String classe = line.substring(0, pos1);
				String cod = line.substring(pos1+1, pos2);
				String nome = line.substring(pos2+1, pos3);
				
				//
				
				
				//Caso a rúbrica for classificável
				if(0 != pos1) {

					String valorPassado = line.substring(pos3+1);
					
					valorPassadoMensal = valorPassado.split(",");
					valoresPassadosMensal = new Double[valorPassadoMensal.length];
					
					for(int i = 0; i<valoresPassadosMensal.length; i++) {
						valoresPassadosMensal[i] = Double.valueOf(valorPassadoMensal[i]);
					}
					//Instanciar o número de pontos de classificação da rúbrica,
					//ou seja, verificar em que nível a rúbrica está
					int pontos = classe.length() - classe.replace(".", "").length();
					
					//Caso o número de pontos seja zero, ou seja, a rubrica lida não possui pai
					//limpa-se os pais registrados e inicializa a rubrica com pai=null
					if(pontos == 0) {
						pais.clear();
						rubrica = new Rubrica(null, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
					
					//Caso a rubrica esteja classificada ao mesmo nível de sua antecessora, 
					//possui o mesmo pai que ela
					}else if(pontos == pontoBuf) {
						rubrica = new Rubrica(buf.getPai(), nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
						buf.getPai().addSubRubrica(rubrica);
						
						
					//Caso a rubrica esteja em um nível mais profundo que a outra, adicionamos a rubrica
					//anterior na lista de pais a adicionamos como pai da rubrica atual
					}else if(pontos > pontoBuf){
						rubrica = new Rubrica(buf, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
						if(buf != null) {
							pais.add(buf);
							buf.addSubRubrica(rubrica);
						}
					
					//Caso tenhamos voltado um nível, deletamos a diferença de níveis da rubrica atual e da anterior,
					//assim como retomamos a posição da rubrica antes de intanciarmos ela
					}else {
						Rubrica pai;
						try{
							pai = pais.get(pontos-1);
						}catch(IndexOutOfBoundsException e){
							pai =  null;
							pais.clear();
						}
						rubrica = new Rubrica(pai, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
						if(pai != null)pai.addSubRubrica(rubrica);
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
	
	
	public static void main(String[] args) {
		LeitorCSV ler = new LeitorCSV("Modelo_Controle_Orcamentario_Completo.csv");
		LinkedHashMap<Integer, Rubrica> map = ler.lerOrcamentoInicial();
	}
}
