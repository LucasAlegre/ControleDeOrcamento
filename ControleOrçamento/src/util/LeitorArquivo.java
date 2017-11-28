package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import negocios.AgenteCategoriaRubrica;
import negocios.Rubrica;
import drivers.DriverCSV;

public class LeitorArquivo {

	private String filename;
	 DriverCSV driver;
	
	public LeitorArquivo(String filename)throws FileNotFoundException{
		this.filename = filename;
		this.driver = new DriverCSV(this.filename);
	}

	public LinkedHashMap<Integer, Rubrica> lerOrcamentoInicial(){
	
		LinkedHashMap<Integer, Rubrica> map = new LinkedHashMap<Integer, Rubrica>();
			
		//Lista de pais para a estruturação da linhagem hierárquica
		List<Rubrica> pais = new ArrayList<>();
		Rubrica pai = null;
		int classPai = 0;
		try {
			String[] valorPassadoMensal;
			Double[] valoresPassadosMensal;
			
			File file = new File(this.filename);
			Scanner scan = new Scanner(file);
			
			while(driver.hasNext()) {
				Rubrica rubrica;
				// INTERPRETAÇÃO DA LINHA
				driver.proceed();
				

				if(driver.getNumOfLineFields() != 0) {
					
					String classe = driver.getFields()[0];
					String cod = driver.getFields()[1];
					String nome = driver.getFields()[2];
					System.out.println(classe + cod + nome);
					
					//Caso a rúbrica for classificável
					if(!classe.equals("")) {
						
						valoresPassadosMensal = new Double[driver.getNumOfLineFields()-3];
						
						for(int i = 3; i<driver.getNumOfLineFields(); i++) {
							valoresPassadosMensal[i-3] = Double.valueOf(driver.getFields()[i]);
						}
						//Instanciar o número de pontos de classificação da rúbrica,
						//ou seja, verificar em que nível a rúbrica está
						int pontos = classe.length() - classe.replace(".", "").length();
						
						//Caso o número de pontos seja zero, ou seja, a rubrica lida não possui pai
						//limpa-se os pais registrados e inicializa a rubrica com pai=null
						if(pontos == 0) {
							pais.clear();
							rubrica = new Rubrica(null, nome, (int)Integer.valueOf(cod), AgenteCategoriaRubrica.DESPESA, valoresPassadosMensal);
						
						//Caso a rubrica esteja classificada ao mesmo nível de sua antecessora, 
						//possui o mesmo pai que ela
						}else if(pontos == classPai) {
							rubrica = new Rubrica(pai.getPai(), nome, (int)Integer.valueOf(cod), AgenteCategoriaRubrica.DESPESA, valoresPassadosMensal);
							pai.getPai().addSubRubrica(rubrica);
							
							
						//Caso a rubrica esteja em um nível mais profundo que a outra, adicionamos a rubrica
						//anterior na lista de pais a adicionamos como pai da rubrica atual
						}else if(pontos > classPai){
							rubrica = new Rubrica(pai, nome, (int)Integer.valueOf(cod), AgenteCategoriaRubrica.DESPESA, valoresPassadosMensal);
							if(pai != null) {
								pais.add(pai);
								pai.addSubRubrica(rubrica);
							}
						
						//Caso tenhamos voltado um nível, deletamos a diferença de níveis da rubrica atual e da anterior,
						//assim como retomamos a posição da rubrica antes de intanciarmos ela
						}else {
							try{
								pai = pais.get(pontos - 1);
							}catch(IndexOutOfBoundsException e){
								pai =  null;
								pais.clear();
							}
							rubrica = new Rubrica(pai, nome, (int)Integer.valueOf(cod), AgenteCategoriaRubrica.DESPESA, valoresPassadosMensal);
							if(pai != null)pai.addSubRubrica(rubrica);
							for(int i = pontos; i < pais.size(); i++) {
								pais.remove(i);
							}
						}
						//set final
						pai = rubrica;
						classPai = pontos;
							
						//coloca no mapa
						map.put(Integer.valueOf(cod), rubrica);
					}
				}
				
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	public LinkedHashMap<Integer, Double> lerRealizadoMensal(){
		
		LinkedHashMap<Integer, Double> realizados = new LinkedHashMap<Integer, Double>();
		
		try {
			InputStream inp = new FileInputStream(this.filename);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			
			int row = 2;
			while(sheet.getRow(row) != null) {
				
				Row linha = sheet.getRow(row);
				int codigo = (int) linha.getCell(1).getNumericCellValue();
				double debito = linha.getCell(2).getNumericCellValue();
				double credito = linha.getCell(3).getNumericCellValue();
				
				realizados.put(codigo, credito - debito);
				
				row++;
			}
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(InvalidFormatException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		return realizados;
	}
	
	public static void main(String Args[]) {
		try {
			LeitorArquivo le = new LeitorArquivo("Modelo_Controle_Orcamentario_Completo.csv");
			le.lerOrcamentoInicial().toString();
		} catch (FileNotFoundException e) {
			System.out.println("Não funfou");
			e.printStackTrace();
		}
		
	}
	
}
