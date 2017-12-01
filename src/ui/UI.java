package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

import negocios.AgenteOrcamentoInicial;
import negocios.PlanoContas;
import facade.GerenciadorFacade;

public class UI {
	
	private boolean executing = true;
	private boolean fileExists = false;
	private boolean dateIsValid = false;
	private boolean optionValid = false;
	private boolean predictionEnd = false;

	public void executeUI(){
		PlanoContas planoContas = PlanoContas.getInstance();
		GerenciadorFacade facade = new GerenciadorFacade(planoContas);
		
		String csvName;
		int day, month, year;
		int predicitionOp;
		int predictionCode;
		int predictionValue;
		int predictionMonth;
		String predicitionContinues;
		
		while(this.executing) {
			
			//Requisição do plano de contas inicial
			while(!this.fileExists) {
				csvName = this.askUser("Digite o nome do arquivo que se deseja ler(.csv)(0 para sair)", "0");
				try {
					facade.lerOrcamentoInicial(csvName);
					this.fileExists = true;
				}catch(FileNotFoundException e) {
					System.out.println("Arquivo não encontrado!\n");
				}
			}
			
			while(!this.dateIsValid) {
				day = Integer.valueOf(this.askUser("Digite a data de congelamento(dd/mm/yyyy)(0 para sair)", "0"));
				month = Integer.valueOf(this.askUser("", ""));
				year = Integer.valueOf(this.askUser("", ""));
				
				if(this.validateDate(day, month, year)) {
					planoContas.setDataCongelamento(LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
					this.dateIsValid = true;
				}
				else System.out.println("Data inválida!\n");
			}
			
			while(!this.optionValid) {
				predicitionOp = Integer.valueOf(this.askUser("Digite a opção para a previsão: 1-Porcentagem   2-Valor Fixo   3-Manter Valor Passado (0 para sair)", "0"));
				if(predicitionOp < 1 || predicitionOp > 3) System.out.println("Opção não reconhecida!!");
				else {
					while(!this.predictionEnd) {
						optionValid = true;
						predictionCode = Integer.valueOf(this.askUser("Digite o código da Rubrica que desejas prever o valor", ""));
						
						if(predicitionOp == 2)predictionValue = 0;
						else predictionValue = Integer.valueOf(this.askUser("Digite o valor de alteração da Rubrica que desejas prever o valor", ""));
						predictionMonth = Integer.valueOf(this.askUser("Digite o mes de previsão da Rubrica que desejas prever o valor", ""));
						facade.geraPrevisao(predicitionOp, predictionCode, predictionValue, predictionMonth);
						predicitionContinues = this.askUser("Desejas continuar a previsão?(s/n)(0 para sair)", "");
						if(predicitionContinues.equals("n")) this.predictionEnd = true;
					}
				}
			}
			
			this.askUser("Queres continuar a listar previsões? N para não", "N");
			
		}
	}
	
	private String askUser(String question, String quitOp) {
		Scanner inputChannel = new Scanner(System.in);
		System.out.println(question+"\n");
		String input = inputChannel.nextLine();
		if(input.equals(quitOp) && !quitOp.equals("")) {
			this.executing = false;
			System.out.println("Encerrando");
			System.exit(-1);;
		}
		return input;		
	}
	
	private boolean validateDate(int day, int month, int year) {
		
		Calendar calendar = Calendar.getInstance();
		boolean monthIsValid = month<=12 && month>=1;
		
		int yearLowerLimit = calendar.get(calendar.YEAR);
		boolean yearIsValid = year > yearLowerLimit;
		boolean yearIsLeapYear = year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
		
		int dayUpperLimit, dayLowerLimit=1;
		
		if(month % 2 == 0) dayUpperLimit = 30;
		else dayUpperLimit = 31;
		
		if(month == 2) {
			if(yearIsLeapYear)dayUpperLimit = 29;
			else dayUpperLimit = 28;
		}
		
		boolean dayIsValid = day >= dayLowerLimit && day <= dayUpperLimit; 
		
		return dayIsValid && monthIsValid && yearIsValid;
	}
	
	public static void main(String Args[]) {
		UI ui = new UI();
		
		ui.executeUI();
		
		/**

		// --- Leitura do Arquivo Inicial --requisito 01
		PlanoContas planoContas = PlanoContas.getInstance();
		
		AgenteOrcamentoInicial gInicial = new GerenciadorOrcamentoInicial("Modelo_Controle_Orcamentario_Completo.csv", planoContas);
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
		**/
	
	}
	
	

	
}
