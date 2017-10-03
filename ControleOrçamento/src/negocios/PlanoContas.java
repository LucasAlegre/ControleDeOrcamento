package negocios;

import java.util.*;

import util.LeitorCSV;

/**
 *  Classe que representa um Plano de Contas de uma empresa.
 *
 */
public class PlanoContas {
	
	private Map<Integer, Rubrica> rubricas;
	private Date dataCongelamento;
	
	public PlanoContas(){
		rubricas = new LinkedHashMap<Integer, Rubrica>();
	}
	
	public void setRubricas(Map<Integer, Rubrica> map) {
		this.rubricas = map;
	}
	
	public Map<Integer, Rubrica> getRubricas(){
		return rubricas;
	}
	
	public void setDataCongelamento(Date date) {
		this.dataCongelamento = date;
	}
	
	public void lePlanoBase(String filename) {
		
		LeitorCSV ler = new LeitorCSV(filename);
		LinkedHashMap<Integer, Rubrica> map = ler.lerPlanoBase();
		this.setRubricas(map);
	}
}
