package ua.mamamusic.accountancy.model;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ExternalReportTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row,int col) {

	    if(value instanceof Double){
	    	value = String.format("%1$,.2f", (Double)value);
	    }
	    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);	
	    return c;
	}
}
