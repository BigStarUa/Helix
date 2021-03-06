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
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

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
	private JTextField txtColumnId;
	private JTextField txtColumnPrice;
	private JList<DistributorAlias> aliasList;
	private GenericListModel<DistributorAlias> model;
	private DistributorsListListener listener;
	private JTextField txtArtist;
	private JTextField txtTrack;
	private JTextField txtType;
	private JTextField txtColumnquantity;

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
		if(distributor != null && distributor.getId() > 0){
			lblProductname.setText(distributor.getName());
			txtColumnCount.setText(String.valueOf(distributor.getColumnCount()));
			txtColumnId.setText(String.valueOf(distributor.getColumnId()));
			txtColumnPrice.setText(String.valueOf(distributor.getColumnPrice()));
			txtArtist.setText(String.valueOf(distributor.getColumnArtist()));
			txtTrack.setText(String.valueOf(distributor.getColumnTrack()));
			txtType.setText(String.valueOf(distributor.getColumnTrackType()));
			txtColumnquantity.setText(String.valueOf(distributor.getColumnQuantity()));
			
			model = new GenericListModel<>(new ArrayList<>(distributor.getAliasSet()));
			aliasList.setModel(model);;
			
		}else{
			lblProductname.setText("New Distributor");
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
					
					JLabel lblName = new JLabel("Name");
					lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
					lblName.setBounds(10, 27, 46, 14);
					panelMain.add(lblName);
				
					
					txtName = new JTextField();
					txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtName.setBounds(119, 22, 191, 25);
					panelMain.add(txtName);
					txtName.setColumns(10);
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
					
					JLabel lblIdColumn = new JLabel("ID Column");
					lblIdColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblIdColumn.setBounds(10, 42, 120, 20);
					panelCharacteristic.add(lblIdColumn);
					
					JLabel lblPriceColumn = new JLabel("Price Column");
					lblPriceColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblPriceColumn.setBounds(10, 73, 120, 20);
					panelCharacteristic.add(lblPriceColumn);
					
					txtColumnCount = new JTextField();
					txtColumnCount.setBounds(140, 13, 86, 20);
					panelCharacteristic.add(txtColumnCount);
					txtColumnCount.setColumns(10);
					
					txtColumnId = new JTextField();
					txtColumnId.setColumns(10);
					txtColumnId.setBounds(140, 44, 86, 20);
					panelCharacteristic.add(txtColumnId);
					
					txtColumnPrice = new JTextField();
					txtColumnPrice.setColumns(10);
					txtColumnPrice.setBounds(140, 75, 86, 20);
					panelCharacteristic.add(txtColumnPrice);
					
					JLabel lblArtistColumn = new JLabel("Artist Column");
					lblArtistColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblArtistColumn.setBounds(10, 104, 120, 20);
					panelCharacteristic.add(lblArtistColumn);
					
					txtArtist = new JTextField();
					txtArtist.setColumns(10);
					txtArtist.setBounds(140, 106, 86, 20);
					panelCharacteristic.add(txtArtist);
					
					JLabel lblTrackColumn = new JLabel("Track Column");
					lblTrackColumn.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblTrackColumn.setBounds(10, 137, 120, 20);
					panelCharacteristic.add(lblTrackColumn);
					
					txtTrack = new JTextField();
					txtTrack.setColumns(10);
					txtTrack.setBounds(140, 139, 86, 20);
					panelCharacteristic.add(txtTrack);
					
					JLabel lblTrackType = new JLabel("Track type");
					lblTrackType.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblTrackType.setBounds(10, 168, 120, 20);
					panelCharacteristic.add(lblTrackType);
					
					txtType = new JTextField();
					txtType.setColumns(10);
					txtType.setBounds(140, 170, 86, 20);
					panelCharacteristic.add(txtType);
					
					txtColumnquantity = new JTextField();
					txtColumnquantity.setColumns(10);
					txtColumnquantity.setBounds(396, 13, 86, 20);
					panelCharacteristic.add(txtColumnquantity);
					
					JLabel lblColumnQuantity = new JLabel("Column quantity");
					lblColumnQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblColumnQuantity.setBounds(266, 11, 120, 20);
					panelCharacteristic.add(lblColumnQuantity);
					
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
							pushDataToProduct();
							listener.saveDistributor(distributor);
							dispose();
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
	
	private void pushDataToProduct(){
		try{
			distributor.setName(txtName.getText());
			distributor.setColumnCount(Integer.valueOf(txtColumnCount.getText()));
			distributor.setColumnId(Integer.valueOf(txtColumnId.getText()));
			distributor.setColumnPrice(Integer.valueOf(txtColumnPrice.getText()));
			distributor.setColumnArtist(Integer.valueOf(txtArtist.getText()));
			distributor.setColumnTrack(Integer.valueOf(txtTrack.getText()));
			distributor.setColumnTrackType(Integer.valueOf(txtType.getText()));
			distributor.setColumnQuantity(Integer.valueOf(txtColumnquantity.getText()));
			
			Set<DistributorAlias> set = new HashSet<DistributorAlias>();
			set.addAll(model.getList());
			distributor.setAliasSet(set);
		}catch(Exception e){
			
		}
	
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
