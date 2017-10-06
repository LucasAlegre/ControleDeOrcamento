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
	

	//TODO: Gerar um arquivo por mês. Lembrar que dessa maneira q foi feito, como é sempre o mesmo nome de arquivo, ele cria um,e qaundo manda o método rodar dnv ele sobreescreve o arquivo de antes
	//é ótimo pra testar pq n fica mil arquivos, mas qnd for pra finalizar o trabalho vamos ter de ter um controle de cada arquivo ter um nome diferente
	//uma ideia que eu tive é tipo, faz o janeiro funcionar, se ele funcionar só mete um for fora de tudo dando append de um contador nos nomes dos arquivos, dai 
	//ele gera os 12 meses!
	public void geraArquivoPrevisoes() {
		
		 try {
	            String filename = this.getFilename();
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

	            HSSFRow rowhead = sheet.createRow((short)0);
	            rowhead.createCell(0).setCellValue("Descrição da conta");
	            rowhead.createCell(1).setCellValue("Código");
	            rowhead.createCell(2).setCellValue("Débito");
	            rowhead.createCell(3).setCellValue("Crédito");

	            HSSFRow row = sheet.createRow((short)1);
	            row.createCell(0).setCellValue("nome rubrica");
	            row.createCell(1).setCellValue("Código");
	            row.createCell(2).setCellValue("débito");
	            row.createCell(3).setCellValue("crédito");

	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            System.out.println("Arquivo gerado!");

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }    
	}
}
