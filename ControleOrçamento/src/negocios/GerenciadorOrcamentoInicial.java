package negocios;

import java.util.LinkedHashMap;

import util.LeitorCSV;

/**
 *  Classe responsável por gerenciar a leitura do plano orçamentario do ano anterior da empresa.
 * 
 */
public class GerenciadorOrcamentoInicial implements Gerenciador {

	
	public void execute(String filename, PlanoContas planoContas) {
		
		
		lerOrcamentoAnterior(planoContas, filename);

	}
	
	private void lerOrcamentoAnterior(PlanoContas plano, String filename) {
		
		LeitorCSV ler = new LeitorCSV(filename);
		LinkedHashMap<Integer, Rubrica> map = ler.lerOrcamentoInicial();
		plano.setRubricas(map);
	}
	
}
