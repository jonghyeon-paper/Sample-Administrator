package com.skplanet.iba.web.file;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.skplanet.iba.support.enumdata.ResponseCode;
import com.skplanet.iba.support.excel.ExcelDataMigration;
import com.skplanet.iba.support.excel.ObjectProperties;
import com.skplanet.iba.support.excel.sample.ExcelObject;
import com.skplanet.iba.support.model.AjaxResponse;

/**
 * 파일 프로세스 샘플 컨트롤러입니다.
 * 샘플 파일이기에 @Deprecated 했습니다.
 * 추후에 삭제하시기 바랍니다.
 * 화면 페이지도 추후에 삭제하시기 바랍니다.
 * - create date 2017.07.26.
 * @author jonghyeon
 */
@Deprecated
@Controller
@RequestMapping("/file/sample")
public class FileSampleController {

	@GetMapping("/view.do")
	public String view() {
		return "/file/sample/view.part";
	}
	
	@RequestMapping(value = "/upload.do")
	@ResponseBody
	public AjaxResponse uploadFile(MultipartHttpServletRequest multipartRequest) throws IOException {
		
		// 파일 추출
		List<MultipartFile> fileList = new ArrayList<>();
		Iterator<String> iterator = multipartRequest.getFileNames();
		while (iterator.hasNext()) {
			fileList.add(multipartRequest.getFile(iterator.next()));
		}
		MultipartFile file = fileList.get(0);
		
		// 엑셀 데이터 추출
		ExcelDataMigration excelDataMigration = new ExcelDataMigration(file.getInputStream());
		
		List<ObjectProperties> objectPropertiesList = new ArrayList<>();
		objectPropertiesList.add(new ObjectProperties("column1", "컬럼1"));
		objectPropertiesList.add(new ObjectProperties("column2", "컬럼2"));
		objectPropertiesList.add(new ObjectProperties("column3", "컬럼3"));
		objectPropertiesList.add(new ObjectProperties("column4", "컬럼4"));
		objectPropertiesList.add(new ObjectProperties("column5", "컬럼5"));
		objectPropertiesList.add(new ObjectProperties("column6", "컬럼6"));
		objectPropertiesList.add(new ObjectProperties("column7", "컬럼7"));
		
		@SuppressWarnings("unchecked")
		List<ExcelObject> resultList = (List<ExcelObject>) excelDataMigration.convertObjectList(ExcelObject.class, objectPropertiesList);
		
		// 결과 응답
		AjaxResponse response = new AjaxResponse();
		response.setResponseCode(ResponseCode.SUCCESS);
		response.setTargetObject(resultList);
		return response;
	}
	
	@RequestMapping(value = "downloadXls.do")
	public String downloadFileXls(Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Content-Description", "JSP Generated Data");   
		response.setHeader("Content-Disposition", "attachment;filename=filefilefile.xls"); 
		
		model.addAttribute("data", dummyDataList());
		return "/file/sample/downloadXls";
	}
	
	@RequestMapping(value = "downloadXlsx.do")
	public void downloadFileXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<ObjectProperties> objectPropertiesList = new ArrayList<>();
		objectPropertiesList.add(new ObjectProperties("column1", "컬럼1"));
		objectPropertiesList.add(new ObjectProperties("column2", "컬럼2"));
		objectPropertiesList.add(new ObjectProperties("column3", "컬럼3"));
		objectPropertiesList.add(new ObjectProperties("column4", "컬럼4"));
		objectPropertiesList.add(new ObjectProperties("column5", "컬럼5"));
		objectPropertiesList.add(new ObjectProperties("column6", "컬럼6"));
		objectPropertiesList.add(new ObjectProperties("column7", "컬럼7"));
		
		ExcelDataMigration excelDataMigration = new ExcelDataMigration();
		XSSFWorkbook resultWorkbook = excelDataMigration.createXlsxFromObjectList(dummyDataList(), objectPropertiesList);
		
		String filename =  "filefilefile";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".xlsx\"");
		OutputStream os = response.getOutputStream();
		resultWorkbook.write(os);
	}
	
	private List<ExcelObject> dummyDataList() {
		List<ExcelObject> list = new ArrayList<>();
		list.add(new ExcelObject(1, "일", "하나",  "첫번쨰", "ㄱ", "1", "a"));
		list.add(new ExcelObject(2, "이",  "둘",  "두번쨰", "ㄴ", "2", "b"));
		list.add(new ExcelObject(3, "삼",  "셋",  "세번쨰", "ㄷ", "3", "c"));
		list.add(new ExcelObject(4, "사",  "넷",  "네번쨰", "ㄹ", "4", "d"));
		list.add(new ExcelObject(5, "오", "다섯", "다섯번쨰", "ㅁ", "5", "e"));
		list.add(new ExcelObject(6, "육", "여섯", "여섯번쨰", "ㅂ", "6", "f"));
		list.add(new ExcelObject(7, "칠", "일곱", "일곱번쨰", "ㅅ", "7", "g"));
		list.add(new ExcelObject(8, "팔", "여덟", "여덟번쨰", "ㅇ", "8", "h"));
		list.add(new ExcelObject(9, "구", "아홉", "아홉번쨰", "ㅈ", "9", "i"));
		
		return list;
	}
}
