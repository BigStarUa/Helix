package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ExternalReportTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object[]> list;
	
	public ExternalReportTableModel(List<Object[]> list){
		this.setList(list);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		
			return 8;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object[] object = list.get(rowIndex);
		
		switch(columnIndex){
		case 0:
			return ((Artist)object[0]).getName();
		case 1:
			return ((Track)object[1]).getName();
		case 2:
			return ((TrackType)object[2]).getName();
		case 3:
			return ((Distributor)object[3]).getName();
		case 4:
			return object[4].toString();
		case 5:
			return String.format("%1$,.2f", object[5]);
		case 6:
			return String.format("%1$,.2f", object[5]);
		case 7:
			return object[6];
		}
		if(columnIndex == -1){
			return object;
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
			return "Income";
		case 6:
			return "Income";
		case 7:
			return "Income";
		}
		return "";
	}

	public List<Object[]> getList() {
		return list;
	}

	public void setList(List<Object[]> list) {
		this.list = list;
	}
	
}
