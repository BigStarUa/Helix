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
import javax.swing.JList;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.DistributorsListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.TrackTypeFormListener;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorsListTableModel;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.GenericListRenderer;
import ua.mamamusic.accountancy.model.GenericTableModel;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;
import ua.mamamusic.accountancy.session.TrackTypeManager;
import ua.mamamusic.accountancy.session.TrackTypeManagerImpl;

import java.awt.Font;
import java.awt.Color;

public class TrackTypeList extends AbstractJPanel implements TrackTypeFormListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TrackTypeList distribList;
	private JList<TrackType> list;
	private Window window;
	private TableRowSorter<TableModel> sorter;
	private JTextField filterText;
	private JToolBar toolBar;
	private JButton editButton;
	private JButton delButton;
	private TrackTypeManager ttm;
	private GenericTableModel<TrackType> model;
	private ListSelectionModel listSelectionModel;
	private JTable table;

	private TrackTypeList(){
		setLayout(new BorderLayout());
		filterText = new JTextField();
		window = SwingUtilities.getWindowAncestor(TrackTypeList.this);
		
		// Getting list of product from database
		ttm = new TrackTypeManagerImpl();
		List<TrackType> typeList = ttm.loadAllTrackTypesOrderedBy("name");
		
		//list = new ArrayList<>();
			
		//Creating table model from list of products
		model = new GenericTableModel<TrackType>(typeList);
		//Creating table
		table = new JTable(model);
		table.setShowGrid(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setRowHeight(20);
		//table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				if(row > -1 && e.getButton() == MouseEvent.BUTTON1){
					if (e.getClickCount() == 2){
						TrackType type = (TrackType) table.getModel().getValueAt(row, -1);
				        JDialog pf = new TrackTypeForm(window, type, TrackTypeList.this);
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
					editButton.setEnabled(true);
					delButton.setEnabled(true);
				}
				
			}
		});
		
	    
	    toolBar = getToolBar();
	    add(toolBar, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public static synchronized TrackTypeList getInstance(){
		if(distribList == null){
			distribList = new TrackTypeList();
		}
		return distribList;
	}

	private JToolBar getToolBar() {
		JToolBar toolBar = new JToolBar();
		
		JButton addButton = new JButton();
		addButton.setIcon(IconFactory.ADD32_ICON);
		addButton.setToolTipText("Add track type");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TrackType trackType = new TrackType();
				JDialog pf = new TrackTypeForm(window, trackType, TrackTypeList.this);
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
				TrackType trackType = (TrackType) list.getModel().getElementAt(list.getSelectedIndex());
				JDialog pf = new TrackTypeForm(window, trackType, TrackTypeList.this);
				pf.setVisible(true);
			}
		});
		toolBar.add(editButton);
		
		delButton = new JButton();
		delButton.setIcon(IconFactory.DELETE32_ICON);
		delButton.setToolTipText("Delete Distributor");
		delButton.setFocusable(false);
		delButton.setEnabled(false);
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		toolBar.add(delButton);
		
		return toolBar;
	}

	@Override
	public void saveTrackType(TrackType type) {
		if(type.getId() > 0){
			ttm.updateTrack(type);
			System.out.println("Product updated");
		}else{
			ttm.saveNewTrack(type);
			model.addElement(type);
			System.out.println("Product saved");
		}
	}
	
}
