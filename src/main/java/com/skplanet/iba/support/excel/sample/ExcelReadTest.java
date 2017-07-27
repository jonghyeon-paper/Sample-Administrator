package com.skplanet.iba.support.excel.sample;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.skplanet.iba.support.excel.ExcelDataMigration;
import com.skplanet.iba.support.excel.ObjectProperties;

public class ExcelReadTest {
	
	private final static String filePath = "C:\\development\\project-temp\\maven-Sample-Administrator\\src\\main\\java\\com\\skplanet\\iba\\support\\excel\\sample\\Book1.xlsx";
	
	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		// map test
		ExcelDataMigration excelDataMigration1 = new ExcelDataMigration(filePath);
		List<ObjectProperties> propertiesList1 = new ArrayList<>();
		propertiesList1.add(new ObjectProperties("column1", "컬럼1"));
		propertiesList1.add(new ObjectProperties("column2", "컬럼2"));
		propertiesList1.add(new ObjectProperties("column3", "컬럼3"));
		propertiesList1.add(new ObjectProperties("column4", "컬럼4"));
		propertiesList1.add(new ObjectProperties("column5", "컬럼5"));
		propertiesList1.add(new ObjectProperties("column6", "컬럼6"));
		propertiesList1.add(new ObjectProperties("column7", "컬럼7"));
		
		List<Map<String, Object>> resultList1 = (List<Map<String, Object>>) excelDataMigration1.convertMapList(propertiesList1);
		for (Map<String, Object> item : resultList1) {
			System.out.println(item.toString());
		}
		
		System.out.println("======================================");
		System.out.println("======================================");
		
		// object test
		ExcelDataMigration excelDataMigration2 = new ExcelDataMigration(filePath);
		List<ObjectProperties> propertiesList2 = new ArrayList<>();
		propertiesList2.add(new ObjectProperties("column1", "컬럼1"));
		propertiesList2.add(new ObjectProperties("column2", "컬럼2"));
		propertiesList2.add(new ObjectProperties("column3", "컬럼3"));
		propertiesList2.add(new ObjectProperties("column4", "컬럼4"));
		propertiesList2.add(new ObjectProperties("column5", "컬럼5"));
		propertiesList2.add(new ObjectProperties("column6", "컬럼6"));
		propertiesList2.add(new ObjectProperties("column7", "컬럼7"));
		
		List<ExcelObject> resultList2 = (List<ExcelObject>) excelDataMigration2.convertObjectList(ExcelObject.class, propertiesList2);
		for (ExcelObject item : resultList2) {
			System.out.println(item.toString());
		}
	}
}
