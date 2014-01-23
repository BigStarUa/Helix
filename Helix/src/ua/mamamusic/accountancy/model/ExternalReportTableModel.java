package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ExternalReportTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ProductRow> list;
	private List<String> columnTitleList;
	
	public ExternalReportTableModel(List<ProductRow> list, List<String> columnTitleList){
		this.setList(list);
		this.columnTitleList = columnTitleList;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
			return columnTitleList.size();
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductRow object = list.get(rowIndex);
		if(columnIndex == -1){
			return object;
		}
		
		return getCell(rowIndex, columnIndex);
		
		//return "";

	}
	
	private Object getCell(int rowIndex, int columnIndex){
		ProductRow object = list.get(rowIndex);
		if(columnTitleList.get(columnIndex) == "Artist"){
			return object.getArtist();
		}else if(columnTitleList.get(columnIndex) == "Track"){
			return object.getTrack();
		}else if(columnTitleList.get(columnIndex) == "Type"){
			return object.getType();
		}else if(columnTitleList.get(columnIndex) == "Quantity"){
			return object.getQuantity();
		}else if(columnTitleList.get(columnIndex) == "Income"){
			return object.getIncome();
		}else if(columnTitleList.get(columnIndex) == "Artist income"){
			return object.getArtistIncome();
		}else if(columnTitleList.get(columnIndex) == "Company income"){
			return object.getCompanyIncome();
		}else if(columnTitleList.get(columnIndex) == "Distributor"){
			return object.getDistributor();
		}else if(columnTitleList.get(columnIndex) == "Rights"){
			return object.getRightType();
		}else if(columnTitleList.get(columnIndex) == "Date"){
			return object.getDate();
		}else{
			return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		
		return columnTitleList.get(column);
		
	}

	public List<ProductRow> getList() {
		return list;
	}

	public void setList(List<ProductRow> list) {
		this.list = list;
	}
	
}
