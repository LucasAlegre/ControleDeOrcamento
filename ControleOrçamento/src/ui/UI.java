package ui;


import java.time.LocalDate;

import negocios.GerenciadorOrcamentoInicial;
import negocios.GerenciadorPrevisao;
import negocios.GerenciadorRealizadoMensal;
import negocios.PlanoContas;

public class UI {
	
	public static void main(String Args[]) {
		

		//====================Requisitos: 01 e 04====================//
		 PlanoContas planoContas = new PlanoContas(); //cria o plano de contas
		 GerenciadorOrcamentoInicial gInicial = new GerenciadorOrcamentoInicial();
		 gInicial.execute("Modelo_Controle_Orcamentario_Completo.csv", planoContas);
	
		 planoContas.setDataCongelamento(LocalDate.of(2020, 1, 11)); //seta a data de congelamento
		//==========================================================//
		
		//====================Requisitos: 02 e 03========================//
		//Aqui, o gestou pode informar o código da rúbrica, que então será buscada da memória, uma vez que já foi lida e processada pelo requisito um e então setar um valor de previsão para ela 
		GerenciadorPrevisao gerenciadorPrevisao = new GerenciadorPrevisao();
		
		//Executando previsão por porcentagem  e printando resultado, importante notar que como o foco é com os dados mockados, já estamos mandando o aumento certinho, por exemplo, se queremos aumento de 20%, já estamos mandando 1,20 para a função
		gerenciadorPrevisao.execute(planoContas, 1, 104, 1.2, 0);
		
		System.out.println(gerenciadorPrevisao.toString(planoContas, 104, 0));
		//Executando previsao por valorFixo e printando resultado
		gerenciadorPrevisao.execute(planoContas, 2, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(planoContas, 104, 0));
		
		//exceutando previsão deixando a do ano anterior  e printando resultado
		gerenciadorPrevisao.execute(planoContas, 3, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(planoContas, 104, 0));
		
		//Gera arquivo das previsoes
		gerenciadorPrevisao.geraArquivoPrevisao(planoContas);
		//==========================================================//
	}

	
}
