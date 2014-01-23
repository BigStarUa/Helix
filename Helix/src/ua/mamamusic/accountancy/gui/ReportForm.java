package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.WriteExcel;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.ExternalReportTableModel;
import ua.mamamusic.accountancy.model.GenericComboBoxModel;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.ProductRow;
import ua.mamamusic.accountancy.model.ReportTableModel;
import ua.mamamusic.accountancy.model.TRightType;
import ua.mamamusic.accountancy.model.ProductRowTableModel;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.ProductRowManager;
import ua.mamamusic.accountancy.session.ProductRowManagerImpl;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;
import ua.mamamusic.accountancy.session.TrackTypeManager;
import ua.mamamusic.accountancy.session.TrackTypeManagerImpl;

import javax.swing.AbstractButton;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import javax.swing.JCheckBox;

import jxl.write.WriteException;

import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;

import java.awt.Rectangle;
import java.awt.Font;

public class ReportForm extends AbstractJPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JList<Artist> list;
	private JComboBox<Distributor> distributorComboBox;
	private JComboBox<TrackType> typeComboBox;
	private List<Artist> artistList;
	private List<TrackType> typeList;
	private JDatePickerImpl dateStart;
	private JDatePickerImpl dateEnd;
	private JCheckBox chckbxGroupDistrib;
	private JCheckBox chckbxGroupType;
	private JFileChooser fc;
	private JCheckBox chckbxGroupDate;
	private JCheckBox chckbxGroupRights;
	private JComboBox comboBoxRights;
	private JLabel lblTotalsum;

	/**
	 * Create the dialog.
	 */
	public ReportForm() {
		build();
		init();
	}
	
	private void init() {
		
		DistributorManager pm = new DistributorManagerImpl();
		List<Distributor> distribList = new ArrayList<Distributor>(pm.loadAllProductsOrderedBy("name"));
		
		ArtistManager am = new ArtistManagerImpl();
		artistList = new ArrayList<Artist>(am.loadAllArtistsOrderedBy("name"));
		
		TrackTypeManager ttm = new TrackTypeManagerImpl();
		typeList = new ArrayList<TrackType>(ttm.loadAllTrackTypesOrderedBy("name"));
		
		list.setModel(new GenericListModel<Artist>(artistList));
		list.setSelectionModel(new DefaultListSelectionModel(){
		    private static final long serialVersionUID = 1L;

		    boolean gestureStarted = false;

		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if(!gestureStarted){
		            if (isSelectedIndex(index0)) {
		                super.removeSelectionInterval(index0, index1);
		            } else {
		                super.addSelectionInterval(index0, index1);
		            }
		        }
		        gestureStarted = true;
		    }

		    @Override
		    public void setValueIsAdjusting(boolean isAdjusting) {
		        if (isAdjusting == false) {
		            gestureStarted = false;
		        }
		    }

		});
		
		Distributor fakeDistrib = new Distributor();
		fakeDistrib.setName("Select all");
		distribList.add(0, fakeDistrib);
		distributorComboBox.setModel(new GenericComboBoxModel<>(distribList));
		distributorComboBox.setSelectedIndex(0);
		distributorComboBox.setEnabled(false);
		
		TrackType fakeType = new TrackType();
		fakeType.setName("Select all");
		typeList.add(0, fakeType);
		typeComboBox.setModel(new GenericComboBoxModel<>(typeList));
		typeComboBox.setSelectedIndex(0);
		typeComboBox.setEnabled(false);
		
		typeComboBox.setSelectedIndex(0);
		typeComboBox.setEnabled(false);
	}
	
	private void build() {
		setBounds(100, 100, 650, 640);
		setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel.setBackground(Color.WHITE);
			panel.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.setPreferredSize(new Dimension(250, 10));
			panel.setMinimumSize(new Dimension(50, 10));
			contentPanel.add(panel, BorderLayout.WEST);
			panel.setLayout(null);
			
			JPanel panel_5 = new JPanel();
			panel_5.setBackground(Color.WHITE);
			panel_5.setBorder(new TitledBorder(null, "Artist", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_5.setBounds(10, 11, 230, 211);
			panel.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
			
			list = new JList<Artist>();
			list.setFont(new Font("Tahoma", Font.PLAIN, 14));
			list.setBorder(null);
			list.setBounds(10, 11, 230, 187);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.WHITE);
			scrollPane.setLocation(79, 11);
			scrollPane.setSize(161, 136);
			scrollPane.setViewportView(list);
			
			panel_5.add(scrollPane);
			
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			toolBar.setBackground(Color.WHITE);
			panel_5.add(toolBar, BorderLayout.NORTH);
			
			JButton btnSelectall = new JButton(IconFactory.CHECKBOX_CHECKED16_ICON);
			btnSelectall.setFocusable(false);
			btnSelectall.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					list.setSelectionInterval(0, list.getModel().getSize()-1);
				}
			});
			toolBar.add(btnSelectall);
			
			JButton btnDeselectall = new JButton(IconFactory.CHECKBOX_UNCHECKED16_ICON);
			btnDeselectall.setFocusable(false);
			btnDeselectall.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					list.clearSelection();
				}
			});
			toolBar.add(btnDeselectall);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.WHITE);
			panel_1.setBorder(new TitledBorder(null, "Distributor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(10, 233, 230, 46);
			panel.add(panel_1);
			panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
			
			distributorComboBox = new JComboBox<Distributor>();
			
			
			chckbxGroupDistrib = new JCheckBox("Group");
			chckbxGroupDistrib.setBackground(Color.WHITE);
			chckbxGroupDistrib.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					  AbstractButton abstractButton = (AbstractButton) evt.getSource();
				        boolean selected = abstractButton.getModel().isSelected();
				        if(selected){
				        	distributorComboBox.setEnabled(true);
				        }else{
				        	distributorComboBox.setEnabled(false);
				        }
				}
			});
			panel_1.add(chckbxGroupDistrib);
			panel_1.add(distributorComboBox);
			
			JPanel panel_4 = new JPanel();
			panel_4.setBackground(Color.WHITE);
			panel_4.setBorder(new TitledBorder(null, "Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_4.setBounds(10, 290, 230, 46);
			panel.add(panel_4);
			panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
			
			typeComboBox = new JComboBox<TrackType>();
			
			
			chckbxGroupType = new JCheckBox("Group");
			chckbxGroupType.setBackground(Color.WHITE);
			chckbxGroupType.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					  AbstractButton abstractButton = (AbstractButton) evt.getSource();
				        boolean selected = abstractButton.getModel().isSelected();
				        if(selected){
				        	typeComboBox.setEnabled(true);
				        }else{
				        	typeComboBox.setEnabled(false);
				        }
				}
			});
			panel_4.add(chckbxGroupType);
			panel_4.add(typeComboBox);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(Color.WHITE);
			panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Date", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBounds(10, 347, 230, 102);
			panel.add(panel_2);
			
			
			AbstractFormatter ab = new AbstractFormatter() {
				@Override
				public String valueToString(Object value) throws ParseException {
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					Date date = new Date();
					if(value != null) {
						date = ((Calendar)value).getTime();
						return sdf.format(date);
					}
					return "";
				}
				
				@Override
				public Object stringToValue(String text) throws ParseException {
					return null;
				}
			};
			panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
			
			Box horizontalBox_2 = Box.createHorizontalBox();
			panel_2.add(horizontalBox_2);
			
			chckbxGroupDate = new JCheckBox("Group");
			chckbxGroupDate.setVerticalAlignment(SwingConstants.TOP);
			chckbxGroupDate.setBackground(Color.WHITE);
			horizontalBox_2.add(chckbxGroupDate);
			
			Component horizontalGlue = Box.createHorizontalGlue();
			horizontalBox_2.add(horizontalGlue);
			
			Box horizontalBox = Box.createHorizontalBox();
			panel_2.add(horizontalBox);
			
			JDatePanelImpl pan = new JDatePanelImpl(null);
			
			JLabel lblFrom = new JLabel("From:");
			horizontalBox.add(lblFrom);
			dateStart = new JDatePickerImpl(pan, ab);
			horizontalBox.add((Component)dateStart);
			
			Component rigidArea = Box.createRigidArea(new Dimension(20, 5));
			panel_2.add(rigidArea);
			
			Box horizontalBox_1 = Box.createHorizontalBox();
			panel_2.add(horizontalBox_1);
			
			
			JDatePanelImpl pan2 = new JDatePanelImpl(null);
			
			JLabel lblTill = new JLabel("Till:   ");
			horizontalBox_1.add(lblTill);
			dateEnd = new JDatePickerImpl(pan2, ab);
			horizontalBox_1.add((Component)dateEnd);
			
			JButton btnGetData = new JButton("Report", IconFactory.TABLES32_ICON);
			btnGetData.setBounds(139, 517, 101, 35);
			btnGetData.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					
					List<Artist> artList = list.getSelectedValuesList();
					String incomeString = "Income";
					Distributor distr = null;
					TrackType type = null;
					TRightType rightType = null;
					Artist[] array = artList.toArray(new Artist[artList.size()]);
					List<String> columnNameList = new ArrayList<String>();
					columnNameList.add("Artist");
					columnNameList.add("Track");
					
					
					try{
						Date start = ((Calendar)dateStart.getModel().getValue()).getTime();
						Date end = ((Calendar)dateEnd.getModel().getValue()).getTime();
						
						
						if(chckbxGroupType.isSelected()){
							if(typeComboBox.getSelectedItem() != null && typeComboBox.getSelectedIndex() > 0){
								type = (TrackType) typeComboBox.getSelectedItem();
							}
							columnNameList.add("TrackType");
						}
						if(chckbxGroupDistrib.isSelected()){
							if(distributorComboBox.getSelectedItem() != null && distributorComboBox.getSelectedIndex() > 0){
								distr = (Distributor) distributorComboBox.getSelectedItem();
							}
							columnNameList.add("Distributor");
						}
						columnNameList.add(incomeString);
						
						if(chckbxGroupRights.isSelected()){
							columnNameList.add("Rights");
							int index = comboBoxRights.getSelectedIndex();
							switch(index){
							case 0:
								break;
							case 1:
								rightType = TRightType.AUTHOR;
								break;
							case 2:
								rightType = TRightType.RELATED;
								break;
							}
						}
						if(chckbxGroupDate.isSelected()){
							columnNameList.add("Date");
						}
						
						ProductRowManager drm = new ProductRowManagerImpl();
						//List<Object[]> dataRowList = drm.loadDataRowsByCriterias(array, distr, type, start, end, chckbxGroupDistrib.isSelected(), chckbxGroupType.isSelected());
						List<Object[]> dataRowList = drm.loadData(array, distr, type, start, end, chckbxGroupDistrib.isSelected(), chckbxGroupType.isSelected(), chckbxGroupDate.isSelected(), chckbxGroupRights.isSelected(), rightType);
						//List<ProductRow> dataRowList = drm.loadAllDataRowsByPeriod(start, end, distr);
						
						TableModel model = new ReportTableModel(columnNameList, dataRowList);
						table.setModel(model);
						
						int incomeColumnIndex = columnNameList.indexOf(incomeString);
						double sum = 0;
						for(int i = 0; i < model.getRowCount(); i++){
							sum += (double)dataRowList.get(i)[incomeColumnIndex];
						}
						lblTotalsum.setText(String.format("%1$,.2f", sum));
						
					}catch(NullPointerException npe){
						JOptionPane.showMessageDialog(ReportForm.this,
							    "Select dates",
							    "Warning",
							    JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			});
			panel.add(btnGetData);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(Color.WHITE);
			panel_3.setBorder(new TitledBorder(null, "Rights", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_3.setBounds(10, 460, 230, 46);
			panel.add(panel_3);
			panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
			
			chckbxGroupRights = new JCheckBox("Group");
			chckbxGroupRights.setBackground(Color.WHITE);
			chckbxGroupRights.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					  AbstractButton abstractButton = (AbstractButton) evt.getSource();
				        boolean selected = abstractButton.getModel().isSelected();
				        if(selected){
				        	comboBoxRights.setEnabled(true);
				        }else{
				        	comboBoxRights.setEnabled(false);
				        }
				}
			});
			panel_3.add(chckbxGroupRights);
			
			comboBoxRights = new JComboBox();
			comboBoxRights.setModel(new DefaultComboBoxModel(new String[] {"Select all", "Author", "Related"}));
			comboBoxRights.setEnabled(false);
			panel_3.add(comboBoxRights);
			
			JButton btnExternalReport = new JButton("External report");
			btnExternalReport.setBounds(10, 517, 105, 35);
			btnExternalReport.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ProductRowManager drm = new ProductRowManagerImpl();
					List<Artist> artList = list.getSelectedValuesList();
					Distributor distr = null;
					Artist[] array = artList.toArray(new Artist[artList.size()]);
					Date start = ((Calendar)dateStart.getModel().getValue()).getTime();
					Date end = ((Calendar)dateEnd.getModel().getValue()).getTime();
					
					List<Object[]> dataRowList = drm.loadDataForExternalReport(array, distr, start, end);
					
					TableModel model = new ExternalReportTableModel(dataRowList);
					table.setModel(model);
					
					int incomeColumnIndex = 5;
					double sum = 0;
					for(int i = 0; i < model.getRowCount(); i++){
						sum += (double)dataRowList.get(i)[incomeColumnIndex];
					}
					lblTotalsum.setText(String.format("%1$,.2f", sum));
					
				}
			});
			panel.add(btnExternalReport);
		}
		{
			JToolBar toolBar = new JToolBar();
			contentPanel.add(toolBar, BorderLayout.NORTH);
		}
		
		table = new JTable();
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Save to Excel", IconFactory.EXPORT32_ICON);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						
						FileFilter ff = new FileNameExtensionFilter("Excel Files", "xls");
						//Create file chooser

						fc = new JFileChooser();
						fc.addChoosableFileFilter(ff);
						fc.setAcceptAllFileFilterUsed(false);
						fc.setFileFilter(ff);
						fc.setSelectedFile(new File("Report"));

						int returnVal = fc.showSaveDialog(ReportForm.this);
						
			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			                File file = fc.getSelectedFile();
						
						 WriteExcel test = new WriteExcel();
						    test.setOutputFile(file.getAbsolutePath() + ".xls");
						    test.setSourceTable(table);
						    try {
								test.write();
							} catch (WriteException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    JOptionPane.showMessageDialog(ReportForm.this,
								    "File "+ fc.getSelectedFile().getName() +".xls saved! ",
								    "Done!",
								    JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				panel.setBorder(new EmptyBorder(0, 0, 0, 30));
				buttonPane.add(panel);
				
				JLabel lblSum = new JLabel("Total sum:");
				panel.add(lblSum);
				
				lblTotalsum = new JLabel("");
				lblTotalsum.setFont(new Font("Tahoma", Font.BOLD, 11));
				panel.add(lblTotalsum);
				buttonPane.add(cancelButton);
			}
		}
	}
}
