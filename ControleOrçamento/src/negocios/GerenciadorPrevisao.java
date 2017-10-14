package negocios;
import java.time.LocalDate;

import util.GeradorArquivo;

/**
 *   Classe que gerencia a previs�o para o pr�ximo ano do 
 *   plano de contas.
 */
public class GerenciadorPrevisao extends Gerenciador {

	
	public GerenciadorPrevisao(PlanoContas planoContas) {
		super("Previsoes.xls", planoContas);
	}

	/**
	 * Esse excecute foi a maneira que encontramos de colocar o padr�o de projeto "Fachada", pois ele � o p�blico que chama cada uma das diferentes previs�es
	 * 
	 * @param option 1 - Porcentagem 2 - Valor fixo 3  - Manter valor anterior 
	 * @param codigo Codigo da Rubrica
	 * @param valor 
	 * @param mes
	 */
	public void execute(int option, int codigo, double valor, int mes) {
		if(LocalDate.now().isBefore(super.getPlanoContas().getDataCongelamento())) {
			switch(option) {
				case 01:{
					previsaoPorcentagem(codigo, valor, mes);
				}
				break;
				case 02:{
					previsaoValorFixo(codigo, valor, mes);
				}
				break;
				case 03:{
					previsaoManterAnoAnterior(codigo, super.getPlanoContas().getRubricas().get(codigo).getvalorAnoPassado(mes), mes);
				}
				break;
				default: {
					System.out.println("Op��o n�o existente!");
				}
			}
		}
		else {
			System.out.println("Data de congelamento atingida, desculpe, mas altera��es nas previs�es n�o podem mais ser feitas");
		}
	}
	
	private void previsaoPorcentagem(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes, super.getPlanoContas().getRubricas().get(codigo).getvalorAnoPassado(mes)*valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O c�digo digitado n�o existe!");
		}
	}

	private void previsaoValorFixo(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O c�digo digitado n�o existe!");
		}
	}
	
	private void previsaoManterAnoAnterior(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O c�digo digitado n�o existe!");
		}
	}
	
	
	public String toString(int codigo, int mes) {
		
		return super.getPlanoContas().getRubricas().get(codigo).getValorPrevisto(mes, codigo) + " mes: " + mes;
	}
	
	public void geraArquivoPrevisao() {
		GeradorArquivo orcamentoMensal = new GeradorArquivo();
		orcamentoMensal.geraArquivoPrevisoes(getPlanoContas(), "Previsoes.xls");
	}

}