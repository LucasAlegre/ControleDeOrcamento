package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import facade.GerenciadorFacade;
import negocios.PlanoContas;
import util.CategoriaMes;
import util.GerenciadorArquivos;

public class GerenciadorArquivosTest {
	private GerenciadorArquivos gerenciador;
	private PlanoContas planoContas;
	
	@Before
	public void createPlanoContasAndAgents(){
		 planoContas = PlanoContas.getInstance();
		 gerenciador = new GerenciadorArquivos();
	}
	//===================interface-based tests:	===================//

	/*Partição: Filename ser nulo? 
	 * Opções de resposta: 
	 * Sim
	 * Não
	 */
	@Rule
	public ExpectedException expectedEx1 = ExpectedException.none();
	
	@Test(expected = java.lang.NullPointerException.class)
	public void nullFilenameOrcamentoInicial() throws FileNotFoundException{	
		gerenciador.lerOrcamentoInicial(null);
		expectedEx1.expectMessage("Expected exception:  java.lang.NullPointerException");
	}
	@Rule
	public ExpectedException expectedEx2 = ExpectedException.none();
	
	@Test(expected = java.lang.NullPointerException.class)
	public void nullFilenameRealizadoMensal() throws FileNotFoundException{	
		gerenciador.lerRealizadoMensal(null);
		expectedEx1.expectMessage("Expected exception:  java.lang.NullPointerException");
	}

	
	/*Partição: Filename ser do tipo errado? 
	 * Opções de resposta: 
	 * Sim
	 * Não
	 */
	@Rule
	public ExpectedException expectedEx3 = ExpectedException.none();

	@Test(expected = org.apache.poi.openxml4j.exceptions.InvalidFormatException.class)
	public void isThrowningExpectionWhenWrongType() throws FileNotFoundException{	
		gerenciador.lerRealizadoMensal("Modelo_Controle_Orcamentario_Completo.csv");
		expectedEx3.expectMessage("Expected exception: org.apache.poi.openxml4j.exceptions.InvalidFormatException");
	}

	
	//===================functionality-based tests:	===================//
	//temos de pensar como vamos ver se foi corretamente gerado, tipo, vai ter de ter um outro arquivo que vai ser lido com a resposta certa.
//	dai comparamos e vemos se foi sucesso
	
	
	/*Partição: Arquivo corretamente gerado? 
	 * Opções de resposta: 
	 * Sim
	 * Não
	 */
	
	/*Partição: Arquivo corretamente lido? 
	 * Opções de resposta: 
	 * Sim
	 * Não
	 */

}
