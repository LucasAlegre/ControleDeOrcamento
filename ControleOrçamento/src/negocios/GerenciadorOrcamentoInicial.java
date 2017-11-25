package negocios;

import java.util.LinkedHashMap;

import util.LeitorArquivoCSV;

/**
 *  Classe respons�vel por gerenciar a leitura do plano or�amentario do ano anterior da empresa.
 * 
 */
public class GerenciadorOrcamentoInicial extends AbstractGerenciador {

	public GerenciadorOrcamentoInicial() {
		super();
	}
	

	/**
	 *  Le o arquivo inicial e cria as rubricas de acordo com o arquivo
	 */
	public void lerOrcamentoAnterior(String filename) {
		
		LeitorArquivoCSV ler = new LeitorArquivoCSV(filename);
		LinkedHashMap<Integer, Rubrica> rubricasIniciais = ler.lerOrcamentoInicial();
		
		getPlanoContas().setRubricas(rubricasIniciais);
	}
	
}