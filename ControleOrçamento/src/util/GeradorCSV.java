package util;

import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import negocios.PlanoContas;



public class GeradorCSV {
	private String filename;
	private PlanoContas planoContas;
	
	public GeradorCSV(PlanoContas planoContas, String filename) {
		this.filename = filename;
		this.planoContas = planoContas;
	}
	private String getFilename(){
		return filename;
	}
	

	//TODO: Gerar um arquivo por m�s. Lembrar que dessa maneira q foi feito, como � sempre o mesmo nome de arquivo, ele cria um,e qaundo manda o m�todo rodar dnv ele sobreescreve o arquivo de antes
	//� �timo pra testar pq n fica mil arquivos, mas qnd for pra finalizar o trabalho vamos ter de ter um controle de cada arquivo ter um nome diferente
	//uma ideia que eu tive � tipo, faz o janeiro funcionar, se ele funcionar s� mete um for fora de tudo dando append de um contador nos nomes dos arquivos, dai 
	//ele gera os 12 meses!
	public void geraArquivoPrevisoes() {
		
		 try {
	            String filename = this.getFilename();
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

	            HSSFRow rowhead = sheet.createRow((short)0);
	            rowhead.createCell(0).setCellValue("Descri��o da conta");
	            rowhead.createCell(1).setCellValue("C�digo");
	            rowhead.createCell(2).setCellValue("D�bito");
	            rowhead.createCell(3).setCellValue("Cr�dito");

	            HSSFRow row = sheet.createRow((short)1);
	            row.createCell(0).setCellValue("nome rubrica");
	            row.createCell(1).setCellValue("C�digo");
	            row.createCell(2).setCellValue("d�bito");
	            row.createCell(3).setCellValue("cr�dito");

	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            System.out.println("Arquivo gerado!");

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }    
	}
}
