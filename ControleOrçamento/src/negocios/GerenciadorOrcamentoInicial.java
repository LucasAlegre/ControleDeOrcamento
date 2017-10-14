package negocios;

import java.util.LinkedHashMap;

import util.LeitorArquivo;

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
	
	/**
	 *  Le o arquivo inicial e cria as rubricas de acordo com o arquivo
	 */
	private void lerOrcamentoAnterior() {
		
		LeitorArquivo ler = new LeitorArquivo(super.getFileName());
		LinkedHashMap<Integer, Rubrica> rubricasIniciais = ler.lerOrcamentoInicial();
		
		getPlanoContas().setRubricas(rubricasIniciais);
	}
	
}