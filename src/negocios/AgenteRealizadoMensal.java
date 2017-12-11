package negocios;
import java.util.LinkedHashMap;

import util.CategoriaMes;
import util.GerenciadorArquivos;

/**
 * Classe respons�vel por controlar o que foi realmente realizado de gastos
 * na empresa.
 *
 */
public class AgenteRealizadoMensal extends AgenteAbstract {


	public AgenteRealizadoMensal(PlanoContas plano) {
		super(plano);
	}
	
	/**
	 * Dado um plano de contas e um mes, gera um tamplate de arquivo .xls para o realizado mensal
	 * @param mes
	 */
	public void geraTemplateOrcamentoMensal(CategoriaMes mes) {
		
		GerenciadorArquivos gerador = new GerenciadorArquivos();
		gerador.geraTemplateRealizadoMensal(getPlanoContas(), mes);
	}
	
	/**
	 * Le o realizado mensal de um respectivo mes e atualiza as rubricas
	 * @param mes
	 */
	public void leRealizadoMensal(String filename, CategoriaMes mes) {
		
		GerenciadorArquivos leitor = new GerenciadorArquivos();
		
		// Codigo para Valor realizado de cada rubrica
		LinkedHashMap<Integer, Double> realizado = leitor.lerRealizadoMensal(filename);
		
		if(getPlanoContas().getRubricas().keySet().size() != realizado.keySet().size()) {
			System.out.println("Falta rúbrica no realizado mensal!");
		}
		
		for(Integer cod : getPlanoContas().getRubricas().keySet()) {
			
			Rubrica rubrica = getPlanoContas().getRubricas().get(cod);
			
			rubrica.setValorRealizado(mes.toInt(), realizado.get(cod));
		}
		
		
	}
	
}
