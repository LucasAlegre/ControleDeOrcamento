package negocios;

import java.util.LinkedHashMap;

import util.LeitorCSV;

/**
 *  Classe responsável por gerenciar a leitura do plano base da empresa.
 * 
 */
public class GerenciadorPlanoBase implements Gerenciador {

	
	public void execute(String filename, PlanoContas planoContas) {
		
		planoContas.lePlanoBase(filename);

	}
	

	public static void main(String[] args) {
		PlanoContas planoContas = new PlanoContas();
		GerenciadorPlanoBase g = new GerenciadorPlanoBase();
		g.execute("PlanoBase.csv", planoContas);
		
		for(Integer i : planoContas.getRubricas().keySet()) {
			System.out.println(i);
		}
	}
	
}
