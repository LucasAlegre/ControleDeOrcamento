package negocios;
import java.util.HashMap;
import java.util.LinkedHashMap;

import util.GeradorCSV;
import util.LeitorCSV;;

/**
 * Classe respons�vel por controlar o que foi realmente realizado de gastos
 * na empresa.
 *
 */
public class GerenciadorRealizadoMensal implements Gerenciador {


	public GerenciadorRealizadoMensal() {

	}
	
	public void execute(String filename, PlanoContas planoContas) {
		
		//Se comunica com a UI de alguma forma pra ver a op��o
		//Se opcao == gerar template:
	    geraTemplateOrcamentoMensal(planoContas, 0);
		//Se opcao == ler orcamento mensal;
		//    leRealizadoMensal()
		
	}

	public void geraTemplateOrcamentoMensal(PlanoContas planoContas, int mes) {
		
		GeradorCSV gerador = new GeradorCSV(planoContas);
		gerador.geraTemplateRealizadoMensal(0);
	}
	
	
	public void leRealizadoMensal(PlanoContas planoContas, String filename, int mes) {
		
		LeitorCSV leitor = new LeitorCSV(filename);
		LinkedHashMap<Integer, Double> realizado = leitor.lerRealizadoMensal();
		
		if(mes < 0 || mes > 12) {
			// throw exception
		}

		if(planoContas.getRubricas().keySet().size() != realizado.keySet().size()) {
			System.out.println("Falta rúbrica no realizado mensal!");
		}
		
		for(Integer cod : planoContas.getRubricas().keySet()) {
			
			Rubrica r = planoContas.getRubricas().get(cod);
			
			r.setValorRealizado(mes, realizado.get(cod));
	
		}
		
		
	}
	
}
