package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProductRowTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ProductRow> list;
	
	public ProductRowTableModel(List<ProductRow> list){
		this.setList(list);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductRow productEntity = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return productEntity.getArtist();
		case 1:
			return productEntity.getTrack();
		case 2:
			return productEntity.getType();
		case 3:
			return productEntity.getQuantity();
		case 4:
			return productEntity.getIncome();
		case 5:
			return productEntity.getDate();
		case 6:
			return productEntity.getDistributor();
		case 7:
			return productEntity.getRightType();
		case 8:
			return productEntity.getRightPercent();
		case -1:
			return productEntity;
		}
	return "";
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return super.getColumnClass(arg0);
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
		case 5:
			return "Date";
		case 6:
			return "Distributor";
		case 7:
			return "RightType";
		case 8:
			return "Percent";
		}
		return "";
	}

	public List<ProductRow> getList() {
		return list;
	}

	public void setList(List<ProductRow> list) {
		this.list = list;
	}
	
}
