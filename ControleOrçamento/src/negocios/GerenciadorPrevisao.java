package negocios;
import java.time.LocalDate;

import util.GeradorCSV;
import util.LeitorCSV;

/**
 *   Classe que gerencia a previsão para o próximo ano do 
 *   plano de contas.
 */
public class GerenciadorPrevisao implements Gerenciador {

	
	public GerenciadorPrevisao() {
		
	}

	//esse excecute foi a maneira que encontramos de colocar o padrão de projeto "Fachada", pois ele é o público que cmaha cada uma das diferentes previsões
	public void execute(PlanoContas planoContas, int option, int codigo, double valor, int mes) {
		if(LocalDate.now().isBefore(planoContas.getDataCongelamento())) {
			switch(option) {
				case 01:{
					previsaoPorcentagem(planoContas, codigo, valor, mes);
				}
				break;
				case 02:{
					previsaoValorFixo(planoContas, codigo, valor, mes);
				}
				break;
				case 03:{
					previsaoManterAnoAnteior(planoContas, codigo, planoContas.getRubricas().get(codigo).getvalorAnoPassado(mes), mes);
				}
				break;
				default: {
					System.out.println("Opção não existente!");
				}
			}
		}
		else {
			System.out.println("Data de congelamento atingida, desculpe, mas alterações nas previsões não podem mais ser feitas");
		}
	}
	private void previsaoPorcentagem(PlanoContas planoContas, int codigo, double valor, int mes) {
		try {
			planoContas.getRubricas().get(codigo).setValorPrevisto(mes, planoContas.getRubricas().get(codigo).getvalorAnoPassado(mes)*valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}

	private void previsaoValorFixo(PlanoContas planoContas, int codigo, double valor, int mes) {
		try {
			planoContas.getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}
	private void previsaoManterAnoAnteior(PlanoContas planoContas, int codigo, double valor, int mes) {
		try {
			planoContas.getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}
	
	@Override
	public void execute(String filename, PlanoContas planoContas) {

		// Chamar o tipo de previsao pra cada rubrica
	}
	public String toString(PlanoContas planoContas, int codigo, int mes) {
		return planoContas.getRubricas().get(codigo).getValorPrevisto(mes, codigo) + " mes: " + mes;
	}
	
	public void geraArquivoPrevisao(PlanoContas planoContas) {
		GeradorCSV orcamentoMensal = new GeradorCSV(planoContas, "OrcamentoMensal");
		orcamentoMensal.geraArquivoPrevisoes();
	}

}
