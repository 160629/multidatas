package com.example.demo.controller;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;
import com.example.demo.util.ExcelSheetCell;
import com.example.demo.util.ExcelSheetDataTable;
import com.example.demo.util.ExcelSheetLocation;
import com.example.demo.util.ExcelUtils;

import io.swagger.annotations.Api;
@Api(tags = "文件操作")
@RestController
public class FileController {
	private final static Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	FileService fileService;
	
	@PostMapping(value = "/changeExcel")
	public void changeExcel(MultipartFile file,String fileName, HttpServletResponse response) throws Exception {
		fileName=fileName==null?"wx":fileName;
		ExcelSheetDataTable excel = fileService.importUserExcel(file);
		XSSFWorkbook workbook = fileService.exportUserExcel(fileName, excel);
		response.setHeader("content-Type", "applicationnd.ms-excel");
		// 下载文件的名称，及编码格式
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8")
				+ ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));

		workbook.write(response.getOutputStream());
		response.flushBuffer();

	}  

}
