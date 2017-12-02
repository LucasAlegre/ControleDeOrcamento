package facade;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import negocios.AgenteAnaliseComparativa;
import negocios.AgenteOrcamentoInicial;
import negocios.AgentePrevisao;
import negocios.AgenteRealizadoMensal;
import negocios.PlanoContas;
import util.CategoriaMes;

public class GerenciadorFacade {
	
	private AgenteAnaliseComparativa gerenciadorAnaliseComparativa;
	private AgenteRealizadoMensal gerenciadorRealizadoMensal;
	private AgentePrevisao gerenciadorPrevisao;
	private AgenteOrcamentoInicial gerenciadorOrcamentoInicial;


	public GerenciadorFacade(PlanoContas plano) {
		gerenciadorAnaliseComparativa = new AgenteAnaliseComparativa(plano);
		gerenciadorRealizadoMensal = new AgenteRealizadoMensal(plano);
		gerenciadorPrevisao = new AgentePrevisao(plano);
		gerenciadorOrcamentoInicial = new AgenteOrcamentoInicial(plano);
		
	}
	
	public void geraPrevisao(int option, int codigo, double valor, int mes) {
		
		if(LocalDate.now().isBefore(gerenciadorPrevisao.getPlanoContas().getDataCongelamento()) && valor >=0) {
			
			switch(option) {
				case AgentePrevisao.PREVISAO_VALORPORCENTAGEM:
					gerenciadorPrevisao.previsaoPorcentagem(codigo, valor, mes);
					break;
				
				case AgentePrevisao.PREVISAO_VALORFIXO:
					gerenciadorPrevisao.previsaoValorFixo(codigo, valor, mes);
					break;
				
				case AgentePrevisao.PREVISAO_VALORANOANTERIOR:
					gerenciadorPrevisao.previsaoManterAnoAnterior(codigo, PlanoContas.getInstance().getRubricas().get(codigo).getvalorAnoPassado(mes), mes);
					break;
				
				default: 
					System.out.println("Opção não existente!");
			}
		}
		else {
			if(valor <=0) {
				System.out.println("Valor não pode ser negativo");
			}
			else {
				System.out.println("Data de congelamento atingida, desculpe, mas alterações nas previsões não podem mais ser feitas");	
			}
		}
	}
	
	public void lerOrcamentoInicial(String filename)throws FileNotFoundException{
		gerenciadorOrcamentoInicial.lerOrcamentoAnterior(filename);
	}
	
	public void geraTemplateRealizadoMensal(CategoriaMes mes) {
		gerenciadorRealizadoMensal.geraTemplateOrcamentoMensal(mes);
	}
	
	public void leRealizadoMensal(String filename, CategoriaMes mes) {
		gerenciadorRealizadoMensal.leRealizadoMensal(filename, mes);
	}
	
	public void geraAnalise(CategoriaMes mesInicial, CategoriaMes mesFinal) {
		gerenciadorAnaliseComparativa.geraAnaliseComparativa(mesInicial, mesFinal);
		
	}

	public void geraArquivoPrevisao() {
		gerenciadorPrevisao.geraArquivoPrevisao();
		
	}
}
