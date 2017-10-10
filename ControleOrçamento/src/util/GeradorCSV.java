package util;

import java.awt.Font;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import negocios.PlanoContas;
import negocios.Rubrica;

public class GeradorCSV {

	private PlanoContas planoContas;
	private static String[] months = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
			                          "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
	
	public GeradorCSV(PlanoContas planoContas) {
		this.planoContas = planoContas;
	}
	
	public void geraTemplateRealizadoMensal(int mes) {
		
		 try {
	           
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet("FirstSheet");  
	            sheet.setColumnWidth(0, 10000);

	            HSSFRow mesRelizado = sheet.createRow((short)0);
	            mesRelizado.createCell(0).setCellValue(months[mes]);
	            
	            
	            HSSFRow rowhead = sheet.createRow((short)1);
	            rowhead.createCell(0).setCellValue("Descrição da conta");
	            rowhead.createCell(1).setCellValue("Código");
	            rowhead.createCell(2).setCellValue("Débito");
	            rowhead.createCell(3).setCellValue("Crédito");
	            
	            int cont = 2;
	            for (Integer key : this.planoContas.getRubricas().keySet()) {
	            	
            		HSSFRow newRubricaRow = sheet.createRow((short)cont);
            
            		HSSFCell rubricaNameCell = newRubricaRow.createCell(0);
            		rubricaNameCell.setCellValue(this.planoContas.getRubricas().get(key).getNome());
           
        			newRubricaRow.createCell(1).setCellValue(key);
        			
		            cont++;
	            }

	      
	            FileOutputStream fileOut = new FileOutputStream(months[mes] + ".xls");
	            workbook.write(fileOut);
	            fileOut.close();
	            System.out.println("Arquivo gerado!");
	            workbook.close();

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }    
	}

	
	//TODO: Gerar um arquivo por m�s. Lembrar que dessa maneira q foi feito, como � sempre o mesmo nome de arquivo, ele cria um,e qaundo manda o m�todo rodar dnv ele sobreescreve o arquivo de antes
	//� �timo pra testar pq n fica mil arquivos, mas qnd for pra finalizar o trabalho vamos ter de ter um controle de cada arquivo ter um nome diferente
	//uma ideia que eu tive � tipo, faz o janeiro funcionar, se ele funcionar s� mete um for fora de tudo dando append de um contador nos nomes dos arquivos, dai 
	//ele gera os 12 meses!
	public void geraArquivoPrevisoes(String filename) {
		 
		 
		 try {
	           
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet("FirstSheet");
	            sheet.setColumnWidth(0, 10000);
	            
	            HSSFCellStyle cellStyle = workbook.createCellStyle();
	            cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	            
	            HSSFCellStyle cellHeaderStyle = workbook.createCellStyle();
	            cellHeaderStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
	            cellHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	            
	            HSSFRow rowhead = sheet.createRow((short)0);          
	            
	            HSSFCell newcell = rowhead.createCell(0);
	            newcell.setCellStyle(cellHeaderStyle);
	            newcell.setCellValue("Rubrica");
	            newcell = rowhead.createCell(1);
	            newcell.setCellStyle(cellHeaderStyle);
	            newcell.setCellValue("Codigo");
    
	            for (int cont = 0; cont < 12; cont ++) {
	            		newcell = rowhead.createCell(cont + 2);
	            		newcell.setCellStyle(cellHeaderStyle);
		            newcell.setCellValue(months[cont]);	
	            }
    
	            int cont = 0;
	            for (Integer key : this.planoContas.getRubricas().keySet()) {
	            		HSSFRow newRubricaRow = sheet.createRow((short)cont+1);
	            		HSSFCell rubricaNameCell = newRubricaRow.createCell(0);
	            		rubricaNameCell.setCellValue(this.planoContas.getRubricas().get(key).getNome());
	            		rubricaNameCell.setCellStyle(cellStyle);
            			newRubricaRow.createCell(1).setCellValue(key);
		            this.fillRubricasMonths(this.planoContas.getRubricas().get(key), newRubricaRow);
		            cont ++;
	            }
	            
	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            System.out.println("Arquivo gerado!");
	            workbook.close();
	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }    
	}
	
	public void fillRubricasMonths (Rubrica rubrica, HSSFRow rubricaRow) {
		for (int cont=0; cont < 12; cont ++) {
			try {
			//if (rubrica.getValorPrevisto(cont) != null) {
	            rubricaRow.createCell(cont + 2).setCellValue(rubrica.getValorPrevisto(cont));	
			}
			catch (NullPointerException exc) {
	            rubricaRow.createCell(cont + 2).setCellValue("-");	
			}
        }
		
	}
	
	
	
}
