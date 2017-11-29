package negocios;

import java.util.LinkedHashMap;

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
		
		GerenciadorArquivos ler = new GerenciadorArquivos();
		LinkedHashMap<Integer, Rubrica> rubricasIniciais = ler.lerOrcamentoInicial(filename);
		
		getPlanoContas().setRubricas(rubricasIniciais);
	}
	
}