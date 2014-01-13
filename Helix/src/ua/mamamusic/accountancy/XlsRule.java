package ua.mamamusic.accountancy;

public class XlsRule {

	private int columnsCount;
	private int idColumn;
	private int priceColumn;
	
	public XlsRule(int columnsCoun, int idColumn, int priceColumn) {
		this.columnsCount = columnsCoun;
		this.idColumn = idColumn;
		this.priceColumn = priceColumn;
	}
	public int getColumnsCount() {
		return columnsCount;
	}
	public void setColumnsCount(int columnsCount) {
		this.columnsCount = columnsCount;
	}
	public int getIdColumn() {
		return idColumn;
	}
	public void setIdColumn(int idColumn) {
		this.idColumn = idColumn;
	}
	public int getPriceColumn() {
		return priceColumn;
	}
	public void setPriceColumn(int priceColumn) {
		this.priceColumn = priceColumn;
	}
	
}
