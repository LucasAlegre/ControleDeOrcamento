package negocios;
import java.time.LocalDate;

import util.GeradorArquivo;

/**
 *   Classe que gerencia a previs�o para o pr�ximo ano do 
 *   plano de contas.
 */
public class GerenciadorPrevisao extends AbstractGerenciador {

	public static final int PREVISAO_VALORFIXO = 0;
	public static final int PREVISAO_VALORPORCENTAGEM = 1;
	public static final int PREVISAO_VALORANOANTERIOR = 2;

	
	public GerenciadorPrevisao() {
		super();
	}

	public void previsaoPorcentagem(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes, super.getPlanoContas().getRubricas().get(codigo).getvalorAnoPassado(mes)*valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}

	public void previsaoValorFixo(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}
	
	public void previsaoManterAnoAnterior(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
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