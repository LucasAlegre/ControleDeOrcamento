package negocios;

/**
 *  Classe pai dos Gerenciadores
 *
 */
public abstract class AgenteAbstract {
	
	private PlanoContas planoContas;
	
	public AgenteAbstract(PlanoContas plano) {
	
		this.planoContas = plano;
	}
	
	public PlanoContas getPlanoContas() {
		return this.planoContas;
	}
	
}