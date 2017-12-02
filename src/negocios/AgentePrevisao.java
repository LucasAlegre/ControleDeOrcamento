package negocios;


/**
 *   Classe que gerencia a previs�o para o pr�ximo ano do 
 *   plano de contas.
 */
public class AgentePrevisao extends AgenteAbstract {

	public static final int PREVISAO_VALORFIXO = 1;
	public static final int PREVISAO_VALORPORCENTAGEM = 2;
	public static final int PREVISAO_VALORANOANTERIOR = 3;

	
	public AgentePrevisao(PlanoContas plano) {
		super(plano);
	}

	public void previsaoPorcentagem(int codigo, double porcentagem, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes, (super.getPlanoContas().getRubricas().get(codigo).getvalorAnoPassado(mes)*porcentagem));
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}

	public void previsaoValorFixo(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes,valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}
	
	public void previsaoManterAnoAnterior(int codigo, double valor, int mes) {
		
		try {
			super.getPlanoContas().getRubricas().get(codigo).setValorPrevisto(mes, valor);
		}
		catch (NullPointerException npe) {
			 System.out.println("O código digitado não existe!");
		}
	}
	
	
	public String toString(int codigo, int mes) {
		
		return super.getPlanoContas().getRubricas().get(codigo).getValorPrevisto(mes, codigo) + " mes: " + mes;
	}
	
	public void geraArquivoPrevisao() {
		GerenciadorArquivos orcamentoMensal = new GerenciadorArquivos();
		orcamentoMensal.geraArquivoPrevisoes(getPlanoContas(), "Previsoes.xls");
	}

}