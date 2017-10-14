package negocios;

import java.time.LocalDate;
import java.util.*;


/**
 *  Classe que representa um Plano de Contas de uma empresa.
 *
 */
public class PlanoContas {
	
	private Map<Integer, Rubrica> rubricas;
	private LocalDate dataCongelamento;
	
	public PlanoContas(){
		rubricas = new LinkedHashMap<Integer, Rubrica>();
		dataCongelamento = LocalDate.of(2020, 1, 11);
	}
	
	public void setRubricas(Map<Integer, Rubrica> map) {
		this.rubricas = map;
	}
	
	public Map<Integer, Rubrica> getRubricas(){
		return rubricas;
	}
	
	public void setDataCongelamento(LocalDate date) {
		this.dataCongelamento = date;
	}
	
	public LocalDate getDataCongelamento() {
		return this.dataCongelamento;
	}

	
	public void printPlanoBase() {
		
		Rubrica rubrica;
		
		for(Integer cod : rubricas.keySet()) {
			rubrica = rubricas.get(cod);
			if(rubrica.getPai() == null) {
				System.out.println(rubrica.toString());
				this.getPlanoBaseInfo(rubrica, 1);
			}
		}
	}
	
	private void getPlanoBaseInfo(Rubrica r, int tab) {
		
		for(Rubrica ru : r.getSubRubricas()) {
			for(int i = 0; i < tab; i++) {
				System.out.print("    ");
			}
			System.out.print(ru.toString() + '\n');
			this.getPlanoBaseInfo(ru, tab + 1);
		}
	}

}
