package ua.mamamusic.accountancy.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;

import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.UploadFormListener;
import ua.mamamusic.accountancy.gui.AddArtistAlias;
import ua.mamamusic.accountancy.gui.AddTrackAlias;
import ua.mamamusic.accountancy.gui.AddTypeAlias;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.TrackTypeManager;
import ua.mamamusic.accountancy.session.TrackTypeManagerImpl;

public class UploadListTableCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel lable;
	private JButton addTrackButton;
	private JButton addArtistButton;
	private DataRow product;
	private ActionListener trackButtonAction;
	private ActionListener artistButtonAction;
	private List<Artist> artistList;
	private JButton addTypeButton;
	private ActionListener typeButtonAction;
	private List<TrackType> typeSet;
	
	public UploadListTableCellEditor(final UploadFormListener listener){
		panel = new JPanel();
//		FlowLayout flowLayout = new FlowLayout();
//		flowLayout.setAlignment(FlowLayout.LEADING);
    	panel.setLayout(new BorderLayout());
    	lable = new JLabel();
    	lable.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	
    	ArtistManager am = new ArtistManagerImpl();
    	//artistList = new HashSet<Artist>(am.loadAllArtistsOrderedBy("name"));
    	artistList = am.loadAllArtistsOrderedBy("name");
    	
    	TrackTypeManager ttm = new TrackTypeManagerImpl();
		//typeSet = new HashSet<TrackType>(ttm.loadAllTrackTypesOrderedBy("name"));
		typeSet = ttm.loadAllTrackTypesOrderedBy("name");
    	
    	addTrackButton = new JButton(IconFactory.ADD16_ICON);
    	addTrackButton.setPreferredSize(new Dimension(20, 20));
    	
    	addArtistButton = new JButton(IconFactory.ADD16_ICON);
    	addArtistButton.setPreferredSize(new Dimension(20, 20));
    	
    	addTypeButton = new JButton(IconFactory.ADD16_ICON);
    	addTypeButton.setPreferredSize(new Dimension(20, 20));
    	
    	trackButtonAction = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(product.getColumnTrack() == ""){
					JOptionPane.showMessageDialog(null, "You can't add empty string");
				}else{
					JDialog addTrackForm = new AddTrackAlias(null, product, listener);
					addTrackForm.setVisible(true);
				}
				stopCellEditing();
			}
    	};
    	artistButtonAction = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(product.getColumnArtist() == ""){
					JOptionPane.showMessageDialog(null, "You can't add empty string");
				}else{
					JDialog addArtistForm = new AddArtistAlias(null, artistList, product.getColumnArtist(), listener);
					addArtistForm.setVisible(true);
				}
				stopCellEditing();
			}
    	};
    	typeButtonAction = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(product.getColumnTrackType() == ""){
					JOptionPane.showMessageDialog(null, "You can't add empty string");
				}else{
					JDialog form = new AddTypeAlias(null, typeSet, product.getColumnTrackType(), listener);
					form.setVisible(true);
				}
				stopCellEditing();
			}
    	};
    	addTrackButton.addActionListener(trackButtonAction);
    	addArtistButton.addActionListener(artistButtonAction);
    	addTypeButton.addActionListener(typeButtonAction);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		
		panel.removeAll();
		
		lable.setText(value.toString());
		
		// Converting row index because of sorting
		int convertedRowIndex = table.convertRowIndexToModel(row);
		
		product = (DataRow)table.getModel().getValueAt(convertedRowIndex, -1);
		
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
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

}
