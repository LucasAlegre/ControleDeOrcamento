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
	
	static PlanoContas instance;
	
	private Map<Integer, String> rubricasEspeciais;
	
	public static PlanoContas getInstance() {
		
		if(instance == null){
			instance = new PlanoContas();
			instance.inicializaMapRubricasEspeciais();
		}		
		return(instance);	
	}
	
	private void inicializaMapRubricasEspeciais() {
		rubricasEspeciais = new HashMap<Integer, String>();
		rubricasEspeciais.put(103, "103"); 
		rubricasEspeciais.put(2396, "2396"); 
		rubricasEspeciais.put(1, "103 - 2396"); 
		rubricasEspeciais.put(110, "2 + 120"); 
		rubricasEspeciais.put(2, "2"); 
		rubricasEspeciais.put(120, "120"); 
		rubricasEspeciais.put(3, "1 - 110"); 
		rubricasEspeciais.put(2398, "133 + 156 + 312");
		rubricasEspeciais.put(133, "133 + 142");
		rubricasEspeciais.put(4, "133"); 
		rubricasEspeciais.put(142, "142"); 
		rubricasEspeciais.put(156, "156 + 2401"); 
		rubricasEspeciais.put(5, "156"); 
		rubricasEspeciais.put(2401, "156"); 
		rubricasEspeciais.put(312, "312 + 338"); 
		rubricasEspeciais.put(6, "312");
		rubricasEspeciais.put(338, "338"); 
		rubricasEspeciais.put(7, "103 - 2398"); 
		rubricasEspeciais.put(187, "187 - 193");  
		rubricasEspeciais.put(10, "187"); 
		rubricasEspeciais.put(193, "193"); 
		rubricasEspeciais.put(11, "9 - 187"); 
		rubricasEspeciais.put(197, "352 + 201");  
		rubricasEspeciais.put(352, "352");  
		rubricasEspeciais.put(201, "201"); 
		rubricasEspeciais.put(12, "11 - 197"); 
		rubricasEspeciais.put(13, "12 - 914 - 205"); 
		rubricasEspeciais.put(14, "11");  
		rubricasEspeciais.put(15, "172");  
		rubricasEspeciais.put(16, "184"); 
		rubricasEspeciais.put(17, "- 187"); 
		rubricasEspeciais.put(18, "13 + 15 + 16 + 17");
		rubricasEspeciais.put(19, "110");
		rubricasEspeciais.put(20, "110");		
	}
	
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

	public Map<Integer, String> getRubricasEspeciais() {
		return this.rubricasEspeciais;
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
