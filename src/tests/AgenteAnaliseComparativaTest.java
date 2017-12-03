package tests;

import static org.junit.Assert.*;

import java.awt.List;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import facade.GerenciadorFacade;
import negocios.AgenteAnaliseComparativa;
import negocios.CategoriaRubrica;
import negocios.PlanoContas;
import negocios.Rubrica;
import util.CategoriaMes;

public class AgenteAnaliseComparativaTest {

	private GerenciadorFacade gerenciador;
	private PlanoContas planoContas;
	private AgenteAnaliseComparativa analiseComp;

	@Before
	public void createPlanoContasAndAgents() throws FileNotFoundException {
		planoContas = PlanoContas.getInstance();
		gerenciador = new GerenciadorFacade(planoContas);
		gerenciador.lerOrcamentoInicial("Modelo_Controle_Orcamentario_Completo.csv");
		planoContas.setDataCongelamento(LocalDate.now().plusYears(1));
		analiseComp = new AgenteAnaliseComparativa(planoContas);
		// gerenciador.geraAnalise(CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
	}
	// ===================interface-based tests: ===================//

	// Domínio: método geraValoresRubrica() do AgenteAnáliseComparativa, jutamente
	// com todas rúbricas

	// ESSA NÃO DEVERIA FUNCIONAR, NAO TEM NOME NA RUBRICA
	/*
	 * Partição: Valor para prever ser nulo no nome da rubrica, avisa o usuário?
	 * Opções de resposta: Sim Não
	 */
	@Test
	public void geraValoresRubricaSemNome() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("0", "SEM NOME", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Rubrica mae = new Rubrica(null, null, 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, null, 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorPrevisto(0, 100);
		filha.setValorRealizado(0, 200);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		assertEquals(correctAnswer, valores);

	}

	/*
	 * Partição: Valor para prever ser nulo no código da rubrica, gera erro(rubrica
	 * filha como dominio)? Opções de resposta: Sim Não
	 */
	@Rule
	public ExpectedException expectedEx1 = ExpectedException.none();

	@Test(expected = java.lang.NullPointerException.class)
	public void geraValoresRubricaSemCodigoNaFilha() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", (Integer) null, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorPrevisto(0, 100);
		filha.setValorRealizado(0, 200);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		expectedEx1.expectMessage("Expected exception:  java.lang.NullPointerException");

	}

	/*
	 * Partição: Valor para prever ser nulo no código da rubrica, gera erro(rubrica
	 * mae como dominio)? Opções de resposta: Sim Não
	 */
	@Rule
	public ExpectedException expectedEx2 = ExpectedException.none();

	@Test(expected = java.lang.NullPointerException.class)
	public void geraValoresRubricaSemCodigoNaMae() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", (Integer) null, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorPrevisto(0, 100);
		filha.setValorRealizado(0, 200);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		expectedEx2.expectMessage("Expected exception:  java.lang.NullPointerException");

	}
	// ERRO: Sem Valor previsto é devolvido zero, e o NAN q devolve ali, fazemos o q
	// com ele??

	/*
	 * Partição: Sem Valor previsto é devolvido zero? Opções de resposta: Sim Não
	 */
	@Test
	public void geraValoresRubricaSemValorPrevisto() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		ArrayList<String> valoresEsperados = new ArrayList<String>(
				Arrays.asList("0", "mae", "0.0", "0.0", "0.0", "0.0%", ":)"));
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		assertEquals(valoresEsperados, valores);

	}

	// ===================functionality-based tests: ===================//

	// Domínio: método geraValoresRubrica() do AgenteAnáliseComparativa, jutamente
	// com todas rúbricas
	@Test
	public void geraValoresRubricaComSubrubrica() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("0", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorPrevisto(0, 100);
		filha.setValorRealizado(0, 200);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		assertEquals(correctAnswer, valores);

	}

	/*
	 * Partição: Rúbrica sem subrubrica no método geraValoresRubrica? Opções de
	 * resposta: Sim Não
	 */
	@Test
	public void geraValoresRubricaSemSubrubrica() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("1", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Rubrica mae = new Rubrica(null, "mae", 1, CategoriaRubrica.DESPESA, null);
		mae.setValorPrevisto(0, 100);
		mae.setValorRealizado(0, 200);
		mae.setValorPrevisto(1, 100);
		mae.setValorRealizado(1, 200);
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		assertEquals(correctAnswer, valores);

	}

	// PARA QUE TU USA A LISTA DE VALORES ANO PASSADO???
	/*
	 * Partição: Rúbrica sem com valoresAnoPassado não nulo funciona corretamente?
	 * Opções de resposta: Sim Não
	 */
	@Test
	public void geraValoresRubricaComValoresAnoPassado() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("1", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Double[] valoresAnoPassado = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		Rubrica mae = new Rubrica(null, "mae", 1, CategoriaRubrica.DESPESA, valoresAnoPassado);
		mae.setValorPrevisto(0, 100);
		mae.setValorRealizado(0, 200);
		mae.setValorPrevisto(1, 100);
		mae.setValorRealizado(1, 200);
		ArrayList<String> valores = analiseComp.geraValoresRubrica(mae, CategoriaMes.JANEIRO, CategoriaMes.FEVEREIRO);
		assertEquals(correctAnswer, valores);

	}
	// Domínio: método somaValoresPrevistosSubrubricas() do
	// AgenteAnáliseComparativa, jutamente com todas rúbricas

	/*
	 * Partição: Soma retorna resultado correto quando nao tem subricas e valor
	 * previsto? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresPrevistosSubrubricasSemSubrubricasSemValorPrevisto() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("1", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Double[] valoresAnoPassado = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		Rubrica mae = new Rubrica(null, "mae", 1, CategoriaRubrica.DESPESA, valoresAnoPassado);
		mae.setValorRealizado(0, 200);
		mae.setValorRealizado(1, 200);
		Double valores = analiseComp.somaValoresPrevistosSubrubricas(mae, 02);
		assertEquals(0.0, valores, 0.0000000001);
	}

	/*
	 * Partição: Soma retorna resultado correto quando nao tem subricas, mas tem
	 * valor previsto? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresPrevistosSubrubricasSemSubrubricasComValorPrevisto() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("1", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Double[] valoresAnoPassado = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		Rubrica mae = new Rubrica(null, "mae", 1, CategoriaRubrica.DESPESA, valoresAnoPassado);
		mae.setValorPrevisto(0, 100);
		mae.setValorRealizado(0, 200);
		mae.setValorPrevisto(1, 100);
		mae.setValorRealizado(1, 200);
		Double valores = analiseComp.somaValoresPrevistosSubrubricas(mae, 01);
		assertEquals(100.0, valores, 0.0000000001);
	}

	/*
	 * Partição: Soma retorna resultado correto quando tem subricas e valor
	 * previsto? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresPrevistosSubrubricasComSubrubricasComValorPrevisto() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorPrevisto(0, 100);
		filha.setValorRealizado(0, 200);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		Double valores = analiseComp.somaValoresPrevistosSubrubricas(mae, 01);
		assertEquals(100.0, valores, 0.0000000001);
	}

	/*
	 * Partição: Soma retorna resultado correto quando tem subricas e não tem valor
	 * previsto? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresPrevistosSubrubricasComSubrubricassemValorPrevisto() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorRealizado(0, 200);
		filha.setValorRealizado(1, 200);
		Double valores = analiseComp.somaValoresPrevistosSubrubricas(mae, 01);
		assertEquals(0.0, valores, 0.0000000001);
	}
	// Domínio: método somaValoresRealizadosSubrubricas() do
	// AgenteAnáliseComparativa, jutamente com todas rúbricas

	/*
	 * Partição: Soma retorna resultado correto quando nao tem subricas e valor
	 * realizado? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresRealizadosSubrubricasSemSubrubricasSemValorPrevisto() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("1", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Double[] valoresAnoPassado = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		Rubrica mae = new Rubrica(null, "mae", 1, CategoriaRubrica.DESPESA, valoresAnoPassado);
		mae.setValorPrevisto(0, 100);
		mae.setValorPrevisto(1, 100);
		Double valores = analiseComp.somaValoresRealizadosSubrubricas(mae, 02);
		assertEquals(0.0, valores, 0.0000000001);
	}

	/*
	 * Partição: Soma retorna resultado correto quando nao tem subricas, mas tem
	 * valor realizado? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresRealizadosSubrubricasSemSubrubricasComValorPrevisto() {
		ArrayList<String> correctAnswer = new ArrayList<String>(
				Arrays.asList("1", "mae", "200.0", "400.0", "-200.0", "-100.0%", ":("));
		Double[] valoresAnoPassado = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		Rubrica mae = new Rubrica(null, "mae", 1, CategoriaRubrica.DESPESA, valoresAnoPassado);
		mae.setValorRealizado(0, 200);
		mae.setValorRealizado(1, 200);
		Double valores = analiseComp.somaValoresRealizadosSubrubricas(mae, 01);
		assertEquals(200.0, valores, 0.0000000001);
	}

	/*
	 * Partição: Soma retorna resultado correto quando tem subricas e valor
	 * realizado? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresRealizadosSubrubricasComSubrubricasComValorPrevisto() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorRealizado(0, 200);
		filha.setValorRealizado(1, 200);
		Double valores = analiseComp.somaValoresRealizadosSubrubricas(mae, 01);
		assertEquals(200.0, valores, 0.0000000001);
	}

	/*
	 * Partição: Soma retorna resultado correto quando tem subricas e não tem valor
	 * realizado? Opções de resposta: Sim Não
	 */
	@Test
	public void somaValoresRealizadosSubrubricasComSubrubricassemValorPrevisto() {
		Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		Double valores = analiseComp.somaValoresRealizadosSubrubricas(mae, 01);
		assertEquals(0.0, valores, 0.0000000001);
	}
	// Domínio: método geraAvaliacao() do AgenteAnáliseComparativa, jutamente com
	// todas rúbricas e valores ja estimados da comparação

	// CATEGORIA DESPESA
	/*
	 * Partição: Cara negativa é correta para despesas com variação negativa? Opções
	 * de resposta: Sim Não
	 */
	@Test
	public void geraAvaliacaoDespesaVaricaoNegativa() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.DESPESA, -10.0);
		assertEquals(":(", resultado);
	}

	/*
	 * Partição: Cara positiva é correta para despesas com variação positiva? Opções
	 * de resposta: Sim Não
	 */
	@Test
	public void geraAvaliacaoDespesaVaricaoPositiva() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.DESPESA, 1000.0);
		assertEquals(":)", resultado);
	}
	/*
	 * Partição: Cara positiva é correta para despesas com boundary values? Opções
	 * de resposta: Sim Não
	 */

	// teste no valor limite para cara postiva!
	@Test
	public void geraAvaliacaoDespesaVaricaoPositivaaBoundaryValues() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.DESPESA, 0.0);
		assertEquals(":)", resultado);
	}

	// teste no valor limite para cara negativa!
	@Test
	public void geraAvaliacaoDespesaVaricaoNegativaBoundaryValues() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.DESPESA, -0.000000001);
		assertEquals(":(", resultado);
	}

	// CATEGORIA RECEITA
	/*
	 * Partição: Cara positiva é correta para receitas com variação negativa? Opções
	 * de resposta: Sim Não
	 */
	@Test
	public void geraAvaliacaoReceitaVaricaoNegativa() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.RECEITA, -10.0);
		assertEquals(":)", resultado);
	}

	/*
	 * Partição: Cara negativa é correta para receitas com variação positiva? Opções
	 * de resposta: Sim Não
	 */
	@Test
	public void geraAvaliacaoReceitaVaricaoPositiva() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.RECEITA, 1000.0);
		assertEquals(":(", resultado);
	}
	/*
	 * Partição: Cara negativa é correta para receitas com boundary values? Opções
	 * de resposta: Sim Não
	 */

	// teste no valor limite para cara negativa!
	@Test
	public void geraAvaliacaoReceitaVaricaoPositivaaBoundaryValues() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.RECEITA, 0.00000001);
		assertEquals(":(", resultado);
	}

	// teste no valor limite para cara positiva!
	@Test
	public void geraAvaliacaoReceitaVaricaoNegativaBoundaryValues() {
		String resultado = analiseComp.geraAvaliacao(CategoriaRubrica.RECEITA, -0.000000001);
		assertEquals(":)", resultado);
	}
	// ERRO/DUVIDA: QUANDO QUE VAI ROLAR CARINHA :/???, como n exitse outros tipos
	// de despesa na nossa cetagoriarubrica, ela iria retornar
	// erro direto por isso, to meio confusa dai como q isso funciona(ver com o
	// lucas essa daqui eu acho, ja q ele fez categoria rubrica)

	// Domínio: método calculaVariacao() do AgenteAnáliseComparativa, jutamente com
	// todas rúbricas e valores ja estimados da comparação
	@Test
	public void calculaVariacaoComValorPrevistoEComRealizado() {
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		Double resultado = analiseComp.calculaVariacao(filha, 1);
		assertEquals(-100.0, resultado, 0.000000001);
	}
	@Test
	public void calculaVariacaoComValorPrevistoESemRealizado() {
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		filha.setValorPrevisto(0, 100);
		Double resultado = analiseComp.calculaVariacao(filha, 01);
		assertEquals(0.0, resultado, 0.000000001);
	}
	@Test
	public void calculaVariacaoSemValorPrevistoEComRealizado() {
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		filha.setValorRealizado(1, 200);
		Double resultado = analiseComp.calculaVariacao(filha, 01);
		assertEquals(0.0, resultado, 0.000000001);
	}
	@Test
	public void calculaVariacaoSemValorPrevistoSemRealizado() {
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		Double resultado = analiseComp.calculaVariacao(filha, 01);
		assertEquals(0.0, resultado, 0.000000001);
	}
	

}
