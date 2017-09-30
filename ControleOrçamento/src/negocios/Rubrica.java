package negocios;
import java.util.*;


public class Rubrica {
	
	private int codigo;
	private String nome;
	private List<Rubrica> subRubricas;
	private Double[] valoresAnoPassado;
	private Double[] valoresPrevistos;
	private Double[] valoresRealizados;
	private CategoriaRubrica categoria;
	
	public Rubrica(String nome, int codigo, Double[] valoresAnoPassado, CategoriaRubrica categoria) {
		this.nome = nome;
		this.codigo = codigo;
		this.valoresAnoPassado = valoresAnoPassado;
		this.categoria = categoria;
	}

}
