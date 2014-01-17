package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class UploadListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DataRow> list;
	
	public UploadListTableModel(List<DataRow> list){
		this.setList(list);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DataRow productEntity = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return productEntity.getColumnArtist();
		case 1:
			return productEntity.getColumnTrack();
		case 2:
			return productEntity.getColumnTrackType();
		case 3:
			return productEntity.getColumnQuantity();
		case 4:
			return productEntity.getColumnPrice();
		case 5:
			return productEntity.getColumnRelatedIncome();
		case -1:
			return productEntity;
		}
	return "";
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Artist";
		case 1:
			return "Track";
		case 2:
			return "Type";
		case 3:
			return "Quantity";
		case 4:
			return "Income";
		}
		return "";
	}

	public List<DataRow> getList() {
		return list;
	}

	public void setList(List<DataRow> productList) {
		this.list = productList;
	}
	

	public void removeProduct(int row){
		list.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void productChanged(DataRow product){
		int index = list.indexOf(product);
		fireTableRowsUpdated(index, index);
	}
}
