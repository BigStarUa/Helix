package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ArtistsListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Artist> list;
	
	public ArtistsListTableModel(List<Artist> list){
		this.setList(list);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Artist product = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return product.getName();
		case -1:
			return product;
		}
		return "";
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Name";
		}
		return "";
	}

	public List<Artist> getList() {
		return list;
	}

	public void setList(List<Artist> productList) {
		this.list = productList;
	}
	
	public void addArtist(Artist artist){
		list.add(artist);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}

	public void removeProduct(int row){
		list.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
}
