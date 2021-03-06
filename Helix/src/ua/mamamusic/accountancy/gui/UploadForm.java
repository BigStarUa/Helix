package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.ReadExcel;
import ua.mamamusic.accountancy.UploadFormListener;
import ua.mamamusic.accountancy.XMLParser;
import ua.mamamusic.accountancy.XlsRule;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackAlias;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.model.TrackTypeAlias;
import ua.mamamusic.accountancy.model.UploadListTableCellEditor;
import ua.mamamusic.accountancy.model.UploadListTableCellRenderer;
import ua.mamamusic.accountancy.model.UploadListTableModel;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.DataRowManager;
import ua.mamamusic.accountancy.session.DataRowManagerImpl;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;
import ua.mamamusic.accountancy.session.TrackTypeManager;
import ua.mamamusic.accountancy.session.TrackTypeManagerImpl;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Component;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePanel;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import java.awt.GridLayout;

public class UploadForm extends AbstractJPanel implements UploadFormListener{
	
	private static final long serialVersionUID = 1L;
	private static UploadForm uploadList;
	private JTable table;
	private TableRowSorter<TableModel> sorter;
	private JToolBar toolBar;
	private JFileChooser fc;
	private JButton addButton;
	private JComboBox comboBoxDistributors;
	private File file;
	private JLabel lblFileName;
	private JButton btnStart;
	private JToolBar toolBar_bottom;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblLoadingicon_1;
	private JTextField filterText;
	private JLabel lblDate;
	private List<Artist> artistList;
	private JButton btnExport;
	private List<TrackType> typeList;
	private JPanel panel_3;
	private JLabel lblFilter;
	private JPanel panel_4;
	private JPanel panel_5;
	private JDatePicker datePanel;
	private JPanel panel_8;
	private JToolBar toolBar_1;
	private enum MonthDay {FIRST, LAST}

	public UploadForm(){
		setLayout(new BorderLayout());
		//window = SwingUtilities.getWindowAncestor(UploadForm.this);
		
		DistributorManager pm = new DistributorManagerImpl();
		List<Distributor> list = pm.loadAllProductsOrderedBy("name");
		
		ArtistManager am = new ArtistManagerImpl();
		artistList = am.loadAllArtistsOrderedBy("name");
		
		TrackTypeManager ttm = new TrackTypeManagerImpl();
		typeList = ttm.loadAllTrackTypesOrderedBy("name");
		
		FileFilter ff = new FileNameExtensionFilter("Excel Files", "xls");
		//Create file chooser
		fc = new JFileChooser();
		fc.addChoosableFileFilter(ff);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(ff);
		
		//Creating table
		table = new JTable();
//		table.setGridColor(Color.LIGHT_GRAY);
//		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		table.setFillsViewportHeight(true);
//		table.setRowHeight(20);
//		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.setDefaultEditor(Object.class, new UploadListTableCellEditor(this));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if(row > -1 && col > -1 && e.getButton() == MouseEvent.BUTTON1){
				}
			}
		});

		List<ArtistAlias> aliasList = am.loadAllArtistAliases();
		
		
		table.setDefaultRenderer(Object.class, new UploadListTableCellRenderer(aliasList));
		table.setRowHeight(30);
		
		sorter = new TableRowSorter<TableModel>();
		table.setRowSorter(sorter);
		
	    toolBar = getToolBar();
	    add(toolBar, BorderLayout.NORTH);
	    
	    
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		toolBar_bottom = new JToolBar();
		add(toolBar_bottom, BorderLayout.SOUTH);
		
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		
		
		panel_4 = new JPanel();
		toolBar.add(panel_4);
		AbstractFormatter ab = new AbstractFormatter() {
			@Override
			public String valueToString(Object value) throws ParseException {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Date date = new Date();
				if(value != null) {
					date = ((Calendar)value).getTime();
					return sdf.format(date);
				}
				return "...";
			}
			
			@Override
			public Object stringToValue(String text) throws ParseException {
				return null;
			}
		};
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel_8 = new JPanel();
		panel_4.add(panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		
		
		lblDate = new JLabel("Date:");
		panel_8.add(lblDate);
				JDatePanelImpl pan = new JDatePanelImpl(null);
				datePanel = new JDatePickerImpl(pan, ab);
				DateModel<Calendar> mod = (DateModel<Calendar>) datePanel.getModel();
				mod.setValue(Calendar.getInstance());
				panel_8.add((Component)datePanel);
				
				JPanel panel_9 = new JPanel();
				panel_4.add(panel_9);
				panel_9.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
				
				JLabel lblDistributor_1 = new JLabel("Distributor:");
				panel_9.add(lblDistributor_1);
				
				
				comboBoxDistributors = new JComboBox(new MyComboBoxModel(list));
				panel_9.add(comboBoxDistributors);
				comboBoxDistributors.setPreferredSize(new Dimension(150, 25));
				comboBoxDistributors.setMaximumSize(new Dimension(200, 32767));
		
		
		
		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		toolBar.add(panel_2);

		
		btnStart = new JButton("Start", IconFactory.START32_ICON);
		panel_2.add(btnStart);
		btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		

		panel_3 = new JPanel();
		panel.add(panel_3);
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEADING);
		flowLayout_2.setVgap(8);
		
		lblFilter = new JLabel("Filter:");
		panel_3.add(lblFilter);
		
		filterText = new JTextField();
		filterText.setColumns(20);
		panel_3.add(filterText);
		
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
					}
				});
				
		lblLoadingicon_1 = new JLabel(IconFactory.LOADING32_ICON);
		panel_3.add(lblLoadingicon_1);
		lblLoadingicon_1.setVisible(false);	
		
		panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		panel.add(panel_5);
		
		btnExport = new JButton("Export", IconFactory.EXPORT32_ICON);
		panel_5.add(btnExport);
		btnExport.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TableModel model = table.getModel();
				int count = model.getRowCount();
				List<DataRow> list = new LinkedList<DataRow>();
				Date date = ((Calendar)datePanel.getModel().getValue()).getTime();
				DataRowManager drm = new DataRowManagerImpl();
				
				Date start = getFirstDateOfCurrentMonth(date, MonthDay.FIRST);
				Date end = getFirstDateOfCurrentMonth(date, MonthDay.LAST);
				Distributor dist = (Distributor) comboBoxDistributors.getSelectedItem();
				List<DataRow> existingList = drm.loadAllDataRowsByPeriod(start, end, dist);
				
				if(existingList != null && existingList.size() > 0){
					JOptionPane.showMessageDialog(UploadForm.this,
						    "Data from (" + start + ") till (" + end + ") already exist!",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);

				}else{
				
					for(int i = 0; i < count; i++){
						DataRow row = (DataRow)model.getValueAt(i, -1);
						if(row.isValid()){
							row.setDate(date);
							list.add(row);
						}
					}
					drm.saveNewDataRowList(list);
					JOptionPane.showMessageDialog(UploadForm.this,
						    list.size() + " row of data inserted in database",
						    "Done",
						    JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(file == null){
					JOptionPane.showMessageDialog(UploadForm.this,
						    "Select file",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}else{
					if(comboBoxDistributors.getSelectedItem() != null){
						Distributor dist = (Distributor) comboBoxDistributors.getSelectedItem();
		                ReadExcel rx = new ReadExcel(file, table, lblLoadingicon_1, sorter, dist,
		                		artistList, typeList, UploadForm.this);
		                rx.setInputFile(file.getAbsolutePath());
						rx.execute(); 
					}else{
						JOptionPane.showMessageDialog(UploadForm.this,
							    "Select distributor first",
							    "Warning",
							    JOptionPane.WARNING_MESSAGE);
					}
				}
		    }
		});
		
	}
	
	public static synchronized UploadForm getInstance(){
		if(uploadList == null){
			uploadList = new UploadForm();
		}
		return uploadList;
	}
	
	private Date getFirstDateOfCurrentMonth(Date date, MonthDay monthDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch(monthDay){
			case FIRST:
				cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
				break;
			case LAST:
				cal.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
				break;
		}
		return cal.getTime();
	}

	private JToolBar getToolBar() {
		toolBar_1 = new JToolBar();
		toolBar_1.setFloatable(false);
		if(table.getSelectedRow() > -1){
			//editButton.setEnabled(true);
	    }
		if(table.getSelectedRow() > -1){
			//editButton.setEnabled(true);
	    }
		
		panel_1 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_1.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEADING);
		toolBar_1.add(panel_1);
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		addButton = new JButton();
		addButton.setText("Open file");
		addButton.setMargin(new Insets(2, 5, 2, 5));
		addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(addButton);
		addButton.setIcon(IconFactory.OPEN_FILE32_ICON);
		addButton.setToolTipText("Browse file");
		addButton.setFocusable(false);
		
		lblFileName = new JLabel("Empty");
		lblFileName.setMaximumSize(new Dimension(30, 32));
		panel_1.add(lblFileName);
		lblFileName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFileName.setPreferredSize(new Dimension(150, 32));
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == addButton) {
		            int returnVal = fc.showOpenDialog(UploadForm.this);
		 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                file = fc.getSelectedFile();
		                lblFileName.setText(file.getName());
		            }
		        }
			}
		});
		
		
		
		return toolBar_1;
	}
	
	private Artist matchArtist(String artist){
		for(Artist art : artistList){
			if(art.getAliasSet() == null) continue;
			for(ArtistAlias alias : art.getAliasSet()){
				if(alias.getName().equalsIgnoreCase(artist.trim())){
					return art;
				}
			}
		}
		return null;
	}
	
	private Track matchTrack(Artist artist, String track){

		if(artist == null) return null;
		if(artist.getTrackSet() == null) return null;
		for(Track t : artist.getTrackSet()){
			if(t.getAliasSet() == null) continue;
			for(TrackAlias alias : t.getAliasSet()){
				if(alias.getName().equalsIgnoreCase(track.trim())){
					return t;
				}
			}
		}
		return null;
	}
	
	private TrackType matchType(String type){
		for(TrackType t : typeList){
			if(t.getTypeSet() == null) continue;
			for(TrackTypeAlias alias : t.getTypeSet()){
				if(alias.getName().equalsIgnoreCase(type.trim())){
					return t;
				}
			}
		}
		return null;
	}
	
	@Override
	public void fireTableChanged() {
		UploadListTableModel model = (UploadListTableModel)table.getModel();
		
		for(int i=0; i < model.getRowCount(); i++){
			DataRow prod = (DataRow) model.getValueAt(i, -1);
			prod.setArtist(matchArtist(prod.getColumnArtist()));
			if(prod.getArtist() != null){
				prod.setTrack(matchTrack(prod.getArtist(), prod.getColumnTrack()));
			}
			prod.setType(matchType(prod.getColumnTrackType()));
		}
		table.repaint();
		table.revalidate();
	}
	
	
	

class MyComboBoxModel extends AbstractListModel<Distributor> implements ComboBoxModel<Distributor> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Distributor> list;
	Distributor selected = null;
  String selection = null;

  
  public MyComboBoxModel(List<Distributor> distribList){
	  this.list = distribList;
  }
  
  public Distributor getElementAt(int index) {
    //return ComputerComps[index];
    return list.get(index);
  }

  public int getSize() {
    //return ComputerComps.length;
	  return list.size();
  }

  public void setSelectedItem(Object anItem) {
    selection = ((Distributor) anItem).getName(); // to select and register an
    selected = (Distributor) anItem;
  } // item from the pull-down list

  // Methods implemented from the interface ComboBoxModel
  public Object getSelectedItem() {
	  return selected;
    //return selection; // to add the selection to the combo box
  }
}
}
