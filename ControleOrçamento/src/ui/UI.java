package ui;


import java.time.LocalDate;

import negocios.GerenciadorPrevisao;
import negocios.PlanoContas;

public class UI {
	
	public static void main(String Args[]) {
		
		//====================Requisitos: 01 e 04====================//
		 PlanoContas rubricasAnoAnterior = new PlanoContas(); //cria o plano de contas
		 rubricasAnoAnterior.setOrcamentoAnterior("Modelo_Controle_Orcamentario_Completo.csv"); //carrega dados do arquivo do ano anterior
		 rubricasAnoAnterior.setDataCongelamento(LocalDate.of(2020, 1, 11)); //seta a data de congelamento
		//==========================================================//
		
		//====================Requisitos: 02 e 03========================//
		//Aqui, o gestou pode informar o código da rúbrica, que então será buscada da memória, uma vez que já foi lida e processada pelo requisito um e então setar um valor de previsão para ela 
		GerenciadorPrevisao gerenciadorPrevisao = new GerenciadorPrevisao();
		
		//Executando previsão por porcentagem  e printando resultado, importante notar que como o foco é com os dados mockados, já estamos mandando o aumento certinho, por exemplo, se queremos aumento de 20%, já estamos mandando 1,20 para a função
		gerenciadorPrevisao.execute(rubricasAnoAnterior, 1, 104, 1.2, 0);
		
		System.out.println(gerenciadorPrevisao.toString(rubricasAnoAnterior, 104, 0));
		//Executando previsao por valorFixo e printando resultado
		gerenciadorPrevisao.execute(rubricasAnoAnterior, 2, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(rubricasAnoAnterior, 104, 0));
		
		//exceutando previsão deixando a do ano anterior  e printando resultado
		gerenciadorPrevisao.execute(rubricasAnoAnterior, 3, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(rubricasAnoAnterior, 104, 0));
		
		//Gera arquivo das previsoes
		gerenciadorPrevisao.geraArquivoPrevisao(rubricasAnoAnterior);
		//==========================================================//
	}

	
}
