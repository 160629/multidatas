package com.example.demo.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.ExcelSheetDataTable;
import com.example.demo.util.ExcelSheetLocation;
import com.example.demo.util.ExcelUtils;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public ExcelSheetDataTable importUserExcel(MultipartFile file) throws Exception {
		Map<String, Integer> map = new HashMap<>();
		InputStream inputStream = file.getInputStream();
		ExcelSheetDataTable excel = ExcelUtils.importExcel(inputStream);	
		return excel;
	}

	@Override
	public XSSFWorkbook exportUserExcel(String fileName, ExcelSheetDataTable excel) throws Exception {
		int rowStartIndex = excel.getRowStartIndex();
		int rowEndIndex = excel.getRowEndIndex();
		Map<ExcelSheetLocation, String> map = excel.getRowValueMap(rowStartIndex);
		int columnEndIndex = excel.getColumnEndIndex();
		String[] headers =new String[columnEndIndex+1];
		String[] fields =new String[columnEndIndex+1];
		for (int i = 0; i < columnEndIndex; i++) {
			String string = map.get(new ExcelSheetLocation(rowStartIndex, i));
			headers[i]=string;
			fields[i]=i+"";
		}
		headers[columnEndIndex]="性别";
		fields[columnEndIndex]=columnEndIndex+"";
		List<Map<String,String>> list = new ArrayList<>();
		for (int i = 1; i < rowEndIndex; i++) {
			Map<ExcelSheetLocation, String> rowValueMap = excel.getRowValueMap(i);
			ArrayList<ExcelSheetLocation> list2 = new ArrayList<>(rowValueMap.keySet());
			List<ExcelSheetLocation> collect = list2.stream()
					.sorted(Comparator.comparing(ExcelSheetLocation::getColumnIndex)).collect(Collectors.toList());
			Map<String,String> ve = new HashMap<>();
			boolean flag = true;
			for (int j = 0; j < columnEndIndex; j++) {
				String string = rowValueMap.get(collect.get(j));
			

				if(j==3) {
					String substring2 = string.substring(8, 10);
					if(Integer.valueOf(substring2)<90) {
						flag=false;
						continue;
					}
					String substring = string.substring(16, 17);
					if(Integer.valueOf(substring)%2==0) {
						ve.put(columnEndIndex+"", "女");
					}else {
						flag=false;
						ve.put(columnEndIndex+"", "男");
					}
				}
				ve.put(j+"", string);
			}
			if(flag) {
				list.add(ve);
			}
			
		}
		XSSFWorkbook workbook = ExcelUtils.exportExcel(headers, fields, list, fileName);
		return workbook;
	
	}

}
