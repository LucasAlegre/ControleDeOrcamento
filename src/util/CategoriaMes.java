package util;

public enum CategoriaMes {

	JANEIRO(0, "Janeiro"), FEVEREIRO(1, "Fevereiro"), MARCO(2, "Março"), ABRIL(3, "Abril"), MAIO(4, "Maio"), JUNHO(5, "Junho"),   
    JULHO(6, "Julho"), AGOSTO(7, "Agosto"), SETEMBRO(8, "Setembro"), OUTUBRO(9, "Outubro"), NOVEMBRO(10, "Novembro"), DEZEMBRO(11, "Dezembro");
	
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
