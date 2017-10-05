package negocios;

import java.util.LinkedHashMap;

import util.LeitorCSV;

/**
 *  Classe respons�vel por gerenciar a leitura do plano or�amentario do ano anterior da empresa.
 * 
 */
public class GerenciadorOrcamentoInicial implements Gerenciador {

	
	public void execute(String filename, PlanoContas planoContas) {
		
		planoContas.setOrcamentoAnterior(filename);

	}
	

	public static void main(String[] args) {
		
		PlanoContas planoContas = new PlanoContas();
		GerenciadorOrcamentoInicial g = new GerenciadorOrcamentoInicial();
		g.execute("Modelo_Controle_Orcamentario_Completo.csv", planoContas);
		
		planoContas.printPlanoBase();
	}
	
}
