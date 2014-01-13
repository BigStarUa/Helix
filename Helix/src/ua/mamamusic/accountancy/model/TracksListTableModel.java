package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TracksListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Track> list;
	
	public TracksListTableModel(List<Track> list){
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
		Track product = list.get(rowIndex);
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

	public List<Track> getList() {
		return list;
	}

	public void setList(List<Track> trackList) {
		this.list = trackList;
	}
	
	public void addTrack(Track track){
		list.add(track);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}

	public void removeTrack(int row){
		list.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
}
