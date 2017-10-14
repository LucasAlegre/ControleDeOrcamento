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
public class GerenciadorRealizadoMensal extends Gerenciador {


	public GerenciadorRealizadoMensal(String filename, PlanoContas planoContas) {
		super(filename, planoContas);
	}
	
	public void execute() {
		
		//Se comunica com a UI de alguma forma pra ver a op��o
		//Se opcao == gerar template:
	    geraTemplateOrcamentoMensal(0);
		//Se opcao == ler orcamento mensal;
		//    leRealizadoMensal()
		
	}

	public void geraTemplateOrcamentoMensal(int mes) {
		
		GeradorCSV gerador = new GeradorCSV(super.getPlanoContas());
		gerador.geraTemplateRealizadoMensal(0);
	}
	
	
	public void leRealizadoMensal(int mes) {
		
		LeitorCSV leitor = new LeitorCSV(super.getFileName());
		LinkedHashMap<Integer, Double> realizado = leitor.lerRealizadoMensal();
		
		if(mes < 0 || mes > 12) {
			// throw exception
		}

		if(super.getPlanoContas().getRubricas().keySet().size() != realizado.keySet().size()) {
			System.out.println("Falta rúbrica no realizado mensal!");
		}
		
		for(Integer cod : super.getPlanoContas().getRubricas().keySet()) {
			
			Rubrica r = super.getPlanoContas().getRubricas().get(cod);
			
			r.setValorRealizado(mes, realizado.get(cod));
	
		}
		
		
	}
	
}
