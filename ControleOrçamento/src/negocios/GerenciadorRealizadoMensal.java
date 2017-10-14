package negocios;
import java.util.HashMap;
import java.util.LinkedHashMap;

import util.GeradorArquivo;
import util.LeitorArquivo;;

/**
 * Classe respons�vel por controlar o que foi realmente realizado de gastos
 * na empresa.
 *
 */
public class GerenciadorRealizadoMensal extends Gerenciador {


	public GerenciadorRealizadoMensal(String filename, PlanoContas planoContas) {
		super(filename, planoContas);
	}
	
	/**
	 * Dado um plano de contas e um mes, gera um tamplate de arquivo .xls para o realizado mensal
	 * @param mes
	 */
	public void geraTemplateOrcamentoMensal(int mes) {
		
		GeradorArquivo gerador = new GeradorArquivo();
		gerador.geraTemplateRealizadoMensal(getPlanoContas(), mes);
	}
	
	/**
	 * Le o realizado mensal de um respectivo mes e atualiza as rubricas
	 * @param mes
	 */
	public void leRealizadoMensal(int mes) {
		
		LeitorArquivo leitor = new LeitorArquivo(getFileName());
		
		// Codigo para Valor realizado de cada rubrica
		LinkedHashMap<Integer, Double> realizado = leitor.lerRealizadoMensal();
		
		if(getPlanoContas().getRubricas().keySet().size() != realizado.keySet().size()) {
			System.out.println("Falta rúbrica no realizado mensal!");
		}
		
		for(Integer cod : getPlanoContas().getRubricas().keySet()) {
			
			Rubrica rubrica = getPlanoContas().getRubricas().get(cod);
			
			rubrica.setValorRealizado(mes, realizado.get(cod));
		}
		
		
	}
	
}
