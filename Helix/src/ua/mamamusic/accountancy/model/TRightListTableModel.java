package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TRightListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TRight> list;
	
	public TRightListTableModel(List<TRight> list){
		this.setList(list);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TRight product = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return product.getArtist().getName();
		case 1:
			return product.getAuthor();
		case 2:
			return product.getRelated();
		case -1:
			return product;
		}
		return "";
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Artist";
		case 1:
			return "Author";
		case 2:
			return "Related";
		}
		return "";
	}

	public List<TRight> getList() {
		return list;
	}

	public void setList(List<TRight> productList) {
		this.list = productList;
	}
	
	public int getCountAuthorRights(){
		int count = 0;
		for(int i = 0; i < list.size(); i++){
			count += list.get(i).getAuthor();
		}
		return 100 - count;
	}
	
	public int getCountRelatedRights(){
		int count = 0;
		for(int i = 0; i < list.size(); i++){
			count += list.get(i).getRelated();
		}
		return 100 - count;
	}
	
	public void addOrUpdateRight(TRight right){
		if(list.contains(right)){
			int index = list.indexOf(right);
			fireTableRowsUpdated(index, index);
		}else{
			list.add(right);
			fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
		}
	}

	public void removeRight(int row){
		list.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
}
