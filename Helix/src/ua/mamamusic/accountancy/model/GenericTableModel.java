package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class GenericTableModel<E> extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<E> list;
	
	public GenericTableModel(List<E> list){
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
		E element = list.get(rowIndex);
		return element;

	}

	@Override
	public String getColumnName(int column) {
		return "";
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}
	
	public void addElement(E element){
		list.add(element);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}

	public void removeElement(int row){
		list.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
}
