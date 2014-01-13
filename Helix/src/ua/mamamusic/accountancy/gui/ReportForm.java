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
import ua.mamamusic.accountancy.model.GenericComboBoxModel;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.ReportTableModel;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.DataRowManager;
import ua.mamamusic.accountancy.session.DataRowManagerImpl;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;
import ua.mamamusic.accountancy.session.TrackTypeManager;
import ua.mamamusic.accountancy.session.TrackTypeManagerImpl;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JList;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import javax.swing.JCheckBox;

import jxl.write.WriteException;

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
	}
	
	private void build() {
		setBounds(100, 100, 650, 560);
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
			
			list = new JList<Artist>();
			list.setBorder(null);
			list.setBounds(10, 11, 230, 187);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.WHITE);
			scrollPane.setBorder(new TitledBorder(null, "Artists", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			scrollPane.setLocation(10, 11);
			scrollPane.setSize(230, 182);
			scrollPane.setViewportView(list);
			
			panel.add(scrollPane);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.WHITE);
			panel_1.setBorder(new TitledBorder(null, "Distributor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(10, 204, 230, 66);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			distributorComboBox = new JComboBox<Distributor>();
			panel_1.add(distributorComboBox, BorderLayout.SOUTH);
			
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
			panel_1.add(chckbxGroupDistrib, BorderLayout.NORTH);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(Color.WHITE);
			panel_2.setBorder(new TitledBorder(null, "Date from", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBounds(10, 342, 230, 46);
			panel.add(panel_2);
			panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
			
			
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
			
			JDatePanelImpl pan = new JDatePanelImpl(null);
			dateStart = new JDatePickerImpl(pan, ab);
			panel_2.add((Component)dateStart);
			
			
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(Color.WHITE);
			panel_3.setBorder(new TitledBorder(null, "Date till", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_3.setBounds(10, 389, 230, 46);
			panel.add(panel_3);
			panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
			
			JDatePanelImpl pan2 = new JDatePanelImpl(null);
			dateEnd = new JDatePickerImpl(pan2, ab);
			panel_3.add((Component)dateEnd);
			
			JPanel panel_4 = new JPanel();
			panel_4.setBackground(Color.WHITE);
			panel_4.setBorder(new TitledBorder(null, "Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_4.setBounds(10, 277, 230, 66);
			panel.add(panel_4);
			panel_4.setLayout(new BorderLayout(0, 0));
			
			typeComboBox = new JComboBox<TrackType>();
			panel_4.add(typeComboBox, BorderLayout.SOUTH);
			
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
			panel_4.add(chckbxGroupType, BorderLayout.NORTH);
			
			JButton btnGetData = new JButton("Get data", IconFactory.TABLES32_ICON);
			btnGetData.setBounds(65, 437, 117, 35);
			btnGetData.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					
					List<Artist> artList = list.getSelectedValuesList();
					Distributor distr = null;
					TrackType type = null;
					Artist[] array = artList.toArray(new Artist[artList.size()]);
					
					try{
						Date start = ((Calendar)dateStart.getModel().getValue()).getTime();
						Date end = ((Calendar)dateEnd.getModel().getValue()).getTime();
						if(chckbxGroupDistrib.isSelected() && distributorComboBox.getSelectedItem() != null && distributorComboBox.getSelectedIndex() > 0){
							distr = (Distributor) distributorComboBox.getSelectedItem();
						}
						
						if(chckbxGroupType.isSelected() && typeComboBox.getSelectedItem() != null && typeComboBox.getSelectedIndex() > 0){
							type = (TrackType) typeComboBox.getSelectedItem();
						}
						
						DataRowManager drm = new DataRowManagerImpl();
						List<Object[]> dataRowList = drm.loadDataRowsByCriterias(array, distr, type, start, end, chckbxGroupDistrib.isSelected(), chckbxGroupType.isSelected());
						
						TableModel model = new ReportTableModel(dataRowList);
						table.setModel(model);
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
		}
		{
			JToolBar toolBar = new JToolBar();
			contentPanel.add(toolBar, BorderLayout.NORTH);
		}
		
		table = new JTable();
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));
		contentPanel.add(table, BorderLayout.CENTER);
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
				buttonPane.add(cancelButton);
			}
		}
	}
}
