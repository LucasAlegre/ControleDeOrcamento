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
		//Aqui, o gestou pode informar o c�digo da r�brica, que ent�o ser� buscada da mem�ria, uma vez que j� foi lida e processada pelo requisito um e ent�o setar um valor de previs�o para ela 
		GerenciadorPrevisao gerenciadorPrevisao = new GerenciadorPrevisao();
		
		//Executando previs�o por porcentagem  e printando resultado, importante notar que como o foco � com os dados mockados, j� estamos mandando o aumento certinho, por exemplo, se queremos aumento de 20%, j� estamos mandando 1,20 para a fun��o
		gerenciadorPrevisao.execute(rubricasAnoAnterior, 1, 104, 1.2, 0);
		
		System.out.println(gerenciadorPrevisao.toString(rubricasAnoAnterior, 104, 0));
		//Executando previsao por valorFixo e printando resultado
		gerenciadorPrevisao.execute(rubricasAnoAnterior, 2, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(rubricasAnoAnterior, 104, 0));
		
		//exceutando previs�o deixando a do ano anterior  e printando resultado
		gerenciadorPrevisao.execute(rubricasAnoAnterior, 3, 104, 200, 0);
		System.out.println(gerenciadorPrevisao.toString(rubricasAnoAnterior, 104, 0));
		
		//Gera arquivo das previsoes
		gerenciadorPrevisao.geraArquivoPrevisao(rubricasAnoAnterior);
		//==========================================================//
	}

	
}
