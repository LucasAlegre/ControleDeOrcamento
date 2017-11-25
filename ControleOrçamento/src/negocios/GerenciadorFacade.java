package negocios;

import java.time.LocalDate;

import util.CategoriaMes;

public class GerenciadorFacade {
	
	private GerenciadorAnaliseComparativa gerenciadorAnaliseComparativa;
	private GerenciadorRealizadoMensal gerenciadorRealizadoMensal;
	private GerenciadorPrevisao gerenciadorPrevisao;
	private GerenciadorOrcamentoInicial gerenciadorOrcamentoInicial;


	public GerenciadorFacade() {
		
		gerenciadorAnaliseComparativa = new GerenciadorAnaliseComparativa();
		gerenciadorRealizadoMensal = new GerenciadorRealizadoMensal();
		gerenciadorPrevisao = new GerenciadorPrevisao();
		gerenciadorOrcamentoInicial = new GerenciadorOrcamentoInicial();
		
	}
	
	public void geraPrevisao(int option, int codigo, double valor, int mes) {
		
		if(LocalDate.now().isBefore(gerenciadorPrevisao.getPlanoContas().getDataCongelamento())) {
			
			switch(option) {
				case GerenciadorPrevisao.PREVISAO_VALORPORCENTAGEM:
					gerenciadorPrevisao.previsaoPorcentagem(codigo, valor, mes);
					break;
				
				case GerenciadorPrevisao.PREVISAO_VALORFIXO:
					gerenciadorPrevisao.previsaoValorFixo(codigo, valor, mes);
					break;
				
				case GerenciadorPrevisao.PREVISAO_VALORANOANTERIOR:
					gerenciadorPrevisao.previsaoManterAnoAnterior(codigo, PlanoContas.getInstance().getRubricas().get(codigo).getvalorAnoPassado(mes), mes);
					break;
				
				default: 
					System.out.println("Opção não existente!");
			}
		}
		else {
			System.out.println("Data de congelamento atingida, desculpe, mas alterações nas previsões não podem mais ser feitas");
		}
	}
	
	public void lerOrcamentoInicial(String filename) {
		gerenciadorOrcamentoInicial.lerOrcamentoAnterior(filename);
	}
	
	public void geraTemplateRealizadoMensal(CategoriaMes mes) {
		gerenciadorRealizadoMensal.geraTemplateOrcamentoMensal(mes);
	}
	
	public void leRealizadoMensal(String filename, CategoriaMes mes) {
		gerenciadorRealizadoMensal.leRealizadoMensal(filename, mes);
	}
	
	public void geraAnalise() {
		
	}
}
