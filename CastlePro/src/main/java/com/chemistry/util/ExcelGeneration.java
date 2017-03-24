package com.chemistry.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class ExcelGeneration {

	HSSFWorkbook workbook = new HSSFWorkbook();	

	public HSSFWorkbook getWorkBook() {
		return workbook;
	}
	
	public HSSFWorkbook toExcelWorkbook1(HSSFWorkbook workbook, Map<String, Object[]> mapper) {		

		HSSFSheet sheet = workbook.createSheet("Volumetric");
		Set<String> keyset = mapper.keySet();
        int rownum = 0;
      
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = mapper.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
   
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
                else if(obj instanceof Integer)
                	cell.setCellValue((Integer)obj);
            	 }
              
            }
       
        return workbook;
	}

	public void  getCalculations(HSSFWorkbook workbook) {
		try {
		HSSFSheet sheet = workbook.getSheet("Volumetric");
		
		System.out.println("Got Sheet");
		System.out.println("calculated massess : ");
        double[] mass = new double[5];  
        Row row;        
        Cell c1, c2;
		double a, b;
        
        row = sheet.getRow(1);
        c1 = row.getCell(1);
		c2 = row.getCell(2);
        a= c1.getNumericCellValue();
		b = c2.getNumericCellValue();
		mass[0] = b - a;
		System.out.println("0 :"+mass[0]);

		row = sheet.getRow(2);
        c1 = row.getCell(1);
		c2 = row.getCell(2);
		a= c1.getNumericCellValue();
		b = c2.getNumericCellValue();
		mass[1] = b - a;
		System.out.println("1 :"+mass[1]);

		row = sheet.getRow(3);
        c1 = row.getCell(1);
		c2 = row.getCell(2);
		a= c1.getNumericCellValue();
		b = c2.getNumericCellValue();
		mass[2] = b - a;
		System.out.println("2 :"+mass[2]);

		row = sheet.getRow(4);
        c1 = row.getCell(1);
		c2 = row.getCell(2);
		a= c1.getNumericCellValue();
		b = c2.getNumericCellValue();
		mass[3] = b - a;
		System.out.println("3 :"+mass[3]);
		
		row = sheet.getRow(5);
        c1 = row.getCell(1);
		c2 = row.getCell(2);
		a= c1.getNumericCellValue();
		b = c2.getNumericCellValue();
		mass[4] = b - a;
		System.out.println("4 :"+mass[4]);


		
       for(int i = 0; i < 5; i++)
        	System.out.println(mass[i]);
		}
		catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
        
	}
	
	public double[] getMassOfWaterOfVolumetric(HSSFWorkbook workbook) {
		return getMassOfWater(workbook, "Volumetric");
	}

	public double[] getVolumesOfNaOHOfAcidBase(HSSFWorkbook workbook) {
		return getMassOfWater(workbook, "Acid base");
	}

	private double[] getMassOfWater(HSSFWorkbook workbook, String name) {
		try {
			HSSFSheet sheet = workbook.getSheet(name);
			return calculateMass(sheet);
		}
		catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	private double[] calculateMass(HSSFSheet sheet) {
		double[] mass = new double[5];
		for(int i =0; i < 5; i++) {
			mass[i] = getMassOFRow(sheet.getRow(i+1));
			System.out.println("Mass : "+i +" "+mass[i]);
		}
		return mass;
	}
	private double getMassOFRow(Row row) {
		Cell c1, c2;
		c1 = row.getCell(1);
		c2 = row.getCell(2);
		double a, b;
		a= c1.getNumericCellValue();
		b = c2.getNumericCellValue();
		return (b - a);
	}
	
	public HSSFWorkbook toExcelWorkbook2(HSSFWorkbook workbook, Map<String, Object[]> mapper) {		

		HSSFSheet sheet = workbook.createSheet("Acid base");
		Set<String> keyset = mapper.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = mapper.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
            }
        }
		 return workbook;
	}
}
