package negocios;
import util.LeitorCSV;

/**
 *   Classe que gerencia a previs�o para o pr�ximo ano do 
 *   plano de contas.
 */
public class GerenciadorPrevisao implements Gerenciador {

	
	/**
	 *  Le o arquivo de plano contas do ano anterior e atualiza-o com as rubricas.
	 * 
	 * @param filename - Nome do arquivo com o plano inicial
	 */
	private void lePlanoInicial(String filename) {

	
	}
		
	public GerenciadorPrevisao() {
		
	}
	
	
	@Override
	public void execute(String filename, PlanoContas planoContas) {
		
		lePlanoInicial(filename);
		// Chamar o tipo de previsao pra cada rubrica
		
	}

}
