package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.border.EtchedBorder;
import javax.swing.table.TableModel;
import javax.swing.JSplitPane;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

import ua.mamamusic.accountancy.AbstractJDialog;
import ua.mamamusic.accountancy.DistributerAliasListListener;
import ua.mamamusic.accountancy.DistributorsListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorAlias;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.ReportPeriod;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.SystemColor;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class DistributorForm extends AbstractJDialog implements DistributerAliasListListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Distributor distributor;
	private JTextField txtName;
	private JLabel lblProductname;
	private JTextField txtColumnCount;
	private JTextField txtColumnPrice;
	private JList<DistributorAlias> aliasList;
	private GenericListModel<DistributorAlias> model;
	private DistributorsListListener listener;
	private JTextField txtArtist;
	private JTextField txtTrack;
	private JTextField txtType;
	private JTextField txtColumnquantity;
	private JTextField txtAuthor;
	private JTextField txtRelated;
	private JTextField txtRelatedColumn;
	private JLabel lblTotal;
	private JLabel lblRelatedColumn;
	private JComboBox<String> comboBoxIncome;
	private JComboBox<ReportPeriod> comboBoxReportPeriod;

	public DistributorForm(Window owner, String title, ModalityType modalityType, Distributor distributor, DistributorsListListener listener) {
		super(owner, title, modalityType);
		setPreferredSize(new Dimension(600, 440));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.distributor = distributor;
		this.listener = listener;
		init();
		populateForm();
	}

	private void populateForm(){		
		txtName.setText(distributor.getName());
		comboBoxReportPeriod.setModel(new DefaultComboBoxModel<>(new ReportPeriod[] {ReportPeriod.MONTH, ReportPeriod.QUARTER}));
		
		if(distributor != null && distributor.getId() > 0){
			lblProductname.setText(distributor.getName());
			txtColumnCount.setText(String.valueOf(distributor.getColumnCount() + 1));
			txtColumnPrice.setText(String.valueOf(distributor.getColumnPrice() + 1));
			txtArtist.setText(String.valueOf(distributor.getColumnArtist() + 1));
			txtTrack.setText(String.valueOf(distributor.getColumnTrack() + 1));
			txtType.setText(String.valueOf(distributor.getColumnTrackType() + 1));
			txtColumnquantity.setText(String.valueOf(distributor.getColumnQuantity() + 1));
			txtAuthor.setText(String.valueOf(distributor.getAuthorRights()));
			txtRelated.setText(String.valueOf(distributor.getRelatedRights()));
			
			txtRelatedColumn.setText(String.valueOf(distributor.getColumnRelatedIncome() + 1));
			comboBoxIncome.setSelectedIndex(distributor.getColumnIncomeType());
			repaintIncomeGroup();
			if(distributor.getAliasSet() != null){
				model = new GenericListModel<>(new ArrayList<>(distributor.getAliasSet()));
			}else{
				model = new GenericListModel<>(new ArrayList<DistributorAlias>());
			}
			aliasList.setModel(model);;
			
			comboBoxReportPeriod.setSelectedItem(distributor.getReportPeriod());;
//			if(distributor.getReportPeriod() != null){
//				switch(distributor.getReportPeriod()){
//				case MONTH:
//					comboBoxReportPeriod.setSelectedIndex(0);
//					break;
//				case QUARTER:
//					comboBoxReportPeriod.setSelectedIndex(1);
//					break;
//				}
//			}
			
		}else{
			lblProductname.setText("New Distributor");
		}
	}
	
	private void repaintIncomeGroup(){
		if(comboBoxIncome.getSelectedIndex() == 0){
			lblRelatedColumn.setVisible(false);
			txtRelatedColumn.setVisible(false);
			lblTotal.setText("Total:   ");
		}else{
			lblRelatedColumn.setVisible(true);
			txtRelatedColumn.setVisible(true);
			lblTotal.setText("Author:  ");
		}
	}
	
	/**
	 * Create the dialog.
	 */
	private void init() {
		setBounds(100, 100, 550, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTop = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelTop.getLayout();
			flowLayout.setAlignment(FlowLayout.LEADING);
			contentPanel.add(panelTop, BorderLayout.NORTH);
			{
				JLabel lblIcondistributer = new JLabel(IconFactory.DISTRIBUTOR48_ICON);
				panelTop.add(lblIcondistributer);
			}
			{
				JLabel lblProductForm = new JLabel("Distributor:");
				lblProductForm.setFont(new Font("Tahoma", Font.BOLD, 14));
				panelTop.add(lblProductForm);
			}
			
			lblProductname = new JLabel("ProductName");
			lblProductname.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panelTop.add(lblProductname);
		}
		{
			
			{
				JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
				contentPanel.add(tabbedPane, BorderLayout.CENTER);
				{
					JPanel panelMain = new JPanel();
					tabbedPane.addTab("Main", null, panelMain, null);
					panelMain.setLayout(null);
					
					JLabel lblName = new JLabel("Name:");
					lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
					lblName.setBounds(10, 27, 59, 14);
					panelMain.add(lblName);
				
					
					txtName = new JTextField();
					txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtName.setBounds(129, 22, 191, 25);
					panelMain.add(txtName);
					txtName.setColumns(10);
					
					JLabel lblReportPeriod = new JLabel("Report period:");
					lblReportPeriod.setFont(new Font("Tahoma", Font.PLAIN, 16));
					lblReportPeriod.setBounds(10, 72, 110, 25);
					panelMain.add(lblReportPeriod);
					
					comboBoxReportPeriod = new JComboBox<ReportPeriod>();
					comboBoxReportPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
					comboBoxReportPeriod.setBounds(129, 72, 191, 24);
					panelMain.add(comboBoxReportPeriod);
				}
				{
					JPanel panelAlias = new JPanel();
					tabbedPane.addTab("Alias", null, panelAlias, null);
					panelAlias.setLayout(new BorderLayout(0, 0));
					
					JToolBar toolBar = new JToolBar();
					panelAlias.add(toolBar, BorderLayout.NORTH);
					
					JButton btnAdd = new JButton(IconFactory.ADD24_ICON);
					btnAdd.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							DistributorAliasForm dialog = new DistributorAliasForm(DistributorForm.this.getOwner(), "", new DistributorAlias(), DistributorForm.this);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						}
					});
					
					toolBar.add(btnAdd);
					
					JButton btnEdit = new JButton(IconFactory.EDIT24_ICON);
					btnEdit.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							DistributorAlias alias = aliasList.getSelectedValue();
							if(alias != null){
								DistributorAliasForm dialog = new DistributorAliasForm(DistributorForm.this.getOwner(), "", alias, DistributorForm.this);
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
								dialog.setVisible(true);
							}
						}
					});
					
					toolBar.add(btnEdit);
					
					JButton btnRemove = new JButton(IconFactory.DELETE24_ICON);
					toolBar.add(btnRemove);
					
					aliasList = new JList<DistributorAlias>();
					aliasList.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.LIGHT_GRAY));
					panelAlias.add(aliasList);
					
				}
				{
					JPanel panelCharacteristic = new JPanel();
					tabbedPane.addTab("Characteristic", null, panelCharacteristic, null);
					panelCharacteristic.setLayout(null);
					
					JLabel lblColumnCount = new JLabel("Column count");
					lblColumnCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblColumnCount.setBounds(10, 11, 120, 20);
					panelCharacteristic.add(lblColumnCount);
					
					txtColumnCount = new JTextField();
					txtColumnCount.setBounds(140, 13, 86, 20);
					panelCharacteristic.add(txtColumnCount);
					txtColumnCount.setColumns(10);
					
					JLabel lblArtistColumn = new JLabel("Artist Column");
					lblArtistColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblArtistColumn.setBounds(10, 73, 120, 20);
					panelCharacteristic.add(lblArtistColumn);
					
					txtArtist = new JTextField();
					txtArtist.setColumns(10);
					txtArtist.setBounds(140, 75, 86, 20);
					panelCharacteristic.add(txtArtist);
					
					JLabel lblTrackColumn = new JLabel("Track Column");
					lblTrackColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblTrackColumn.setBounds(10, 106, 120, 20);
					panelCharacteristic.add(lblTrackColumn);
					
					txtTrack = new JTextField();
					txtTrack.setColumns(10);
					txtTrack.setBounds(140, 108, 86, 20);
					panelCharacteristic.add(txtTrack);
					
					JLabel lblTrackType = new JLabel("Track type Column");
					lblTrackType.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblTrackType.setBounds(10, 137, 120, 20);
					panelCharacteristic.add(lblTrackType);
					
					txtType = new JTextField();
					txtType.setColumns(10);
					txtType.setBounds(140, 139, 86, 20);
					panelCharacteristic.add(txtType);
					
					txtColumnquantity = new JTextField();
					txtColumnquantity.setColumns(10);
					txtColumnquantity.setBounds(140, 44, 86, 20);
					panelCharacteristic.add(txtColumnquantity);
					
					JLabel lblColumnQuantity = new JLabel("Column quantity");
					lblColumnQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblColumnQuantity.setBounds(10, 42, 120, 20);
					panelCharacteristic.add(lblColumnQuantity);
					
					JPanel panel = new JPanel();
					panel.setBorder(new TitledBorder(null, "Income Columns", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel.setBounds(264, 11, 216, 105);
					panelCharacteristic.add(panel);
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					
					Box horizontalBox = Box.createHorizontalBox();
					panel.add(horizontalBox);
					
					comboBoxIncome = new JComboBox<String>();
					comboBoxIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
					horizontalBox.add(comboBoxIncome);
					comboBoxIncome.setModel(new DefaultComboBoxModel<String>(new String[] {"Total (Combined)", "Separated"}));
					comboBoxIncome.addItemListener(new ItemListener() {
					     @Override
					     public void itemStateChanged(ItemEvent e) {
					    	 if (e.getStateChange() == ItemEvent.SELECTED) {
					             repaintIncomeGroup();
					          }
					     }
					 });
					
					Component rigidArea = Box.createRigidArea(new Dimension(20, 5));
					panel.add(rigidArea);
					
					Box horizontalBox_1 = Box.createHorizontalBox();
					panel.add(horizontalBox_1);
					
					lblTotal = new JLabel("Total:    ");
					lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
					horizontalBox_1.add(lblTotal);
					
					txtColumnPrice = new JTextField();
					txtColumnPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
					horizontalBox_1.add(txtColumnPrice);
					txtColumnPrice.setColumns(10);
					
					Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 5));
					panel.add(rigidArea_1);
					
					Box horizontalBox_2 = Box.createHorizontalBox();
					panel.add(horizontalBox_2);
					
					lblRelatedColumn = new JLabel("Related:");
					lblRelatedColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					horizontalBox_2.add(lblRelatedColumn);
					
					txtRelatedColumn = new JTextField();
					txtRelatedColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					horizontalBox_2.add(txtRelatedColumn);
					txtRelatedColumn.setColumns(10);
					
				}
				{
					JPanel panelRights = new JPanel();
					tabbedPane.addTab("Rights", null, panelRights, null);
					panelRights.setLayout(null);
					
					JPanel panel = new JPanel();
					panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Rights, Total 100%", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel.setBounds(10, 11, 130, 73);
					panelRights.add(panel);
					panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
					
					Box verticalBox = Box.createVerticalBox();
					panel.add(verticalBox);
					
					Box horizontalBox_1 = Box.createHorizontalBox();
					verticalBox.add(horizontalBox_1);
					
					JLabel lblAuthor = new JLabel("Author:");
					horizontalBox_1.add(lblAuthor);
					lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
					
					Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
					horizontalBox_1.add(rigidArea);
					
					txtAuthor = new JTextField();
					horizontalBox_1.add(txtAuthor);
					txtAuthor.setColumns(10);
					
					JLabel label = new JLabel("%");
					horizontalBox_1.add(label);
					
					Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 10));
					verticalBox.add(rigidArea_2);
					
					Box horizontalBox = Box.createHorizontalBox();
					verticalBox.add(horizontalBox);
					
					JLabel lblRelated = new JLabel("Related:");
					lblRelated.setFont(new Font("Tahoma", Font.PLAIN, 14));
					horizontalBox.add(lblRelated);
					
					Component rigidArea_1 = Box.createRigidArea(new Dimension(8, 20));
					horizontalBox.add(rigidArea_1);
					
					txtRelated = new JTextField();
					horizontalBox.add(txtRelated);
					txtRelated.setColumns(10);
					
					JLabel label_1 = new JLabel("%");
					horizontalBox.add(label_1);
				}
			}
		}
		
		
		{
		JTextArea txtrComment = new JTextArea();
		txtrComment.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtrComment.setMaximumSize(new Dimension(4, 16));
		//contentPanel.add(txtrComment, BorderLayout.SOUTH);
		txtrComment.setMargin(new Insets(10, 10, 2, 2));
		txtrComment.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtrComment.setRows(3);
		
			JScrollPane scrollPaneComment = new JScrollPane();
			scrollPaneComment.setBorder(new EmptyBorder(5, 0, 0, 0));
			scrollPaneComment.setViewportView(txtrComment);
			
			contentPanel.add(scrollPaneComment, BorderLayout.SOUTH);
		}
		{
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK", IconFactory.OK_ICON);
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(txtName.getText() == ""){
							JOptionPane.showMessageDialog(null, "You can't save empty name");
						}else{
							try {
								pushDataToProduct();
								listener.saveDistributor(distributor);
								dispose();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(DistributorForm.this,
									    "Something goes wrong. Please check form data for correctness.",
									    "Warning",
									    JOptionPane.WARNING_MESSAGE);
								e.printStackTrace();
							}
							
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel", IconFactory.CANCEL_ICON);
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void pushDataToProduct() throws Exception{
			distributor.setName(txtName.getText());
			distributor.setColumnCount(Integer.valueOf(txtColumnCount.getText()) - 1);
			distributor.setColumnPrice(Integer.valueOf(txtColumnPrice.getText()) - 1);
			distributor.setColumnArtist(Integer.valueOf(txtArtist.getText()) - 1);
			distributor.setColumnTrack(Integer.valueOf(txtTrack.getText()) - 1);
			distributor.setColumnTrackType(Integer.valueOf(txtType.getText()) - 1);
			distributor.setColumnQuantity(Integer.valueOf(txtColumnquantity.getText()) - 1);
			distributor.setAuthorRights(Integer.valueOf(txtAuthor.getText()));
			distributor.setRelatedRights(Integer.valueOf(txtRelated.getText()));
			distributor.setColumnIncomeType(comboBoxIncome.getSelectedIndex());
			
			if(comboBoxReportPeriod.getSelectedItem() == null) throw new Exception();
			distributor.setReportPeriod(comboBoxReportPeriod.getItemAt(comboBoxReportPeriod.getSelectedIndex()));
			
			if(comboBoxIncome.getSelectedIndex() == 1){
				distributor.setColumnRelatedIncome(Integer.parseInt(txtRelatedColumn.getText()) - 1);
			}
			
			Set<DistributorAlias> set = new HashSet<DistributorAlias>();
			if(model != null){
				set.addAll(model.getList());
			}
			distributor.setAliasSet(set);
	
	}

	@Override
	public void saveAlias(DistributorAlias alias) {
		if(alias != null && alias.getId() < 1){
			//GenericListModel<DistributorAlias> model = (GenericListModel<DistributorAlias>)aliasList.getModel();
			alias.setDistributor(distributor);
			model.addElement(alias);
		}else{
			model.repaintObjectInList(alias);
		}
	}
}
