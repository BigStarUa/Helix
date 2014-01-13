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
import ua.mamamusic.accountancy.TracksListListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistsListTableModel;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorsListTableModel;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TracksListTableModel;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;
import ua.mamamusic.accountancy.session.TrackManager;
import ua.mamamusic.accountancy.session.TrackManagerImpl;

import java.awt.Font;
import java.awt.Color;

public class TracksList extends AbstractJPanel implements TracksListListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TracksList artistList;
	private JTable table;
	private Window window;
	private TableRowSorter<TableModel> sorter;
	private JTextField filterText;
	private JToolBar toolBar;
	private JButton editButton;
	private JButton delButton;
	private TracksListTableModel model;
	private TrackManagerImpl tm;

	private TracksList(){
		setLayout(new BorderLayout());
		filterText = new JTextField();
		window = SwingUtilities.getWindowAncestor(TracksList.this);
		
		// Getting list of product from database
		tm = new TrackManagerImpl();
		List<Track> list = tm.loadAllTracksOrderedBy("name");
		
		//list = new ArrayList<>();
				
		//Creating table model from list of products
		model = new TracksListTableModel(list);
		
		//Creating table
		table = new JTable(model);
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
				        Track track = (Track) table.getModel().getValueAt(row, -1);
				        JDialog pf = new TrackForm(window, "Track form", track, TracksList.this);
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
	
	public static synchronized TracksList getInstance(){
		if(artistList == null){
			artistList = new TracksList();
		}
		return artistList;
	}

	private JToolBar getToolBar() {
		JToolBar toolBar = new JToolBar();
		
		JButton addButton = new JButton();
		addButton.setIcon(IconFactory.ADD32_ICON);
		addButton.setToolTipText("Add Distributor");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Track track = new Track();
				JDialog pf = new TrackForm(window, "Track form", track, TracksList.this);
				pf.setVisible(true);
			}
		});
		toolBar.add(addButton);
		
		editButton = new JButton();
		editButton.setIcon(IconFactory.EDIT32_ICON);
		editButton.setToolTipText("Edit Distributor");
		editButton.setFocusable(false);
		editButton.setEnabled(false);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				RowSorter<? extends TableModel> rowSorter = table.getRowSorter();
		        row = rowSorter.convertRowIndexToModel(row);
				if(row > -1){
					Track track = (Track) table.getModel().getValueAt(row, -1);
					JDialog pf = new TrackForm(window, "Track form", track, TracksList.this);
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
		delButton.setToolTipText("Delete Distributor");
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
				            "Congratulations!", JOptionPane.YES_NO_OPTION,
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
		        	TracksList.this.toolBar.repaint();
		        	TracksList.this.toolBar.revalidate();
		        }
		        //getListener().fireUpdate();
		        //filterText.requestFocusInWindow();
			}
		});
		toolBar.add(filterText);
		return toolBar;
	}

	@Override
	public void saveTrack(Track track) {
		TrackManager tm = new TrackManagerImpl();
		if(track.getId() > 0){
			tm.updateTrack(track);
			System.out.println("Product updated");
		}else{
			tm.saveNewTrack(track);
			model.addTrack(track);
			System.out.println("Product saved");
		}
	}
	
}
