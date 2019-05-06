package com.example.demo.util;

import com.example.demo.enumtype.CommonEnum;
import com.example.demo.enumtype.CommonEnum.SexEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtils {

	// region 导入excel

	/**
	 * 获取Excel文件格式
	 *
	 * @param filePath excel路径地址
	 * @return
	 */
	public static CommonEnum.ExcelFileTypeEnum GetExcelFileType(String filePath) throws IOException {
		InputStream inputStream = null;
		Workbook wb = null;
		CommonEnum.ExcelFileTypeEnum exceltype = CommonEnum.ExcelFileTypeEnum.ExcelUnKnown;
		try {
			inputStream = new FileInputStream(filePath);

			// 这种方式更准确
			try {
				// 2003版本的excel，用.xls结尾
				wb = new HSSFWorkbook(inputStream);// 得到工作簿
				exceltype = CommonEnum.ExcelFileTypeEnum.Excel97_2003;

			} catch (Exception ex) {
				// ex.printStackTrace();
				try {
					// 这里需要重新获取流对象，因为前面的异常导致了流的关闭—————————————————————————————加了这一行
					inputStream = new FileInputStream(filePath);
					// 2007版本的excel，用.xlsx结尾

					wb = new XSSFWorkbook(inputStream);// 得到工作簿
					exceltype = CommonEnum.ExcelFileTypeEnum.Excel2007Plus;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					exceltype = CommonEnum.ExcelFileTypeEnum.ExcelUnKnown;
					e.printStackTrace();
				}
			}
			//
		} catch (Exception ex) {
			// ex.printStackTrace();
			exceltype = CommonEnum.ExcelFileTypeEnum.ExcelUnKnown;
		} finally {
			if (wb != null) {
				wb = null;
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return exceltype;
	}

	/**
	 * 导入Excel某sheet页内容
	 *
	 * @param inputStream excel输入流
	 * @return
	 */
	public static ExcelSheetDataTable importExcel(InputStream inputStream) throws Exception {
		return importExcel(inputStream, 0, 0, true);
	}

	/**
	 * 导入Excel某sheet页内容
	 *
	 * @param filePath excel文件路径
	 * @return
	 */
	public static ExcelSheetDataTable importExcel(String filePath) throws Exception {
		return importExcel(filePath, 0, 0, true);
	}

	/**
	 * 读取Excel文件
	 *
	 * @param inputStream   excel路径地址
	 * @param sheetIndex    获取指定的sheet页
	 * @param startRowIndex 从指定行数开始读取
	 * @param nullRowEnd    遇到空行结束读取
	 * @return
	 */
	public static ExcelSheetDataTable importExcel(InputStream inputStream, int sheetIndex, int startRowIndex,
			boolean nullRowEnd) throws Exception {

		ExcelSheetDataTable exlDT = new ExcelSheetDataTable(sheetIndex);
		if (inputStream == null) {
			return null;
		}
		Workbook wb = null;

		try {
			wb = WorkbookFactory.create(inputStream);
			// 获取公式计算器;
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

			// 获取指定的sheet;
			Sheet sheet = wb.getSheetAt(sheetIndex);
			// 行数;
			int rowNum = sheet.getPhysicalNumberOfRows();
			// 取前5行中的最大列数;
			int max = 5;
			int colNum = 0;
			for (int i = 0; i < rowNum && i < max; i++) {
				Row r = sheet.getRow(i);
				if (r != null) {
					colNum = Math.max(r.getPhysicalNumberOfCells(), colNum);
				}
			}
			// 循环遍历excel表格;
			boolean isNullRow = false;
			for (int i = startRowIndex; !isNullRow && i < rowNum; i++) {
				int nullRow = 0;
				String[] obj = new String[colNum];
				for (int j = 0; j < colNum; j++) {
					// 获取单元格值;
					obj[j] = getCellValue(evaluator, sheet, i, j);
					if (null == obj[j] || (nullRowEnd && obj[j].trim().isEmpty())) {
						nullRow++;
					}
					if (nullRowEnd && nullRow >= colNum) {
						isNullRow = true;
					}
					if (i == 0) {
						Comment comment = sheet.getCellComment(i, j);
						if (null != comment && StringUtils.isNotEmpty(comment.getString().toString())) {
							exlDT.setOnlyCellComment(i, j, comment.getString().toString());
						}
					}

				}
				if (!isNullRow) {
					//
					for (int j0 = 0; j0 < obj.length; j0++) {
						exlDT.setOnlyCellValue(i, j0, obj[j0]);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			inputStream.close();
		}
		return exlDT;
	}

	public static ExcelSheetDataTable importExcel(String filePath, int sheetIndex, int startRowIndex,
			boolean nullRowEnd) throws Exception {

		InputStream inputStream = new FileInputStream(filePath);
		return importExcel(inputStream, sheetIndex, startRowIndex, nullRowEnd);

	}

	/**
	 * 获取包含合并区域内的cell的值;
	 *
	 * @param        evaluator, 没有可以为null;
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getCellValue(FormulaEvaluator evaluator, Sheet sheet, int row, int column) throws Exception {
		Cell cell = getCell(sheet, row, column);
		try {
			return getCellValue(cell, evaluator);
		} catch (Exception ex) {
			throw new Exception("第" + row + "行，第" + column + "列的数据非法，请检查！");
		}
	}

	/**
	 * 获取指定cell的值;
	 *
	 * @param cell
	 * @param evaluator
	 * @return
	 */
	public static String getCellValue(Cell cell, FormulaEvaluator evaluator) {

		if (cell == null) {
			return null;
		}
		int cellType = cell.getCellType();

		// 当cell表明是公式时,单独处理;
		if (Cell.CELL_TYPE_FORMULA == cellType && evaluator != null) {
			CellValue cv = evaluator.evaluate(cell);
			return getCellValue(cv, cell);
		} else {
			return getCellValue(cell);
		}
	}

	/**
	 * 获取指定的行列的cell;当行列在合并区域内时,返回该合并去区域的第一个cell;
	 *
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static Cell getCell(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return fCell;
				}
			}
		}

		Cell cell = sheet.getRow(row) == null ? null : sheet.getRow(row).getCell(column);
		return cell;
	}

	private static String getCellValue(CellValue cellValue, Cell cell) {
		String cellValueString = null;
		switch (cellValue.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			// System.out.print("String :");
			cellValueString = cellValue.getStringValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValueString = String.valueOf(cellValue.getBooleanValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// System.out.print("NUMERIC:");
			if (cell != null && HSSFDateUtil.isCellDateFormatted(cell)) {

				Date date = cell.getDateCellValue();
				cellValueString = DateUtil.formatString(date, DateUtil.DATE_FULL_DATE);
			} else {

				DecimalFormat df = new DecimalFormat("0");
				cellValueString = df.format(cell.getNumericCellValue());
			}

			break;
		case Cell.CELL_TYPE_FORMULA:
			// System.out.print("FORMULA:");
			break;
		default:
			break;
		}

		return cellValueString;
	}

	private static String getCellValue(Cell cell) {
		String cellValueString = null;
		if (cell == null) {
			return null;

		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			// System.out.print("String :");
			cellValueString = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValueString = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// System.out.print("NUMERIC:");
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				cellValueString = DateUtil.formatString(date, DateUtil.DATE_FULL_DATE);
			} else {

				DecimalFormat df = new DecimalFormat("0");
				cellValueString = df.format(cell.getNumericCellValue());
			}

			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				cellValueString = String.valueOf(cell.getRichStringCellValue().getString());
			} catch (IllegalStateException e) {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					cellValueString = DateUtil.formatString(date, DateUtil.DATE_FULL_DATE);
				} else {

					DecimalFormat df = new DecimalFormat("0");
					cellValueString = df.format(cell.getNumericCellValue());
				}
			}
			break;
		// 错误
		case Cell.CELL_TYPE_ERROR:
			cellValueString = "CELL_TYPE_ERROR";
			break;
		default:
			break;
		}
		return cellValueString;

	}

	/**
	 * 获取值;
	 *
	 * @param obj
	 * @return
	 */
	public String getCellValueOther(Object obj) {

		String cellValue = null;

		CellValue cv = null;
		Cell cell = null;

		if (obj instanceof CellValue) {
			cv = (CellValue) obj;
		} else if (obj instanceof Cell) {
			cell = (Cell) obj;
		} else {
			return cellValue;
		}

		int cellType = (cv != null) ? cv.getCellType() : cell.getCellType();
		switch (cellType) {
		// 文本
		case Cell.CELL_TYPE_STRING:
			cellValue = (cv != null) ? cv.getStringValue() : cell.getStringCellValue();
			break;
		// 布尔型
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = (cv != null) ? String.valueOf(cv.getBooleanValue())
					: String.valueOf(cell.getBooleanCellValue());
			break;
		// 空白
		case Cell.CELL_TYPE_BLANK:
			cellValue = (cv != null) ? cv.getStringValue() : cell.getStringCellValue();
			break;
		// 公式
		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		// 错误
		case Cell.CELL_TYPE_ERROR:
			cellValue = "error";
			break;
		// 数字、日期
		case Cell.CELL_TYPE_NUMERIC:
			// 日期型
			if (cell != null && HSSFDateUtil.isCellDateFormatted(cell)) {
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				cellValue = DateUtil.formatString(date, DateUtil.DATE_CUT_DAY);
			}
			// 数字型
			else {
				// 这种方法对于自动加".0"的数字可直接解决
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(10);
				cellValue = (cv != null) ? String.valueOf(cv.getNumberValue())
						: String.valueOf(nf.format(cell.getNumericCellValue()));
				// 但如果是科学计数法的数字就转换成了带逗号的，例如：12345678912345的科学计数法是1.23457E+13，经过这个格式化后就变成了字符串“12,345,678,912,345”，这也并不是想要的结果，所以要将逗号去掉
				if (cellValue.indexOf(",") >= 0) {
					cellValue = cellValue.replace(",", "");
				}
			}
			break;
		default:
			cellValue = null;
		}
		return cellValue.trim();
	}

	// endregion

	// region 导出excel

	/**
	 * 导出数据到原有Excel文件
	 *
	 * @param esDT
	 * @param originFilePath
	 * @param sheetIndex
	 * @return
	 */
	public static Workbook ExportOnlyValueToOriginExcel(ExcelSheetDataTable esDT, String originFilePath, int sheetIndex)
			throws Exception {
		Workbook wb = null;
		boolean success = false;
		InputStream inputStream = null;
		try {

			inputStream = new FileInputStream(originFilePath);
			wb = WorkbookFactory.create(inputStream);

			// 获取指定的sheet;
			Sheet sheet = wb.getSheetAt(sheetIndex);
			success = setCellValueToExcel(esDT, sheet, null);
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}
		return wb;
	}

	/**
	 * 设置批注到原有Excel文件（如果输入参数esDT某批注为空，则清除对应单元格批注）
	 *
	 * @param esDT
	 * @param originFilePath
	 * @param sheetIndex
	 * @return
	 */
	public static Workbook ExportOnlyCommentToOriginExcel(ExcelSheetDataTable esDT, String originFilePath,
			int sheetIndex) throws Exception {

		InputStream inputStream = new FileInputStream(originFilePath);
		return ExportOnlyCommentToOriginExcel(esDT, inputStream, sheetIndex);
	}

	public static Workbook ExportOnlyCommentToOriginExcel(ExcelSheetDataTable esDT, InputStream inputStream,
			int sheetIndex) throws Exception {
		Workbook wb = null;
		boolean success = false;
		if (inputStream == null) {
			throw new Exception("inputStream 必须非空！");
		}

		try {

			wb = WorkbookFactory.create(inputStream);

			// 获取指定的sheet;
			Sheet sheet = wb.getSheetAt(sheetIndex);
			success = setCellCommentToExcel(esDT, sheet);
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}
		return wb;
	}

	/**
	 * 导出数据到新Excel文件
	 *
	 * @param esDT
	 * @param sheetIndex
	 * @param excelType  Excel文件类型 为 97-2003 和2007+
	 * @return
	 */
	public static Workbook ExportOnlyValueToNewExcel(ExcelSheetDataTable esDT, int sheetIndex,
			CommonEnum.ExcelFileTypeEnum excelType) throws Exception {
		if (excelType == CommonEnum.ExcelFileTypeEnum.ExcelUnKnown) {
			return null;
		}
		Workbook wb = null;
		boolean success = false;
		try {
			if (excelType == CommonEnum.ExcelFileTypeEnum.Excel97_2003) {
				wb = new HSSFWorkbook();
			} else {
				wb = new XSSFWorkbook();
			}

			Sheet sheet = wb.createSheet();
			// 获取指定的sheet;
			success = setCellValueToExcel(esDT, sheet, null);

		} catch (Exception ex) {

			ex.printStackTrace();
		} finally {

		}
		return wb;
	}

	public boolean setCellValueToExcel(ExcelSheetDataTable esDT, Sheet sheet) throws Exception {
		return setCellValueToExcel(esDT, sheet, null);
	}

	public static boolean setCellCommentToExcel(ExcelSheetDataTable esDT, Sheet sheet) throws Exception {
		return setCellCommentToExcel(esDT, sheet, null);
	}

	public boolean setCellToExcel(ExcelSheetDataTable esDT, Sheet sheet) throws Exception {
		return setCellToExcel(esDT, sheet, null);
	}

	public static boolean setCellCommentToExcel(ExcelSheetDataTable esDT, Sheet sheet, ProcessSheet lastProcess)
			throws Exception {
		if (esDT == null) {
			throw new Exception("ExcelSheetDataTable esDT非null");
		}
		if (sheet == null) {
			throw new Exception("sheet非null");

		}
		boolean isXSSF = false;
		try {
			Workbook wb = sheet.getWorkbook();
			if (wb.getClass().getTypeName().contains("XSSFWorkbook")) {
				isXSSF = true;
			}

			CellStyle csComment = wb.createCellStyle();

			csComment.setFillForegroundColor(HSSFColor.RED.index);
			csComment.setFillPattern(CellStyle.SOLID_FOREGROUND);
			CellStyle csUnComment = wb.createCellStyle();
			csUnComment.setFillPattern(CellStyle.NO_FILL);
			// 创建绘图对象
			Drawing p = sheet.createDrawingPatriarch();
			ClientAnchor ca = p.createAnchor(0, 0, 0, 0, 3, 3, 5, 6);

			List<Integer> rowIndexList = esDT.getRowIndexList();
			int rowIndexListLength = rowIndexList.size();
			for (int i = 0; i < rowIndexListLength; i++) {
				int rowindex = rowIndexList.get(i).intValue();
				Row row = sheet.getRow(rowindex);
				if (row == null) {
					row = sheet.createRow(rowindex);
				}
				Map<ExcelSheetLocation, String> rowMap = esDT.getRowValueMap(rowindex);
				List<Integer> curColumnIndexList = rowMap.keySet().stream().map(s -> s.getColumnIndex())
						.sorted(Comparator.comparing(num -> num)).collect(Collectors.toList());
				int rowMapLength = curColumnIndexList.size();
				for (int j = 0; j < rowMapLength; j++) {
					int columnIndex = curColumnIndexList.get(j);
					Cell cell = row.getCell(columnIndex);
					if (cell == null) {
						cell = row.createCell(columnIndex);
					}
					cell.removeCellComment();

					String tmpComment = esDT.getExcelSheetCell(rowindex, columnIndex).getCellComment();
					if (!StringUtils.isNotEmpty(tmpComment)) {
						cell.setCellStyle(csUnComment);
					} else {
						Comment ct = p.createCellComment(ca);
						ct.setAuthor("admin");
						RichTextString rs = null;
						if (isXSSF) {
							rs = new XSSFRichTextString();
							((XSSFRichTextString) rs).setString(tmpComment);
						} else {
							rs = new HSSFRichTextString(tmpComment);

						}
						ct.setString(rs);

						cell.setCellComment(ct);
						cell.setCellStyle(csComment);
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw ex;
			return false;
		}
		if (lastProcess != null) {
			lastProcess.Process(sheet, esDT);
		}
		return true;
	}

	public static boolean setCellValueToExcel(ExcelSheetDataTable esDT, Sheet sheet, ProcessSheet lastProcess)
			throws Exception {
		if (esDT == null) {
			throw new Exception("ExcelSheetDataTable esDT非null");
		}
		if (sheet == null) {
			throw new Exception("sheet非null");
		}
		try {
			List<Integer> rowIndexList = esDT.getRowIndexList();
			int rowIndexListLength = rowIndexList.size();
			for (int i = 0; i < rowIndexListLength; i++) {
				int rowindex = rowIndexList.get(i).intValue();
				Row row = sheet.getRow(rowindex);
				if (row == null) {
					row = sheet.createRow(rowindex);
				}
				Map<ExcelSheetLocation, String> rowMap = esDT.getRowValueMap(rowindex);
				List<Integer> curColumnIndexList = rowMap.keySet().stream().map(s -> s.getColumnIndex())
						.sorted(Comparator.comparing(num -> num)).collect(Collectors.toList());
				int rowMapLength = curColumnIndexList.size();
				for (int j = 0; j < rowMapLength; j++) {
					int columnIndex = curColumnIndexList.get(j);
					Cell cell = row.getCell(columnIndex);
					if (cell == null) {
						cell = row.createCell(columnIndex);
					}
					cell.setCellValue(esDT.getCellValue(rowindex, columnIndex));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw ex;
			return false;
		}
		if (lastProcess != null) {
			lastProcess.Process(sheet, esDT);
		}
		return true;
	}

	public boolean setCellToExcel(ExcelSheetDataTable esDT, Sheet sheet, ProcessSheet lastProcess) throws Exception {
		if (esDT == null) {
			throw new Exception("ExcelSheetDataTable esDT非null");
		}
		if (sheet == null) {
			throw new Exception("sheet非null");
		}
		boolean isXSSF = false;
		try {

			Workbook wb = sheet.getWorkbook();
			if (wb.getClass().getTypeName().contains("XSSFWorkbook")) {
				isXSSF = true;
			}
			CellStyle csComment = wb.createCellStyle();
			csComment.setFillForegroundColor(HSSFColor.RED.index);
			csComment.setFillPattern(CellStyle.SOLID_FOREGROUND);
			CellStyle csUnComment = wb.createCellStyle();
			csUnComment.setFillPattern(CellStyle.NO_FILL);
			// 创建绘图对象
			Drawing p = sheet.createDrawingPatriarch();
			ClientAnchor ca = p.createAnchor(0, 0, 0, 0, 3, 3, 5, 6);

			List<Integer> rowIndexList = esDT.getRowIndexList();
			int rowIndexListLength = rowIndexList.size();
			for (int i = 0; i < rowIndexListLength; i++) {
				int rowindex = rowIndexList.get(i).intValue();
				Row row = sheet.getRow(rowindex);
				if (row == null) {
					row = sheet.createRow(rowindex);
				}
				Map<ExcelSheetLocation, String> rowMap = esDT.getRowValueMap(rowindex);
				List<Integer> curColumnIndexList = rowMap.keySet().stream().map(s -> s.getColumnIndex())
						.sorted(Comparator.comparing(num -> num)).collect(Collectors.toList());
				int rowMapLength = curColumnIndexList.size();
				for (int j = 0; j < rowMapLength; j++) {
					int columnIndex = curColumnIndexList.get(j);
					Cell cell = row.getCell(columnIndex);
					if (cell == null) {
						cell = row.createCell(columnIndex);
					}
					cell.setCellValue(esDT.getCellValue(rowindex, columnIndex));

					cell.removeCellComment();
					String tmpComment = esDT.getExcelSheetCell(rowindex, columnIndex).getCellComment();
					if (!StringUtils.isNotEmpty(tmpComment)) {
						cell.setCellStyle(csUnComment);
					} else {
						Comment ct = p.createCellComment(ca);
						ct.setAuthor("admin");
						RichTextString rs = null;
						if (isXSSF) {
							rs = new XSSFRichTextString();
							((XSSFRichTextString) rs).setString(tmpComment);
						} else {
							rs = new HSSFRichTextString(tmpComment);
						}
						ct.setString(rs);

						cell.setCellComment(ct);
						cell.setCellStyle(csComment);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw ex;
			return false;
		}
		if (lastProcess != null) {
			lastProcess.Process(sheet, esDT);
		}
		return true;
	}
	// endregion

	/**
	 * Excel =2007 数据限制，行(1048576)列(16384)
	 *
	 * @param headers  属性-列头
	 * @param fields   字段
	 * @param dataset  导出数据集合
	 * @param fileName 文件名
	 */
	public static XSSFWorkbook exportExcel(String[] headers, String[] fields, List dataset, String fileName) {

		String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式
		int DEFAULT_COLOUMN_WIDTH = 30;

		String datePattern = DEFAULT_DATE_PATTERN;
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();// 缓存
		// 列头样式
		CellStyle headerStyle = workbook.createCellStyle();
		// headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		//headerStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		headerStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 单元格样式
		CellStyle cellStyle = workbook.createCellStyle();
		// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font cellFont = workbook.createFont();
		cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(cellFont);
		// 生成一个(带标题)表格
		XSSFSheet sheet = (XSSFSheet) workbook.createSheet();
		// 设置列宽
		int minBytes = DEFAULT_COLOUMN_WIDTH;// 至少字节数
		int[] arrColWidth = new int[headers.length];
		// 产生表格标题行,以及设置列宽
		String[] properties = new String[headers.length];

		// 遍历集合数据，产生数据行
		int rowIndex = 1;
		// 先把列头输出到excel
		XSSFRow headerRow = (XSSFRow) sheet.createRow(0); // 列头 rowIndex =1
		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
			Drawing p = sheet.createDrawingPatriarch();
			ClientAnchor ca = p.createAnchor(0, 0, 0, 0, 3, 3, 5, 6);
			Comment ct = p.createCellComment(ca);
			XSSFRichTextString string = new XSSFRichTextString(fields[i]);
			ct.setAuthor("admin");
			ct.setString(string);
			Cell cell = headerRow.getCell(i);
			cell.removeCellComment();
			cell.setCellComment(ct);
			cell.setCellStyle(headerStyle);
		}

		for (Object obj : dataset) {
			if (rowIndex == 65535 || rowIndex == 1) {

				if (rowIndex != 1) {
					sheet = (XSSFSheet) workbook.createSheet();// 如果数据超过了，则在第二页显示
					rowIndex = 1;

					// 新sheet页创建列头
					XSSFRow headerRow2 = (XSSFRow) sheet.createRow(rowIndex); // 列头 rowIndex =1
					for (int i = 0; i < headers.length; i++) {
						headerRow2.createCell(i).setCellValue(headers[i]);
						headerRow2.getCell(i).setCellStyle(headerStyle);
					}
				}

				int ii = 0;
				for (String fieldName : fields) {
					properties[ii] = fieldName;
					int bytes = fieldName.getBytes().length;
					arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
					sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
					ii++;
				}
			}
			Map<String, Object> map = null ;
			if(obj instanceof HashMap) {
				map= (Map<String, Object>) obj;
			}else {
				map = BeanUtil.objectToMap(obj);
			}
			
			XSSFRow dataRow = (XSSFRow) sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				XSSFCell newCell = (XSSFCell) dataRow.createCell(i);

				Object o = map.get(properties[i]);
				String cellValue = "";
				if (o == null)
					cellValue = "";
				else if (o instanceof Date)
					cellValue = new SimpleDateFormat(datePattern).format(o);
				else if (o instanceof Float || o instanceof Double) {
					cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
					if (cellValue.lastIndexOf(".00") > 0) {
						cellValue = cellValue.replace(".00", "");
					}
				} else
					cellValue = o.toString();

				newCell.setCellValue(cellValue);
				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		return workbook;
	}

	public static <T> void setFieldValue(Field field, String cellValue, T t, String name) throws Exception {
	
		if (field.getType() == Integer.class && name.equals("staffSex")) {
			field.set(t, SexEnum.valueOf(cellValue).getIndex());
		} else if (field.getType() == String.class) {
			field.set(t, cellValue);
		} else if (field.getType() == Integer.class) {
			field.set(t, Integer.parseInt(cellValue));
		} else if (field.getType() == Date.class) {
			field.set(t, DateUtil.formatDate(cellValue, DateUtil.DATE_CUT_DAY));
		} else if (field.getType() == Date.class && cellValue.length() > 12) {
			field.set(t, DateUtil.formatDate(cellValue, DateUtil.DATE_CUT_DAY));
		}
	}
}
