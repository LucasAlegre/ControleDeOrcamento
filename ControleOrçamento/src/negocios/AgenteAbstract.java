package negocios;

/**
 *  Classe pai dos Gerenciadores
 *
 */
public abstract class AgenteAbstract {
	
	private String filename;
	private PlanoContas planoContas;
	
	public AgenteAbstract(PlanoContas plano) {
		this.filename = "";
		this.planoContas = plano;
	}
	
	public String getFileName() {
		return this.filename;
	}
	
	public PlanoContas getPlanoContas() {
		return this.planoContas;
	}
	
}