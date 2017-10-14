package ui;


import java.time.LocalDate;

import negocios.GerenciadorOrcamentoInicial;
import negocios.GerenciadorPrevisao;
import negocios.GerenciadorRealizadoMensal;
import negocios.PlanoContas;
import util.GeradorArquivo;
import util.Mes;

public class UI {
	
	public static void main(String Args[]) {
		

		// --- Leitura do Arquivo Inicial
		PlanoContas planoContas = new PlanoContas(); 
		GerenciadorOrcamentoInicial gInicial = new GerenciadorOrcamentoInicial("Modelo_Controle_Orcamentario_Completo.csv", planoContas);
		gInicial.execute();
		
		// -- Data de congelamento
		planoContas.setDataCongelamento(LocalDate.of(2020, 1, 11)); 
			
		
        // -- Previsões
		GerenciadorPrevisao gerenciadorPrevisao = new GerenciadorPrevisao(planoContas);
		
		// Previsão Porcentagem
		gerenciadorPrevisao.execute(1, 104, 1.2, Mes.JANEIRO.toInt());
		System.out.println(gerenciadorPrevisao.toString(104, Mes.JANEIRO.toInt()));
		
		// Previsão Valor Fixo
		gerenciadorPrevisao.execute(2, 104, 200, Mes.JANEIRO.toInt());
		System.out.println(gerenciadorPrevisao.toString(104, Mes.JANEIRO.toInt()));
		
		// Previsao mantendo valor do ano anterior
		gerenciadorPrevisao.execute(3, 104, 200, Mes.JANEIRO.toInt());
		System.out.println(gerenciadorPrevisao.toString(104, Mes.JANEIRO.toInt()));
		
		gerenciadorPrevisao.execute(3, 109, 900, Mes.AGOSTO.toInt());

		// Gera arquivo das previsoes
		gerenciadorPrevisao.geraArquivoPrevisao();
		
		// Gera arquivo para usuario completar o realizado mensal
		GerenciadorRealizadoMensal realizadoMensal = new GerenciadorRealizadoMensal("TemplateJaneiroCompletado.xls", planoContas);
		realizadoMensal.geraTemplateOrcamentoMensal(Mes.JANEIRO.toInt());
		
		// Le realizadoMensal
		realizadoMensal.leRealizadoMensal(Mes.JANEIRO.toInt());
		
	
	}

	
}
