package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.ArtistsListListener;
import ua.mamamusic.accountancy.DistributorsListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistsListTableModel;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorsListTableModel;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.Component;

public class ArtistsList extends AbstractJPanel implements ArtistsListListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArtistsList artistList;
	private JTable table;
	private Window window;
	private TableRowSorter<TableModel> sorter;
	private JTextField filterText;
	private JToolBar toolBar;
	private JButton editButton;
	private JButton delButton;
	private ArtistsListTableModel model;
	private ArtistManagerImpl am;
	private JPanel panel;
	private JLabel lblFilter;

	private ArtistsList(){
		setLayout(new BorderLayout());
		window = SwingUtilities.getWindowAncestor(ArtistsList.this);
		
		// Getting list of product from database
		am = new ArtistManagerImpl();
		List<Artist> list = am.loadAllArtistsOrderedBy("name");
		
		//list = new ArrayList<>();
				
		//Creating table model from list of products
		model = new ArtistsListTableModel(list);
		
		//Creating table
		table = new JTable(model);
		table.setShowGrid(false);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setRowHeight(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				if(row > -1 && e.getButton() == MouseEvent.BUTTON1){
					if (e.getClickCount() == 2){
						RowSorter<? extends TableModel> rowSorter = table.getRowSorter();
				        row = rowSorter.convertRowIndexToModel(row);
				        Artist artist = (Artist) table.getModel().getValueAt(row, -1);
				        JDialog pf = new ArtistForm(window, "Artist form", Dialog.ModalityType.DOCUMENT_MODAL, artist, ArtistsList.this);
						pf.setVisible(true);
					}
				}
			}
		});
		
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){		
					//toolBar = getToolBar();
					editButton.setEnabled(true);
					delButton.setEnabled(true);
					toolBar.repaint();
					toolBar.revalidate();
				}
				
			}
		});
		
		//make table sortable
		sorter = new TableRowSorter<TableModel>(model);
	    
	    toolBar = getToolBar();
	    add(toolBar, BorderLayout.NORTH);
	    table.setRowSorter(sorter);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public static synchronized ArtistsList getInstance(){
		if(artistList == null){
			artistList = new ArtistsList();
		}
		return artistList;
	}

	private JToolBar getToolBar() {
		JToolBar toolBar = new JToolBar();
		
		JButton addButton = new JButton();
		addButton.setIcon(IconFactory.ADD32_ICON);
		addButton.setToolTipText("Add artist");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Artist artist = new Artist();
				JDialog pf = new ArtistForm(window, "Artist form", Dialog.ModalityType.DOCUMENT_MODAL, artist, ArtistsList.this);
				pf.setVisible(true);
			}
		});
		toolBar.add(addButton);
		
		editButton = new JButton();
		editButton.setIcon(IconFactory.EDIT32_ICON);
		editButton.setToolTipText("Edit artist");
		editButton.setFocusable(false);
		editButton.setEnabled(false);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				RowSorter<? extends TableModel> rowSorter = table.getRowSorter();
		        row = rowSorter.convertRowIndexToModel(row);
				if(row > -1){
					Artist distributor = (Artist) table.getModel().getValueAt(row, -1);
					JDialog pf = new ArtistForm(window, "Distributor form", Dialog.ModalityType.DOCUMENT_MODAL, distributor, ArtistsList.this);
					pf.setVisible(true);
				}
			}
		});
		if(table.getSelectedRow() > -1){
			//editButton.setEnabled(true);
	    }
		toolBar.add(editButton);
		
		delButton = new JButton();
		delButton.setIcon(IconFactory.DELETE32_ICON);
		delButton.setToolTipText("Delete artist");
		delButton.setFocusable(false);
		delButton.setEnabled(false);
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				RowSorter<? extends TableModel> rowSorter = table.getRowSorter();
		        row = rowSorter.convertRowIndexToModel(row);
				if(row > -1){
					int selection = JOptionPane.showOptionDialog(null,
				            "Are you sure want to delete?",
				            "Delete!", JOptionPane.YES_NO_OPTION,
				            JOptionPane.INFORMATION_MESSAGE, null, null, null);

					if(selection == JOptionPane.YES_OPTION)
					{

					}
				}
			}
		});
		if(table.getSelectedRow() > -1){
			//editButton.setEnabled(true);
	    }
		toolBar.add(delButton);
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(8);
		flowLayout.setAlignment(FlowLayout.TRAILING);
		toolBar.add(panel);
		
		lblFilter = new JLabel("Filter:");
		panel.add(lblFilter);
		filterText = new JTextField();
		filterText.setColumns(20);
		panel.add(filterText);
		
		
		
		filterText.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent ke)
			{
				String text = filterText.getText();
		        if (text.length() == 0) {
		          sorter.setRowFilter(null);
		        } else {
		          sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		        }
		        if(table.getSelectedRow() == -1){
		        	editButton.setEnabled(false);
		        	ArtistsList.this.toolBar.repaint();
		        	ArtistsList.this.toolBar.revalidate();
		        }
		        //getListener().fireUpdate();
		        //filterText.requestFocusInWindow();
			}
		});
		return toolBar;
	}

	@Override
	public void saveArtist(Artist artist) {
		//ArtistManager am = new ArtistManagerImpl();
		if(artist.getId() > 0){
			am.updateArtist(artist);
			System.out.println("Product updated");
		}else{
			am.saveNewArtist(artist);
			model.addArtist(artist);
			System.out.println("Product saved");
		}
	}
	
}
