package negocios;

import java.util.LinkedHashMap;

import util.LeitorCSV;

/**
 *  Classe respons�vel por gerenciar a leitura do plano or�amentario do ano anterior da empresa.
 * 
 */
public class GerenciadorOrcamentoInicial extends Gerenciador {

	public GerenciadorOrcamentoInicial(String filename, PlanoContas planoContas) {
		super(filename, planoContas);
	}
	
	public void execute() {
		
		lerOrcamentoAnterior();

	}
	
	private void lerOrcamentoAnterior() {
		
		LeitorCSV ler = new LeitorCSV(super.getFileName());
		LinkedHashMap<Integer, Rubrica> map = ler.lerOrcamentoInicial();
		super.getPlanoContas().setRubricas(map);
	}
	
}