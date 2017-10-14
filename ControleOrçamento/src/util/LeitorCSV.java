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

import negocios.CategoriaRubrica;
import negocios.Rubrica;

public class LeitorCSV {

	private String filename;
	
	public LeitorCSV(String filename) {
		this.filename = filename; 
	}

	public LinkedHashMap<Integer, Rubrica> lerOrcamentoInicial(){
	
		LinkedHashMap<Integer, Rubrica> map = new LinkedHashMap<Integer, Rubrica>();
			
		//Lista de pais para a estruturação da linhagem hierárquica
		List<Rubrica> pais = new ArrayList<>();
		Rubrica buf = null;
		int pontoBuf = 0;
		try {
			String[] valorPassadoMensal;
			Double[] valoresPassadosMensal;
			
			File file = new File(this.filename);
			Scanner scan = new Scanner(file);
			
			while(scan.hasNext()) {
				Rubrica rubrica;
				
				// INTERPRETAÇÃO DA LINHA
				String line = scan.nextLine();
				
				int pos1 = line.indexOf(',');
				int pos2 = line.indexOf(',', pos1 + 1);
				int pos3 = line.indexOf(',', pos2 + 1); 
				
				String classe = line.substring(0, pos1);
				String cod = line.substring(pos1 + 1, pos2);
				String nome = line.substring(pos2 + 1, pos3);
				
				//Caso a rúbrica for classificável
				if(0 != pos1) {

					String valorPassado = line.substring(pos3 + 1);
					
					valorPassadoMensal = valorPassado.split(",");
					valoresPassadosMensal = new Double[valorPassadoMensal.length];
					
					for(int i = 0; i<valoresPassadosMensal.length; i++) {
						valoresPassadosMensal[i] = Double.valueOf(valorPassadoMensal[i]);
					}
					//Instanciar o número de pontos de classificação da rúbrica,
					//ou seja, verificar em que nível a rúbrica está
					int pontos = classe.length() - classe.replace(".", "").length();
					
					//Caso o número de pontos seja zero, ou seja, a rubrica lida não possui pai
					//limpa-se os pais registrados e inicializa a rubrica com pai=null
					if(pontos == 0) {
						pais.clear();
						rubrica = new Rubrica(null, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
					
					//Caso a rubrica esteja classificada ao mesmo nível de sua antecessora, 
					//possui o mesmo pai que ela
					}else if(pontos == pontoBuf) {
						rubrica = new Rubrica(buf.getPai(), nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
						buf.getPai().addSubRubrica(rubrica);
						
						
					//Caso a rubrica esteja em um nível mais profundo que a outra, adicionamos a rubrica
					//anterior na lista de pais a adicionamos como pai da rubrica atual
					}else if(pontos > pontoBuf){
						rubrica = new Rubrica(buf, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
						if(buf != null) {
							pais.add(buf);
							buf.addSubRubrica(rubrica);
						}
					
					//Caso tenhamos voltado um nível, deletamos a diferença de níveis da rubrica atual e da anterior,
					//assim como retomamos a posição da rubrica antes de intanciarmos ela
					}else {
						Rubrica pai;
						try{
							pai = pais.get(pontos - 1);
						}catch(IndexOutOfBoundsException e){
							pai =  null;
							pais.clear();
						}
						rubrica = new Rubrica(pai, nome, (int)Integer.valueOf(cod), CategoriaRubrica.DESPESA, valoresPassadosMensal);
						if(pai != null)pai.addSubRubrica(rubrica);
						for(int i = pontos; i < pais.size(); i++) {
							pais.remove(i);
						}
					}
					//set final
					buf = rubrica;
					pontoBuf = pontos;
						
					//coloca no mapa
					map.put(Integer.valueOf(cod), rubrica);
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
	
}
