package com.example.demo.util;

import java.util.Objects;

public class ExcelSheetLocation {
	// base from one
	private Integer rowIndex;
	// base from one
	private Integer columnIndex;

	public ExcelSheetLocation(Integer rowIndex, Integer columnIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ExcelSheetLocation that = (ExcelSheetLocation) o;
		return getRowIndex() == that.getRowIndex() && getColumnIndex() == that.getColumnIndex();
	}

	@Override
	public int hashCode() {

		return Objects.hash(getRowIndex(), getColumnIndex());
	}
}
