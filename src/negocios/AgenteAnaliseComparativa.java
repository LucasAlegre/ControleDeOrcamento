package negocios;

import java.util.ArrayList;
import java.util.HashMap;

import util.CategoriaMes;

/**
 *  Classe respons�vel por gerenciar a gera��o de an�lise comparativa do
 *  planejando de or�amento e do que foi efetivamente realizada durante um
 *  determinado per�odo em uma empresa.
 * 
 */
public class AgenteAnaliseComparativa extends AgenteAbstract{

	HashMap<Integer, Double> hardcodedResults = new HashMap<>(); 
	
	public AgenteAnaliseComparativa(PlanoContas plano) {
		super(plano);
	}
	

	public void geraAnaliseComparativa(CategoriaMes mesInicial, CategoriaMes mesFinal) {
		
		int contadorRubrica = 1;
		
        /*
        Rubrica mae = new Rubrica(null, "mae", 0, CategoriaRubrica.DESPESA, null);
		Rubrica filha = new Rubrica(null, "filha", 1, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha);
		filha.setValorPrevisto(0, 100);
		filha.setValorRealizado(0, 200);
		filha.setValorPrevisto(1, 100);
		filha.setValorRealizado(1, 200);
		
		Rubrica filha2 = new Rubrica(null, "filha2", 2, CategoriaRubrica.DESPESA, null);
		mae.addSubRubrica(filha2);
		filha2.setValorPrevisto(0, 200);
		filha2.setValorRealizado(0, 100);
		filha2.setValorPrevisto(1, 200);
		filha2.setValorRealizado(1, 100);
		
		Rubrica filhaMae = new Rubrica(null, "filhaMae", 3, CategoriaRubrica.DESPESA, null);
		Rubrica filha3 = new Rubrica(null, "filha3", 4, CategoriaRubrica.RECEITA, null);
		Rubrica filha4 = new Rubrica(null, "filha4", 5, CategoriaRubrica.RECEITA, null);
		filha3.setValorPrevisto(0, 700);
		filha4.setValorPrevisto(0, 500);
		
		filha3.setValorRealizado(0, 740);
		filha4.setValorRealizado(0, 500);
		
		filha3.setValorPrevisto(1, 700);
		filha4.setValorPrevisto(1, 500);
		
		filha3.setValorRealizado(1, 740);
		filha4.setValorRealizado(1, 500);
		
		filhaMae.addSubRubrica(filha4);
		filhaMae.addSubRubrica(filha3);
		mae.addSubRubrica(filhaMae);
		
		ArrayList<Rubrica> arrayTeste = new ArrayList<Rubrica>();
		arrayTeste.add(mae);
		arrayTeste.add(filha);
		arrayTeste.add(filha2);
		arrayTeste.add(filhaMae);
		arrayTeste.add(filha4);
		arrayTeste.add(filha3);
		*/
		
		GerenciadorArquivos gerenciadorArquivo = new GerenciadorArquivos();
		gerenciadorArquivo.geraArquivoAnaliseComparativa();
		
        for (int codigoRubrica : PlanoContas.getInstance().getRubricas().keySet()) {
        		Rubrica rubrica = PlanoContas.getInstance().getRubricas().get(codigoRubrica);
        		gerenciadorArquivo.preencheLinhaAnaliseComparativa(geraValoresRubrica(rubrica, mesInicial, mesFinal), contadorRubrica);
        		contadorRubrica += 1;
        }
		
		
		
		gerenciadorArquivo.finalizaArquivoAnaliseComparativa();
	}
	
	public static ArrayList<String> geraValoresRubrica(Rubrica rubrica, CategoriaMes mesInicial, CategoriaMes mesFinal) {
		
		ArrayList<String> valores = new ArrayList<String>();
		Double previstos = 0.0;
		Double realizados = 0.0;
		Double[] previstosERealizados = {0.0, 0.0};
		
		valores.add(String.valueOf(rubrica.getCodigo()));
		
		if (rubrica.getNome() == null) {
			valores.add("SEM NOME");
		}
		else {
			valores.add(String.valueOf(rubrica.getNome()));
		}
		if (PlanoContas.getInstance().getRubricasEspeciais().containsKey(rubrica.getCodigo())) {
			previstosERealizados = getValoresPrevistosRealizadosRubricaEspecial(rubrica.getCodigo(), mesInicial, mesFinal);
			
		}
		else {
			previstosERealizados =iteraESomaValoresRubricas(rubrica, mesInicial, mesFinal);
		}
		previstos = previstosERealizados[0];
		realizados = previstosERealizados[1];
		valores.add(previstos.toString());
		valores.add(realizados.toString());
		Double variacao = previstos - realizados;
		valores.add(String.valueOf(variacao));
		valores.add(calculaPorcentagem(previstos, variacao).toString() + "%");
		valores.add(String.valueOf(geraAvaliacao(rubrica.getCategoria(), variacao)));
	   
		return valores;
		
	}
	
	public static Double[] iteraESomaValoresRubricas(Rubrica rubrica, CategoriaMes mesInicial, CategoriaMes mesFinal) {
		
		Double somaValoresPrevistos = 0.0;
		Double somaValoresRealizados = 0.0;
		
		for (int mounthCounter = mesInicial.toInt(); mounthCounter <= mesFinal.toInt(); mounthCounter ++) {
			somaValoresPrevistos += rubrica.somaValoresPrevistosSubrubricas(mounthCounter);
			somaValoresRealizados += rubrica.somaValoresRealizadosSubrubricas(mounthCounter);
		}
		return new Double[] {somaValoresPrevistos, somaValoresRealizados};

	}
	
	
	public static Double[] getValoresPrevistosRealizadosRubricaEspecial(int RubricaCode, CategoriaMes mesInicial, CategoriaMes mesFinal) {
		String formula = PlanoContas.getInstance().getRubricasEspeciais().get(RubricaCode);
	
		String[] formulaArray = formula.split(" ");
		
		Double acumuladorPrevistos = 0.0;
		Double acumuladorRealizados = 0.0;
		
		//se o comprimento é um, é porque a fórmula é uma rubrica; sem operacao
		
		if (formulaArray.length == 1) {
			
			Rubrica rubrica =  PlanoContas.getInstance().getRubricas().get(Integer.valueOf(formulaArray[0]));
			return iteraESomaValoresRubricas(rubrica, mesInicial, mesFinal);
		}
		
		//se é dois, é porque é -x. sim, sempre.
		if (formulaArray.length == 2) {
			Rubrica rubrica =  PlanoContas.getInstance().getRubricas().get(Integer.valueOf(formulaArray[1]));
			Double[] result = iteraESomaValoresRubricas(rubrica, mesInicial, mesFinal);
			for (Double element : result) {
				element *= -1;
			}
			return result;
		}
		//se é maior. é pq é ou x + x + ... ou x - x - x... nunca terá - e + misturados.
		
		
		if (formulaArray[1] == "+") {
			
			for (int cont=0; cont < formulaArray.length; cont += 2) {
				Rubrica rubrica =  PlanoContas.getInstance().getRubricas().get(Integer.valueOf(formulaArray[cont]));
				Double[] result = iteraESomaValoresRubricas(rubrica, mesInicial, mesFinal);
				acumuladorPrevistos += result[0];
				acumuladorRealizados += result[1];
			}
			return new Double[] {acumuladorPrevistos, acumuladorRealizados};
		}
		else {
			
			for (int cont=0; cont < formulaArray.length; cont += 2) {
				Rubrica rubrica =  PlanoContas.getInstance().getRubricas().get(Integer.valueOf(formulaArray[cont]));
				Double[] result = iteraESomaValoresRubricas(rubrica, mesInicial, mesFinal);
				if (cont == 0) {
					acumuladorPrevistos = result[0];
					acumuladorRealizados = result[1];
				}
				else {
					acumuladorPrevistos -= result[0];
					acumuladorRealizados -= result[1];
				}
			}
			return new Double[] {acumuladorPrevistos, acumuladorRealizados};
			
		}
	}

	
	
	public static String geraAvaliacao (CategoriaRubrica categoriaRubrica, Double valorVariacao) {
		switch (categoriaRubrica) {
		case DESPESA:
			if (valorVariacao < 0) {
				return ":(";
			}
			else {
				return ":)";
			}
		case RECEITA:
			if (valorVariacao <= 0) {
				return ":)";
			}
			else {
				return ":(";
			} 
		}
		return ":/";
	}
	
	public static Double calculaVariacao (Rubrica rubrica, int mes) {
		Double variacao = 0.0;
		try {
				variacao = rubrica.getValorPrevisto(mes) - rubrica.getValorRealizado(mes);
				return variacao;	
		
		}
		catch (NullPointerException exc){
			return 0.0;
		}
		
		
	}
	
	private static Double calculaPorcentagem (Double valorPrevisto, Double variacao) {
		//Double variacao = calculaVariacao(rubrica, mes);
		try {
			if (valorPrevisto != 0.0) {
				Double porcentagem = variacao * 100 / valorPrevisto;
				return porcentagem;
			}
			else {
				return 0.0;
			}
			
		}
		catch (NullPointerException exc) {
			return 0.0;
		}
	}
	
	
	
}
