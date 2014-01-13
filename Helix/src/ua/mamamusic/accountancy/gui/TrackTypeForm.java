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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.JSplitPane;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;

import ua.mamamusic.accountancy.AbstractJDialog;
import ua.mamamusic.accountancy.ArtistAliasListListener;
import ua.mamamusic.accountancy.ArtistsListListener;
import ua.mamamusic.accountancy.DistributerAliasListListener;
import ua.mamamusic.accountancy.DistributorsListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.TrackTypeAliasListListener;
import ua.mamamusic.accountancy.TrackTypeFormListener;
import ua.mamamusic.accountancy.TracksListListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorAlias;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.model.TrackTypeAlias;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class TrackTypeForm extends AbstractJDialog implements TrackTypeAliasListListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private TrackType type;
	private JLabel lblProductname;
	private GenericListModel<TrackTypeAlias> model;
	private TrackTypeFormListener listener;
	private JTextField txtName;
	private JList<TrackTypeAlias> aliasList;
	private ListSelectionModel listSelectionModel;
	private JButton btnEdit;
	private JButton btnRemove;

	public TrackTypeForm(Window owner, TrackType type, TrackTypeFormListener listener) {
		super(owner, "TrackType form", Dialog.ModalityType.DOCUMENT_MODAL);
		setPreferredSize(new Dimension(600, 440));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.type = type;
		this.listener = listener;
		init();
		populateForm();
	}

	private void populateForm(){
		if(type != null && type.getId() > 0){
			lblProductname.setText(type.getName());
			txtName.setText(type.getName());
			
			List<TrackTypeAlias> list;
			if(type.getTypeSet() != null){
				list = new ArrayList<>(type.getTypeSet());
			}else{
				list = new ArrayList<>();
			}
			
			model = new GenericListModel<>(list);
			aliasList.setModel(model);
			
			
		}else{
			lblProductname.setText("New Type");
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
				JLabel lblIcondistributer = new JLabel(IconFactory.TRACK_TYPE48_ICON);
				panelTop.add(lblIcondistributer);
			}
			{
				JLabel lblProductForm = new JLabel("Track type:");
				lblProductForm.setFont(new Font("Tahoma", Font.BOLD, 14));
				panelTop.add(lblProductForm);
			}
			
			lblProductname = new JLabel("ProductName");
			lblProductname.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panelTop.add(lblProductname);
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
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblName);
		
		txtName = new JTextField();
		panel_1.add(txtName);
		txtName.setColumns(20);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		aliasList = new JList();
		aliasList.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.LIGHT_GRAY));
		aliasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel = aliasList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				boolean isAdjusting = e.getValueIsAdjusting();
				if(isAdjusting){
					btnEdit.setEnabled(true);
					btnRemove.setEnabled(true);
				}
			}
		});
		panel_2.add(aliasList);
		
		JToolBar toolBar = new JToolBar();
		panel_2.add(toolBar, BorderLayout.NORTH);
		
		JButton btnAdd = new JButton(IconFactory.ADD24_ICON);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog d = new TrackTypeAliasForm(TrackTypeForm.this, type.getName(), new TrackTypeAlias(), TrackTypeForm.this);
				d.setVisible(true);
			}
		});
		toolBar.add(btnAdd);
		
		btnEdit = new JButton(IconFactory.EDIT24_ICON);
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TrackTypeAlias alias = aliasList.getSelectedValue();
				JDialog d = new TrackTypeAliasForm(TrackTypeForm.this, type.getName(), alias, TrackTypeForm.this);
				d.setVisible(true);
			}
		});
		toolBar.add(btnEdit);
		
		btnRemove = new JButton(IconFactory.DELETE24_ICON);
		btnRemove.setEnabled(false);
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = aliasList.getSelectedIndex();
				int selection = JOptionPane.showOptionDialog(null,
			            "Are you sure want to delete?",
			            "Delete!", JOptionPane.YES_NO_OPTION,
			            JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(selection == JOptionPane.YES_OPTION)
				{
					model.removeElement(index);
					
					btnRemove.setEnabled(false);
					btnEdit.setEnabled(false);
				}
			}
		});
		toolBar.add(btnRemove);
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
						pushDataToProduct();
						listener.saveTrackType(type);
						dispose();
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
			type.setName(txtName.getText());
			
			Set<TrackTypeAlias> set = new HashSet<TrackTypeAlias>();
			set.addAll(model.getList());
			type.setTypeSet(set);

		}catch(Exception e){
			
		}
	
	}

	@Override
	public void saveAlias(TrackTypeAlias alias) {
		if(alias != null && !model.contains(alias)){
			model.addElement(alias);
		}else{
			model.repaintObjectInList(alias);
		}
		
	}
}
