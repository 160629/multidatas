package com.example.demo.util;


import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class ExcelSheetDataTable {
	private final static String DEFAULT_SHEET_NAME = "Sheet0";
	private int sheetIndex;
	private String sheetName;
	private Map<ExcelSheetLocation, ExcelSheetCell> dataTable;

	public ExcelSheetDataTable() throws Exception {
		this(0, DEFAULT_SHEET_NAME, new HashMap<ExcelSheetLocation, ExcelSheetCell>());
	}

	public ExcelSheetDataTable(int sheetIndex) throws Exception {
		this(sheetIndex, DEFAULT_SHEET_NAME, new HashMap<ExcelSheetLocation, ExcelSheetCell>());
	}

	public ExcelSheetDataTable(int sheetIndex, String sheetName) throws Exception {
		this(sheetIndex, sheetName, new HashMap<ExcelSheetLocation, ExcelSheetCell>());
	}

	public ExcelSheetDataTable(int sheetIndex, String sheetName, Map<ExcelSheetLocation, ExcelSheetCell> dataTable)
			throws Exception {
		if (sheetIndex < 0) {
			throw new Exception("Sheet 索引必须大于等于0!");
		}
		if (!StringUtils.isNotEmpty(sheetName)) {
			throw new Exception("Sheet 名称必须非空!");
		}
		if (dataTable == null) {
			throw new Exception("Sheet 数据集合必须非空!");
		}
		this.sheetIndex = sheetIndex;
		this.sheetName = sheetName;
		this.dataTable = dataTable;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) throws Exception {
		if (sheetIndex < 0) {
			throw new Exception("Sheet 索引必须大于等于0!");
		}
		this.sheetIndex = sheetIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) throws Exception {
		if (StringUtils.isNotEmpty(sheetName)) {
			throw new Exception("Sheet 名称必须非空!");
		}
		this.sheetName = sheetName;
	}

	public List<Integer> getRowIndexList() {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<Integer> rowIndexList = keys.stream().map(ExcelSheetLocation::getRowIndex).distinct()
				.collect(Collectors.toList());

		return rowIndexList;
	}

	public int getRowCount() {
		List<Integer> rowIndexList = getRowIndexList();
		return rowIndexList.size();
	}

	public int getRowStartIndex() {
		List<Integer> rowIndexList = getRowIndexList();
		return rowIndexList.stream().min(Comparator.comparing(num -> num)).get().intValue();
	}

	public int getRowEndIndex() {
		List<Integer> rowIndexList = getRowIndexList();
		return rowIndexList.stream().max(Comparator.comparing(num -> num)).get().intValue();
	}

	public List<Integer> getColumnIndexList() {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<Integer> columnIndexList = keys.stream().map(ExcelSheetLocation::getColumnIndex).distinct()
				.collect(Collectors.toList());

		return columnIndexList;
//        Collection<ExcelSheetCell> values=this.dataTable.values();
//        List<Integer> rowIndexList= values.stream().map(ExcelSheetCell::getRowIndex).distinct().collect(Collectors.toList());

	}

	public int getColumnCount() {
		List<Integer> columnIndexList = getColumnIndexList();
		return columnIndexList.size();
	}

	public int getColumnStartIndex() {
		List<Integer> columnIndexList = getColumnIndexList();
		return columnIndexList.stream().min(Comparator.comparing(num -> num)).get().intValue();
	}

	public int getColumnEndIndex() {
		List<Integer> columnIndexList = getColumnIndexList();
		return columnIndexList.stream().max(Comparator.comparing(num -> num)).get().intValue();
	}

	public ExcelSheetDataTable getRowExcelSheetDataTable(int rowIndex) throws Exception {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<ExcelSheetLocation> rowLocationList = keys.stream().filter(p -> p.getRowIndex().equals(rowIndex))
				.sorted(Comparator.comparing(lt -> lt.getColumnIndex())).collect(Collectors.toList());
		if (rowLocationList == null) {
			return null;
		}
		ExcelSheetDataTable dt = new ExcelSheetDataTable(this.sheetIndex, this.sheetName);
		rowLocationList.forEach(p -> {
			try {
				dt.setExcelSheetCell(p, this.dataTable.get(p));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		);
		return dt;
	}

	public Map<ExcelSheetLocation, String> getRowValueMap(int rowIndex) throws Exception {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<ExcelSheetLocation> rowLocationList = keys.stream().filter(p -> p.getRowIndex().equals(rowIndex))
				.sorted(Comparator.comparing(lt -> lt.getColumnIndex())).collect(Collectors.toList());
		if (rowLocationList == null) {
			return null;
		}
		Map<ExcelSheetLocation, String> valueMap = new HashMap<>();
		rowLocationList.forEach(p -> {
			valueMap.put(p, this.dataTable.get(p).getCellValue());
		}

		);
		return valueMap;
	}

	public Map<ExcelSheetLocation, String> getColumnValueMap(int columnIndex) throws Exception {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<ExcelSheetLocation> columnLocationList = keys.stream().filter(p -> p.getColumnIndex().equals(columnIndex))
				.sorted(Comparator.comparing(lt -> lt.getColumnIndex())).collect(Collectors.toList());
		if (columnLocationList == null) {
			return null;
		}
		Map<ExcelSheetLocation, String> valueMap = new HashMap<>();
		columnLocationList.forEach(p -> {
			valueMap.put(p, this.dataTable.get(p).getCellValue());
		}

		);

		return valueMap;
	}

	public Map<ExcelSheetLocation, String> getColumnValueCompareMap(int columnIndex, String compareString)
			throws Exception {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<ExcelSheetLocation> columnLocationList = keys.stream().filter(p -> p.getColumnIndex().equals(columnIndex))
				.sorted(Comparator.comparing(lt -> lt.getColumnIndex())).collect(Collectors.toList());
		if (columnLocationList == null) {
			return null;
		}

		Map<ExcelSheetLocation, String> valueMap = new HashMap<>();
		columnLocationList.forEach(p -> {
			if (compareString == null) {
				if (this.dataTable.get(p).getCellValue() == null) {
					valueMap.put(p, null);
				}
			} else {
				if (compareString.equals(this.dataTable.get(p).getCellValue())) {
					valueMap.put(p, this.dataTable.get(p).getCellValue());
				}
			}

		}

		);

		return valueMap;
	}

	public Map<ExcelSheetLocation, String> getNotEmptyCellCommentMap() {
		Set<ExcelSheetLocation> keys = this.dataTable.keySet();
		List<ExcelSheetLocation> rowLocationList = keys.stream()
				.filter(p -> !StringUtils.isNotEmpty(dataTable.get(p).getCellComment()))
				.sorted(Comparator.comparing(lt -> lt.getColumnIndex())).collect(Collectors.toList());
		Map<ExcelSheetLocation, String> commentMap = new HashMap<>();
		rowLocationList.forEach(p -> {
			commentMap.put(p, this.dataTable.get(p).getCellComment());
		}

		);
		return commentMap;
	}

	public ExcelSheetCell getExcelSheetCell(ExcelSheetLocation location) throws Exception {
/*		if (!this.dataTable.containsKey(location)) {
			return null;
		}*/
		return this.dataTable.get(location);
	}

	public ExcelSheetCell getExcelSheetCell(int rowIndex, int columnIndex) throws Exception {
		return getExcelSheetCell(new ExcelSheetLocation(rowIndex, columnIndex));
	}

	public String getCellValue(ExcelSheetLocation location) throws Exception {
		ExcelSheetCell cell = getExcelSheetCell(location);
		return cell == null ? null : cell.getCellValue();
	}

	public String getCellValue(int rowIndex, int columnIndex) throws Exception {
		ExcelSheetCell cell = getExcelSheetCell(rowIndex, columnIndex);
		return cell == null ? null : cell.getCellValue();
	}

	public boolean setExcelSheetCell(ExcelSheetLocation location, ExcelSheetCell cell) throws Exception {
		boolean success = false;
		if (location == null) {
			throw new Exception("location 不能为null！");
		}
		if (cell == null) {
			throw new Exception("cell 不能为null！");
		}

		this.dataTable.put(location, cell);

		success = true;

		return success;
	}

	public boolean setExcelSheetCell(int rowIndex, int columnIndex, ExcelSheetCell cell) throws Exception {
		return setExcelSheetCell(new ExcelSheetLocation(rowIndex, columnIndex), cell);
	}

	public boolean setOnlyCellValue(ExcelSheetLocation location, String cellValue) throws Exception {
		boolean success = false;
		if (location == null) {
			throw new Exception("location 不能为null！");
		}
		ExcelSheetCell cell = null;
		if (this.dataTable.containsKey(location)) {
			ExcelSheetCell tmp = this.dataTable.get(location);
			if (tmp == null) {
				cell = new ExcelSheetCell(cellValue);
				this.dataTable.put(location, cell);
			} else {
				tmp.setCellValue(cellValue);
			}
		} else {
			cell = new ExcelSheetCell(cellValue);
			this.dataTable.put(location, cell);
		}

		success = true;

		return success;
	}

	public boolean setOnlyCellComment(ExcelSheetLocation location, String cellComment) throws Exception {
		boolean success = false;
		if (location == null) {
			throw new Exception("location 不能为null！");
		}
		ExcelSheetCell cell = null;
		if (this.dataTable.containsKey(location)) {
			ExcelSheetCell tmp = this.dataTable.get(location);
			if (tmp == null) {
				cell = new ExcelSheetCell(null, cellComment);
				this.dataTable.put(location, cell);
			} else {
				tmp.setCellComment(cellComment);
			}
		} else {
			cell = new ExcelSheetCell(null, cellComment);
			this.dataTable.put(location, cell);
		}
		success = true;

		return success;
	}

	public boolean setOnlyCellFormat(ExcelSheetLocation location, Short cellFormat) throws Exception {
		boolean success = false;
		if (location == null) {
			throw new Exception("location 不能为null！");
		}
		ExcelSheetCell cell = null;
		if (this.dataTable.containsKey(location)) {
			ExcelSheetCell tmp = this.dataTable.get(location);
			if (tmp == null) {
				cell = new ExcelSheetCell(null, null, cellFormat);
				this.dataTable.put(location, cell);
			} else {
				tmp.setCellFormat(cellFormat);
			}
		} else {
			cell = new ExcelSheetCell(null, null, cellFormat);
			this.dataTable.put(location, cell);
		}
		success = true;

		return success;
	}

	public boolean setOnlyCellValue(int rowIndex, int columnIndex, String cellValue) throws Exception {
		return setOnlyCellValue(new ExcelSheetLocation(rowIndex, columnIndex), cellValue);
	}

	public boolean setOnlyCellComment(int rowIndex, int columnIndex, String cellComment) throws Exception {
		return setOnlyCellComment(new ExcelSheetLocation(rowIndex, columnIndex), cellComment);
	}

	public boolean setOnlyCellFormat(int rowIndex, int columnIndex, Short cellFormat) throws Exception {
		return setOnlyCellFormat(new ExcelSheetLocation(rowIndex, columnIndex), cellFormat);
	}

}
