package com.example.demo.util;

import org.apache.poi.ss.usermodel.Sheet;

@FunctionalInterface
public interface ProcessSheet {


    Boolean Process(Sheet sheet, ExcelSheetDataTable esDT);
}
