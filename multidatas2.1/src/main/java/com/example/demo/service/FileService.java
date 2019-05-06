package com.example.demo.service;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.ExcelSheetDataTable;

public interface FileService {
	ExcelSheetDataTable importUserExcel(MultipartFile file) throws Exception ;
	XSSFWorkbook exportUserExcel(String fileName, ExcelSheetDataTable excel) throws Exception;
}
