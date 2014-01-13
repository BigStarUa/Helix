package ua.mamamusic.accountancy.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import ua.mamamusic.accountancy.IconFactory;

public class UploadListTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ArtistAlias> artistAliasList;
	private JButton addArtistButton = new JButton(IconFactory.ADD16_ICON);
	private JButton addTrackButton = new JButton(IconFactory.ADD16_ICON);
	private JButton addTypeButton = new JButton(IconFactory.ADD16_ICON);
	
	public UploadListTableCellRenderer(List<ArtistAlias> artistAliasList){
		this.artistAliasList = artistAliasList;
		addArtistButton.setPreferredSize(new Dimension(20, 20));
		
		addTrackButton.setPreferredSize(new Dimension(20, 20));
		addTypeButton.setPreferredSize(new Dimension(20, 20));
		
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row,int col) {

	    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	   // String s = table.getModel().getValueAt(row, -1).toString();
	    
	    	JPanel panel = new JPanel();
//	    	FlowLayout flowLayout = new FlowLayout();
//			flowLayout.setAlignment(FlowLayout.LEFT);

	    	panel.setLayout(new BorderLayout());
	    	JLabel lable = new JLabel(value.toString());
	    	lable.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    	
	    	// Converting row index because of sorting
	    	int convertedRowIndex = table.convertRowIndexToModel(row);
	    	
	    	DataRow product = (DataRow)table.getModel().getValueAt(convertedRowIndex, -1);
	    	
	    	if(product.isValid()){
	    		panel.setBackground(Color.WHITE);
	    	}else{
	    		if(product.getArtist() == null){
	    			if(col == 0){
		    			panel.add(addArtistButton, BorderLayout.WEST);
					}
		    			panel.setBackground(Color.RED);
	    		}else{
	    			if(product.getTrack() == null){
	    				panel.setBackground(Color.YELLOW);
		    			if(col == 1){
			    			panel.add(addTrackButton, BorderLayout.WEST);
		    			}
	    			}
	    			if(product.getType() == null){
	    				panel.setBackground(Color.YELLOW);
		    			if(col == 2){
			    			panel.add(addTypeButton, BorderLayout.WEST);
		    			}
	    			}
	    		}
	    	}
	    	
	    	panel.add(lable, BorderLayout.CENTER);
	    	panel.setBorder(new EmptyBorder(5, 5, 5, 5));
	    	
	    	
	    return panel;
	}
}
