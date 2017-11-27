package negocios;

import java.util.LinkedHashMap;

import util.LeitorArquivo;

/**
 *  Classe respons�vel por gerenciar a leitura do plano or�amentario do ano anterior da empresa.
 * 
 */
public class AgenteOrcamentoInicial extends AgenteAbstract {

	public AgenteOrcamentoInicial(PlanoContas plano) {
		super(plano);
	}
	

	/**
	 *  Le o arquivo inicial e cria as rubricas de acordo com o arquivo
	 */
	public void lerOrcamentoAnterior(String filename) {
		
		LeitorArquivo ler = new LeitorArquivo(filename);
		LinkedHashMap<Integer, Rubrica> rubricasIniciais = ler.lerOrcamentoInicial();
		
		getPlanoContas().setRubricas(rubricasIniciais);
	}
	
}