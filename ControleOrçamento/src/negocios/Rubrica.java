package negocios;
import java.util.*;

/**
 *  Classe que representa uma Rubrica de um Plano de Contas
 *
 */
public class Rubrica {
	
	private int codigo;
	private String nome;
	private Rubrica pai;
	private List<Rubrica> subRubricas;
	private Double[] valoresAnoPassado;
	private Double[] valoresPrevistos;
	private Double[] valoresRealizados;
	private CategoriaRubrica categoria;
	
	public Rubrica(Rubrica pai, String nome, int codigo, CategoriaRubrica categoria, Double[] valoresAnoPassado) {
		this.pai = pai;
		this.nome = nome;
		this.codigo = codigo;
		this.categoria = categoria;
		this.subRubricas = new ArrayList<>();
		this.valoresAnoPassado = valoresAnoPassado; 
		this.valoresPrevistos = new Double[12]; 
		this.valoresRealizados = new Double[12]; 
	}

	public Rubrica getPai() {
		return this.pai;
	}
	
	public List<Rubrica> getSubRubricas(){
		return this.subRubricas;
	
	}
	
	public void addSubRubrica(Rubrica subRubrica) {
		this.subRubricas.add(subRubrica);
	}

	public String toString() {
		String out = this.nome + "  " +  String.valueOf(this.codigo);
		for(int i = 0; i<12; i++) {
			out+="    " + String.valueOf(this.valoresAnoPassado[i]);
		}
		return out + '\n';
	}
}
