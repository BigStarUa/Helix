package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object[]> list;
	
	public ReportTableModel(List<Object[]> list){
		this.setList(list);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		try{
			Object[] productEntity = list.get(0);
			return productEntity.length;
		}catch(IndexOutOfBoundsException iobe){
			return 0;
		}
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object[] productEntity = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return ((Artist)productEntity[0]).getName();
		case 1:
			return ((Track)productEntity[1]).getName();
		case 2:
			if(productEntity[2] instanceof TrackType){
				return ((TrackType)productEntity[2]).getName();
			}else if(productEntity[2] instanceof Distributor){
				return ((Distributor)productEntity[2]).getName();
			}else{
				return productEntity[2];
			}
		case 3:
			if(productEntity[3] instanceof Distributor){
				return ((Distributor)productEntity[3]).getName();
			}else if(productEntity[3] instanceof Double){
				return String.format("%1$,.2f", productEntity[3]);
			}else{
				return productEntity[3];
			}
		case 4:
			if(productEntity[4] instanceof Double){
				return String.format("%1$,.2f", productEntity[4]);
			}else{
				return productEntity[4];
			}
		case 5:
			return String.format("%1$,.2f", productEntity[5]);
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
			return "Name";
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
