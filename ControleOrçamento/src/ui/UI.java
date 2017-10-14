package ui;


import java.time.LocalDate;

import negocios.GerenciadorOrcamentoInicial;
import negocios.GerenciadorPrevisao;
import negocios.GerenciadorRealizadoMensal;
import negocios.PlanoContas;
import util.GeradorArquivo;
import util.CategoriaMes;

public class UI {
	
	public static void main(String Args[]) {
		

		// --- Leitura do Arquivo Inicial --requisito 01
		PlanoContas planoContas = new PlanoContas(); 
		GerenciadorOrcamentoInicial gInicial = new GerenciadorOrcamentoInicial("Modelo_Controle_Orcamentario_Completo.csv", planoContas);
		gInicial.execute();
		
		// -- Data de congelamento --requisito 04
		planoContas.setDataCongelamento(LocalDate.of(2020, 1, 11)); 
			
		
        // -- Previsões --requisito 02
		GerenciadorPrevisao gerenciadorPrevisao = new GerenciadorPrevisao(planoContas);
		
		// Previsão Porcentagem
		gerenciadorPrevisao.execute(1, 104, 1.2, CategoriaMes.JANEIRO.toInt());
		System.out.println(gerenciadorPrevisao.toString(104, CategoriaMes.JANEIRO.toInt()));
		
		// Previsão Valor Fixo
		gerenciadorPrevisao.execute(2, 104, 200, CategoriaMes.JANEIRO.toInt());
		System.out.println(gerenciadorPrevisao.toString(104, CategoriaMes.JANEIRO.toInt()));
		
		// Previsao mantendo valor do ano anterior
		gerenciadorPrevisao.execute(3, 104, 200, CategoriaMes.JANEIRO.toInt());
		System.out.println(gerenciadorPrevisao.toString(104, CategoriaMes.JANEIRO.toInt()));
		
		gerenciadorPrevisao.execute(3, 109, 900, CategoriaMes.AGOSTO.toInt());

		// Gera arquivo das previsoes --requisito 02
		gerenciadorPrevisao.geraArquivoPrevisao();
		
		// Gera arquivo para usuario completar o realizado mensal --requisito 05
		GerenciadorRealizadoMensal realizadoMensal = new GerenciadorRealizadoMensal("TemplateJaneiroCompletado.xls", planoContas);
		realizadoMensal.geraTemplateOrcamentoMensal(CategoriaMes.JANEIRO.toInt());
		
		// Le realizadoMensal--requisito 05
		realizadoMensal.leRealizadoMensal(CategoriaMes.JANEIRO.toInt());
		
	
	}

	
}
