package util;

public enum CategoriaMes {

	JANEIRO(1, "Janeiro"), FEVEREIRO(2, "Fevereiro"), MARCO(3, "Março"), ABRIL(4, "Abril"), MAIO(5, "Maio"), JUNHO(6, "Junho"),   
    JULHO(7, "Julho"), AGOSTO(8, "Agosto"), SETEMBRO(9, "Setembro"), OUTUBRO(10, "Outubro"), NOVEMBRO(11, "Novembro"), DEZEMBRO(12, "Dezembro");
	
    private int numMes;
    private String stringMes;
    
    CategoriaMes(int numMes, String stringMes){
       this.numMes = numMes;
       this.stringMes = stringMes;
    }
    
    public int toInt(){
       return this.numMes;
    }
    
    public String toString() {
    	return this.stringMes;
    }
}
