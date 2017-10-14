package ui;


import java.time.LocalDate;

import negocios.GerenciadorOrcamentoInicial;
import negocios.GerenciadorPrevisao;
import negocios.GerenciadorRealizadoMensal;
import negocios.PlanoContas;
import util.GeradorCSV;

public class UI {
	
	public static void main(String Args[]) {
		

		//====================Requisitos: 01 e 04====================//
		 PlanoContas planoContas = new PlanoContas(); //cria o plano de contas
		 GerenciadorOrcamentoInicial gInicial = new GerenciadorOrcamentoInicial("Modelo_Controle_Orcamentario_Completo.csv", planoContas);
		 gInicial.execute();
	
		 planoContas.setDataCongelamento(LocalDate.of(2020, 1, 11)); //seta a data de congelamento
		//==========================================================//
		
		//====================Requisitos: 02 e 03========================//
		//Aqui, o gestou pode informar o c�digo da r�brica, que ent�o ser� buscada da mem�ria, uma vez que j� foi lida e processada pelo requisito um e ent�o setar um valor de previs�o para ela 
		GerenciadorPrevisao gerenciadorPrevisao = new GerenciadorPrevisao(planoContas);
		
		//Executando previs�o por porcentagem  e printando resultado, importante notar que como o foco � com os dados mockados, j� estamos mandando o aumento certinho, por exemplo, se queremos aumento de 20%, j� estamos mandando 1,20 para a fun��o
		gerenciadorPrevisao.execute(1, 104, 1.2, 0);
		
		System.out.println(gerenciadorPrevisao.toString(104, 0));
		//Executando previsao por valorFixo e printando resultado
		gerenciadorPrevisao.execute(2, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(104, 0));
		
		//exceutando previs�o deixando a do ano anterior  e printando resultado
		gerenciadorPrevisao.execute(3, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(104, 0));
		
		
		gerenciadorPrevisao.execute(3, 109, 900, 7);

		
		//Gera arquivo das previsoes
		gerenciadorPrevisao.geraArquivoPrevisao();
		//==========================================================//
		
		GerenciadorRealizadoMensal realizadoMensal = new GerenciadorRealizadoMensal("Janeiro.xls", planoContas);
		realizadoMensal.geraTemplateOrcamentoMensal(0);
		realizadoMensal.leRealizadoMensal(0);
		
		
		
	}

	
}
