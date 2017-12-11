package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import facade.GerenciadorFacade;
import negocios.PlanoContas;
import negocios.Rubrica;
import util.CategoriaRubrica;

public class RubricaTest {


	private GerenciadorFacade gerenciador;
	private PlanoContas planoContas;
	
	@Before
	public void createPlanoContasAndAgents() throws FileNotFoundException{
		 planoContas = PlanoContas.getInstance();
		 gerenciador = new GerenciadorFacade(planoContas);
		 gerenciador.lerOrcamentoInicial("Modelo_Controle_Orcamentario_Completo.csv");
		 planoContas.setDataCongelamento(LocalDate.now().plusYears(1));
	}
	
	//===================functionality-based tests:	===================//
	
		/*Partição: Subrúbricas estão corretas? 
		 * Opções de resposta: 
		 * Sim
		 * Não
		 */
	//=NAO SEI COMO FAZER FUNCIONAR ESSA PORRA,
	@Test
	public void getSubRubricas() {
		String correctAnswer = "[Devoluções  105  169199.0"+
		                 ", ICMS  106  7749.0" +
		                 ", ISSQN s/ Servicos  107  160131.0" +
		                 ", Pis s/ Faturamento  108  70412.0" +
		                 ", Cofins  109  324972.0" +
		                 ", Remessa de Mercadoria  936  0.0]";
		List<Rubrica> subrubricasDa2396 = null;
		Map<Integer, Rubrica> rubrica =  planoContas.getInstance().getRubricas();
		for (Map.Entry<Integer, Rubrica> entry : rubrica.entrySet())
		{
			if(entry.getValue().getCodigo() == 2396) {
				subrubricasDa2396 = entry.getValue().getSubRubricas();	
			}
		}
	//	System.out.println(correctAnswer);
	//	System.out.println(subrubricasDa2396);
		assertEquals(subrubricasDa2396 , correctAnswer);
	}
	/*Partição: Adicionar uma subrúbrica está correto? 
	 * Opções de resposta: 
	 * Sim
	 * Não
	 */
//vVER COM O GUI PORQUE NAO FUNCIONA, já que quem criou o método de add foi ele 
	@Test
	public void addSubRubricas() {
		Rubrica subrubricaAdicionada = new Rubrica( (Rubrica)null, "Teste", -1, CategoriaRubrica.RECEITA, (Double[])null);
		String correctAnswer = null;
		List<Rubrica> subrubricasDa103 = null;
		Map<Integer, Rubrica> rubrica =  planoContas.getInstance().getRubricas();
		for (Map.Entry<Integer, Rubrica> entry : rubrica.entrySet())
		{
			if(entry.getValue().getCodigo() == 103) {
				System.out.println(entry.getValue().getSubRubricas());
				entry.getValue().addSubRubrica(subrubricaAdicionada);	
				subrubricasDa103 = entry.getValue().getSubRubricas();
			}
		}
		System.out.println(correctAnswer);
		System.out.println(subrubricasDa103);
		assertEquals(subrubricasDa103 , correctAnswer);
	}
}
