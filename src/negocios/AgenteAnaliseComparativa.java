package negocios;

import java.util.ArrayList;

import util.CategoriaMes;

/**
 *  Classe respons�vel por gerenciar a gera��o de an�lise comparativa do
 *  planejando de or�amento e do que foi efetivamente realizada durante um
 *  determinado per�odo em uma empresa.
 * 
 */
public class AgenteAnaliseComparativa extends AgenteAbstract{

	public AgenteAnaliseComparativa(PlanoContas plano) {
		super(plano);
	}
	

	public void geraAnaliseComparativa(CategoriaMes mesInicial, CategoriaMes mesFinal) {
		
		int contadorRubrica = 1;
		
        
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
		
		GerenciadorArquivos gerenciadorArquivo = new GerenciadorArquivos();
		gerenciadorArquivo.geraArquivoAnaliseComparativa();
		
        for (Rubrica rubricaTeste : arrayTeste ) {
        		//HSSFRow novaLinha = sheet.createRow((short)contadorRubrica); 
        		//Rubrica rubrica = PlanoContas.getInstance().getRubricas().get(codigoRubrica);
        		gerenciadorArquivo.preencheLinhaAnaliseComparativa(geraValoresRubrica(rubricaTeste, mesInicial, mesFinal), contadorRubrica);
        		contadorRubrica += 1;
        }
		
		
		
		gerenciadorArquivo.finalizaArquivoAnaliseComparativa();
	}
	
	public ArrayList<String> geraValoresRubrica(Rubrica rubrica, CategoriaMes mesInicial, CategoriaMes mesFinal) {
		
		ArrayList<String> valores = new ArrayList<String>();
		try {
			valores.add(String.valueOf(rubrica.getCodigo()));
		}
		catch (NullPointerException ex) {
			valores.add("SEM CODIGO");
		}
		try {
			valores.add(String.valueOf(rubrica.getNome()));
		}
		catch (NullPointerException ex) {
			valores.add("SEM NOME");
		}
		Double somaValoresPrevistos = 0.0;
		Double somaValoresRealizados = 0.0;
		System.out.println("ackaknksankans");
		

		for (int mounthCounter = mesInicial.toInt(); mounthCounter <= mesFinal.toInt(); mounthCounter ++) {
			somaValoresPrevistos += somaValoresPrevistosSubrubricas(rubrica, mounthCounter);
			somaValoresRealizados += somaValoresRealizadosSubrubricas(rubrica, mounthCounter);
			System.out.println(somaValoresPrevistos);
		}
		valores.add(String.valueOf(somaValoresPrevistos));
		valores.add(String.valueOf(somaValoresRealizados));
		Double variacao = somaValoresPrevistos - somaValoresRealizados;
		valores.add(String.valueOf(variacao));
		valores.add(calculaPorcentagem(somaValoresPrevistos, variacao).toString() + "%");
		valores.add(String.valueOf(geraAvaliacao(rubrica.getCategoria(), variacao)));
		
	   
		return valores;
		
	}
	public static Double somaValoresPrevistosSubrubricas(Rubrica rubrica, int mes)  {
		if (rubrica.getSubRubricas().isEmpty() ) {
			try { 
				return rubrica.getValorPrevisto(mes);
			}
			catch (NullPointerException ex){
				return 0.0;
			}
		}
		
		Double count = 0.0;
		for (Rubrica subRubrica : rubrica.getSubRubricas()) {
			count = count +somaValoresPrevistosSubrubricas(subRubrica, mes);
		}
		return count;
	}
	public static Double somaValoresRealizadosSubrubricas(Rubrica rubrica, int mes)  {
		if (rubrica.getSubRubricas().isEmpty() ) {
			try { 
				return rubrica.getValorRealizado(mes);
			}
			catch (NullPointerException ex){
				return 0.0;
			}
		}
		
		Double count = 0.0;
		for (Rubrica subRubrica : rubrica.getSubRubricas()) {
			count = count +somaValoresRealizadosSubrubricas(subRubrica, mes);
		}
		return count;
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
	
	public static Double calculaPorcentagem (Double valorPrevisto, Double variacao) {
		//Double variacao = calculaVariacao(rubrica, mes);
		try {
			Double porcentagem = variacao * 100 / valorPrevisto;
			return porcentagem;
		}
		catch (NullPointerException exc) {
			return 0.0;
		}
	}
	
	
	
}
