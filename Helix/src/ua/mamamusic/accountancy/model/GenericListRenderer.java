package ua.mamamusic.accountancy.model;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class GenericListRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Component renderComponent = super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

		renderComponent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		return renderComponent;
	}

}
