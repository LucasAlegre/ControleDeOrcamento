package negocios;

/**
 * Classe responsável por controlar o que foi realmente realizado de gastos
 * na empresa.
 *
 */
public class GerenciadorRealizadoMensal implements Gerenciador {


	public GerenciadorRealizadoMensal() {

	}
	
	public void execute(String filename, PlanoContas planoContas) {
		
		//Se comunica com a UI de alguma forma pra ver a opção
		//Se opcao == gerar template:
	   	//    geraTemplateOrcamentoMensal(mes informado pela UI);
		//Se opcao == ler orcamento mensal;
		//    leRealizadoMensal()
		
	}

	public void geraTemplateOrcamentoMensal(PlanoContas planoContas, int mes) {
		//TODO: Chama uma classe que gera cvs maybe, percorre as rubricas do plano e imprimi no arquivo
	}
	
	
	public void leRealizadoMensal(PlanoContas planoContas, String filename) {
		//TODO: Chama metodo do planoContas que atualiza rubrica
	}
}
