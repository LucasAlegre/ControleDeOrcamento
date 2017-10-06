package negocios;

import java.time.LocalDate;
import java.util.*;

import util.LeitorCSV;

/**
 *  Classe que representa um Plano de Contas de uma empresa.
 *
 */
public class PlanoContas {
	
	private Map<Integer, Rubrica> rubricas;
	private LocalDate dataCongelamento;
	
	public PlanoContas(){
		rubricas = new LinkedHashMap<Integer, Rubrica>();
	}
	
	private void setRubricas(Map<Integer, Rubrica> map) {
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
	public void setOrcamentoAnterior(String filename) {
		
		LeitorCSV ler = new LeitorCSV(filename);
		LinkedHashMap<Integer, Rubrica> map = ler.lerOrcamentoInicial();
		this.setRubricas(map);
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
			this.getPlanoBaseInfo(ru, tab+1);
		}
	}

}
