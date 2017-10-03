package negocios;

import java.util.LinkedHashMap;

import util.LeitorCSV;

/**
 *  Classe responsável por gerenciar a leitura do plano base da empresa.
 * 
 */
public class GerenciadorPlanoBase implements Gerenciador {

	
	public void execute(String filename, PlanoContas planoContas) {
		
		planoContas = new PlanoContas();
		LeitorCSV ler = new LeitorCSV("PlanoBase.csv");
		LinkedHashMap<Integer, Rubrica> map = ler.lerPlanoBase();
		planoContas.setRubricas(map);
		
	}
	
	
}
