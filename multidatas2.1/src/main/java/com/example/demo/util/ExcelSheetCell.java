package com.example.demo.util;

public class ExcelSheetCell {
	private String cellValue;
	private String cellComment;
	private Short cellFormat;

	public ExcelSheetCell(String cellValue) {
		this(cellValue, null, (short) 0);

	}

	public ExcelSheetCell(String cellValue, String cellComment) {
		this(cellValue, cellComment, (short) 0);
	}

	public ExcelSheetCell(String cellValue, String cellComment, Short cellFormat) {
		this.cellValue = cellValue;
		this.cellComment = cellComment;
		this.cellFormat = cellFormat;
	}

	public String getCellValue() {
		return cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

	public String getCellComment() {
		return cellComment;
	}

	public void setCellComment(String cellComment) {
		this.cellComment = cellComment;
	}

	public Short getCellFormat() {
		return cellFormat;
	}

	public void setCellFormat(Short cellFormat) {
		this.cellFormat = cellFormat;
	}
}
