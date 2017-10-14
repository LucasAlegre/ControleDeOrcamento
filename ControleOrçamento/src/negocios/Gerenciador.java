package negocios;

public abstract class Gerenciador {
	
	private String filename;
	private PlanoContas planoContas;
	
	public Gerenciador(String filename, PlanoContas planoContas) {
		this.filename = filename;
		this.planoContas = planoContas;
	}
	
	public String getFileName() {return this.filename;}
	public PlanoContas getPlanoContas() {return this.planoContas;}
	
}