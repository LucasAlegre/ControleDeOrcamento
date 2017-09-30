package negocios;
import util.LeitorCSV;

/**
 *   Classe que gerencia a previsão para o próximo ano do 
 *   plano de contas.
 */
public class GerenciadorPrevisao implements Gerenciador {

	private PlanoContas planoContas;
	
	/**
	 *  Le o arquivo de plano contas do ano anterior e atualiza-o com as rubricas.
	 * 
	 * @param filename - Nome do arquivo com o plano inicial
	 */
	private void lePlanoInicial(String filename) {
		LeitorCSV leitor = new LeitorCSV(filename);
		//String linha = leitor.next();
		// checa linha {
			// se rubrica
		   //	planoContas.addRubrica(linha);
		
		   		
		//No fim deste método temos o plano de contas com todas rubricas
		
	}
		
	public GerenciadorPrevisao(PlanoContas planoContas) {
		
		this.planoContas = planoContas;
	}
	
	
	@Override
	public void execute(String filename) {
		
		lePlanoInicial(filename);
		// Chamar o tipo de previsao pra cada rubrica
		planoContas.geraPrevisao();
		
	}

}
