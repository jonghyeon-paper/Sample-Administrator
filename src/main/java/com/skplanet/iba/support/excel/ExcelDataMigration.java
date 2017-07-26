package com.skplanet.iba.support.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataMigration {
	
	private InputStream inputStream;
	private Boolean titleRow = false;
	
	public ExcelDataMigration(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public ExcelDataMigration(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}
	
	public ExcelDataMigration(String path) throws FileNotFoundException {
		this(new File(path));
	}
	
	public Boolean isTitleRow() {
		return titleRow;
	}

	public void setTitleRow(Boolean titleRow) {
		this.titleRow = titleRow;
	}
	
	public List<? extends Object> convertObjectList(Class<?> objectClass, List<ObjectProperties> objectPropertiesList)
			throws IOException, EncryptedDocumentException, InvalidFormatException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		List<Object> resultList = new ArrayList<>();
		List<ObjectProperties> sortedObjectPropertiesList = new ArrayList<>();
		
		// Step1. sheet check
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			
			// Step2. row check
			int rows = sheet.getPhysicalNumberOfRows();
			for (int j = 0; j < rows; j++) {
				XSSFRow row = sheet.getRow(j);
				
				// Step3. cell check
				Object newInstance = objectClass.newInstance();
				int cells = row.getPhysicalNumberOfCells();
				for (int k = 0; k < cells; k++) {
					XSSFCell cell = row.getCell(k);
					
					if (j == 0 && titleRow ) {
						// 컬럼 설정
						String excelColumnName = cell.getStringCellValue();
						for (ObjectProperties item : objectPropertiesList) {
							String propertyColumnName = item.getColumnName() == null ? item.getAttributeName() : item.getColumnName();
							if (!excelColumnName.equals(propertyColumnName)) {
								continue;
							}
							sortedObjectPropertiesList.add(item);
							break;
						}
					} else {
						// 데이터 설정
						Object value = null;
						switch (cell.getCellTypeEnum()) {
						case ERROR :
							value = "ERROR!";
							break;
						case BOOLEAN :
							value = cell.getBooleanCellValue() ? "true" : "false";
							break;
						case BLANK :
							value = "";
							break;
						case NUMERIC :
							value = String.valueOf(cell.getNumericCellValue());
							break;
						case STRING :
						default :
							value = cell.getStringCellValue();
							break;
						}
						
						ObjectProperties columnProperty = titleRow ? sortedObjectPropertiesList.get(k) : objectPropertiesList.get(k);
						setObjectValue(newInstance, columnProperty, value);
					}
				}
				
				if (j == 0 && titleRow) {
					continue;
				}
				resultList.add(newInstance);
			}
		}
		return resultList;
	}
	
	private void setObjectValue(Object newInstance, ObjectProperties property, Object value)
			throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (property.getAttributeType() == null) {
			Field field = newInstance.getClass().getDeclaredField(property.getAttributeName());
			property.setAttributeType(field.getType());
		}
		
		Pattern pattern = Pattern.compile("^[\\w]{1}");
		Matcher matcher = null;
		
		matcher = pattern.matcher(property.getAttributeName());
		if (matcher.find()) {
			String setMathodName = matcher.replaceAll("set" + matcher.group().toUpperCase());
			Method setMethod = newInstance.getClass().getDeclaredMethod(setMathodName, new Class[] {property.getAttributeType()});
			if (Number.class.isAssignableFrom(property.getAttributeType())) {
				Double doubleValue = Double.valueOf(String.valueOf(value));
				Integer finalValue = doubleValue.intValue();
				setMethod.invoke(newInstance, finalValue);
			}
			if (String.class.isAssignableFrom(property.getAttributeType())) {
				String finalValue = String.valueOf(value);
				setMethod.invoke(newInstance, finalValue);
			}
		}
	}
	
	public List<? extends Map<String, Object>> convertMapList(List<ObjectProperties> objectPropertiesList)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		List<ObjectProperties> sortedObjectPropertiesList = new ArrayList<>();
		
		// Step1. sheet check
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			
			// Step2. row check
			int rows = sheet.getPhysicalNumberOfRows();
			for (int j = 0; j < rows; j++) {
				XSSFRow row = sheet.getRow(j);
				
				// Step3. cell check
				Map<String, Object> map = new HashMap<>();
				int cells = row.getPhysicalNumberOfCells();
				for (int k = 0; k < cells; k++) {
					XSSFCell cell = row.getCell(k);
					
					if (j == 0 && titleRow) {
						// 컬럼 설정
						String excelColumnName = cell.getStringCellValue();
						for (ObjectProperties item : objectPropertiesList) {
							String propertyColumnName = item.getColumnName() == null ? item.getAttributeName() : item.getColumnName();
							if (!excelColumnName.equals(propertyColumnName)) {
								continue;
							}
							sortedObjectPropertiesList.add(item);
							break;
						}
					} else {
						// 데이터 설정
						Object value = null;
						switch (cell.getCellTypeEnum()) {
						case ERROR :
							value = "ERROR!";
							break;
						case BOOLEAN :
							value = cell.getBooleanCellValue() ? "true" : "false";
							break;
						case BLANK :
							value = "";
							break;
						case NUMERIC :
							value = String.valueOf(cell.getNumericCellValue());
							break;
						case STRING :
						default :
							value = cell.getStringCellValue();
							break;
						}
						
						ObjectProperties columnProperty = titleRow ? sortedObjectPropertiesList.get(k) : objectPropertiesList.get(k);
						map.put(columnProperty.getAttributeName(), value);
					}
				}
				
				if (j == 0 && titleRow) {
					continue;
				}
				resultList.add(map);
			}
		}
		return resultList;
	}
}
