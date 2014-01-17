package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object[]> list;
	private List<String> columnNameList;
	
	public ReportTableModel(List<String> columnNameList, List<Object[]> list){
		this.setList(list);
		this.columnNameList = columnNameList;
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
		if(columnIndex == -1){
			return productEntity;
		}
		
		return getStringFromObject(productEntity[columnIndex]);
	}
	
	private String getStringFromObject(Object object){
		if(object instanceof Artist){
			return ((Artist)object).getName();
		}else if(object instanceof Track){
			return ((Track)object).getName();
		}else if(object instanceof Distributor){
			return ((Distributor)object).getName();
		}else if(object instanceof Double){
			return String.format("%1$,.2f", object);
		}else{
			return object.toString();
		}
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return super.getColumnClass(arg0);
	}

	@Override
	public String getColumnName(int column) {
		return columnNameList.get(column);
	}

	public List<Object[]> getList() {
		return list;
	}

	public void setList(List<Object[]> list) {
		this.list = list;
	}
	
}
