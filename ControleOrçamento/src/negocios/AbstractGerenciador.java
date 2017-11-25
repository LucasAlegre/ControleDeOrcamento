package negocios;

/**
 *  Classe pai dos Gerenciadores
 *
 */
public abstract class AbstractGerenciador {
	
	private String filename;
	private PlanoContas planoContas;
	
	public AbstractGerenciador() {
		this.filename = "";
		this.planoContas = PlanoContas.getInstance();
	}
	
	public String getFileName() {
		return this.filename;
	}
	
	public PlanoContas getPlanoContas() {
		return this.planoContas;
	}
	
}