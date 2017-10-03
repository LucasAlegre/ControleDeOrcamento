package negocios;

import java.util.*;

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
	
	public void setDataCongelamento(Date date) {
		this.dataCongelamento = date;
	}
}
