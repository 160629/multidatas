package com.example.demo.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.AsyncService;
import com.example.demo.service.FileService;
import com.example.demo.util.ExcelSheetDataTable;
import com.example.demo.util.ExcelSheetLocation;
import com.example.demo.util.ExcelUtils;

@Service
public class AsyncServiceImpl implements AsyncService{

	private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
	 
	 
    @Async("taskExecutor")
    @Override
    public void executeAsync() {
        logger.info("start executeAsync");
        try {
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }
}
