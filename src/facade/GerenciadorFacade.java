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
	
	private AgenteAnaliseComparativa agenteAnaliseComparativa;
	private AgenteRealizadoMensal agenteRealizadoMensal;
	private AgentePrevisao agentePrevisao;
	private AgenteOrcamentoInicial agenteOrcamentoInicial;


	public GerenciadorFacade(PlanoContas plano) {
		agenteAnaliseComparativa = new AgenteAnaliseComparativa(plano);
		agenteRealizadoMensal = new AgenteRealizadoMensal(plano);
		agentePrevisao = new AgentePrevisao(plano);
		agenteOrcamentoInicial = new AgenteOrcamentoInicial(plano);
		
	}
	
	public void geraPrevisao(int option, int codigo, double valor, int mes) {
		
		if(LocalDate.now().isBefore(agentePrevisao.getPlanoContas().getDataCongelamento()) && valor >= 0) {
			
			switch(option) {
				case AgentePrevisao.PREVISAO_VALORPORCENTAGEM:
					agentePrevisao.previsaoPorcentagem(codigo, valor, mes);
					break;
				
				case AgentePrevisao.PREVISAO_VALORFIXO:
					agentePrevisao.previsaoValorFixo(codigo, valor, mes);
					break;
				
				case AgentePrevisao.PREVISAO_VALORANOANTERIOR:
					agentePrevisao.previsaoManterAnoAnterior(codigo, PlanoContas.getInstance().getRubricas().get(codigo).getvalorAnoPassado(mes), mes);
					break;
				
				default: 
					System.out.println("Opção não existente!");
			}
		}
		else {
			if(valor <= 0) {
				System.out.println("Valor não pode ser negativo");
			}
			else {
				System.out.println("Data de congelamento atingida, desculpe, mas alterações nas previsões não podem mais ser feitas");	
			}
		}
	}
	
	public void lerOrcamentoInicial(String filename)throws FileNotFoundException{
		agenteOrcamentoInicial.lerOrcamentoAnterior(filename);
	}
	
	public void geraTemplateRealizadoMensal(CategoriaMes mes) {
		agenteRealizadoMensal.geraTemplateOrcamentoMensal(mes);
	}
	
	public void leRealizadoMensal(String filename, CategoriaMes mes) {
		agenteRealizadoMensal.leRealizadoMensal(filename, mes);
	}
	
	public void geraAnalise(CategoriaMes mesInicial, CategoriaMes mesFinal) {
<<<<<<< HEAD
		agenteAnaliseComparativa.geraAnaliseComparativa(mesInicial, mesFinal);
=======
		gerenciadorAnaliseComparativa.geraAnaliseComparativa(mesInicial, mesFinal);
>>>>>>> branch 'master' of https://github.com/LucasAlegre/ControleDeOrcamento.git
		
	}

	public void geraArquivoPrevisao() {
		agentePrevisao.geraArquivoPrevisao();
		
	}
}
