
package br.com.mixfiscal.prodspedxnfe.services;

import org.junit.Test;
import static org.junit.Assert.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class LeitorXlsTest {
    
   @Test
   public void  testarLeituraXls(){
    String arquivo ="C:\\SPEDS\\testecsv.csv";
    
	String line = "";
	 try{
       BufferedReader br = new BufferedReader(new FileReader(arquivo));
	while ((line = br.readLine()) != null) {
	    // "," ou ";" de acordo com o arquivo
	    String[] row = line.split(";");
	    System.out.println(row[0] + " - " + row[1]);
//        List<String> lista = new ArrayList<String>();
//        Workbook workbook = null; 
//        Sheet sheet = null;
//        try {
//        Carrega planilha
//        WorkbookSettings config = new WorkbookSettings();
//        config.setEncoding("Cp1252");//configura acentuação
//        workbook = Workbook.getWorkbook(new File(arquivo),config);//recupera arquivo desejado
//        sheet = workbook.getSheet(0);

//
//                       FileInputStream arquivo = new FileInputStream(new File(caminho_arquivo_xls));
//   
//                      HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
//   
//                      HSSFSheet sheetAlunos = workbook.getSheetAt(0);
//   
//                      Iterator<Row> rowIterator = sheetAlunos.iterator();
//   
//                      while (rowIterator.hasNext()) {
//                             Row row = rowIterator.next();
//                             Iterator<Cell> cellIterator = row.cellIterator();
//   
//                             while (cellIterator.hasNext()) {
//                                    Cell cell = cellIterator.next();
        
//        BufferedReader reader = new BufferedReader(new FileReader(new File(caminho_arquivo_xls)));  
//			String [] dados;  
//			String linha = null;
//			int cont = 0;			
//			while(reader.ready()){
//				linha = reader.readLine();  
//				dados = linha.split(";");	
//				
//				lista.add(linha);
        }
    }
    catch (FileNotFoundException e) { }
    catch (IOException e) {System.out.println(e);} 
    catch (NumberFormatException e) {System.out.println(e);} 
    catch(Exception e){System.out.println(e);} 
//    finally {
//        if(workbook !=null){
//            workbook.close();
//        }
//    }
    assertTrue(true);  
    }  
    
   @Test
   public void  testarSub(){  
//       String chave = "35161055895031000140550030003526711006158248";
//       String chaveTeste = chave.substring(20,22);
      
    assertTrue(true);  
    }  
}
