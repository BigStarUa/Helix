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
import ua.mamamusic.accountancy.ArtistAliasListListener;
import ua.mamamusic.accountancy.ArtistsListListener;
import ua.mamamusic.accountancy.DistributerAliasListListener;
import ua.mamamusic.accountancy.DistributorsListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.TracksAliasListListener;
import ua.mamamusic.accountancy.TracksListListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorAlias;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackAlias;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JToolBar;

public class TrackForm extends AbstractJDialog implements TracksAliasListListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Track track;
	private JTextField txtName;
	private JLabel lblProductname;
	private JList<TrackAlias> aliasList;
	private GenericListModel<TrackAlias> model;
	private TracksListListener listener;

	public TrackForm(Window owner, String title, Track track, TracksListListener listener) {
		super(owner, title, Dialog.ModalityType.DOCUMENT_MODAL);
		setPreferredSize(new Dimension(600, 440));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.track = track;
		this.listener = listener;
		init();
		populateForm();
	}

	private void populateForm(){		
		txtName.setText(track.getName());
		if(track != null && track.getId() > 0){
			lblProductname.setText(track.getName());
			
			List<TrackAlias> list;
			if(track.getAliasSet() != null){
				list = new ArrayList<>(track.getAliasSet());
			}else{
				list = new ArrayList<>();
			}
			
			model = new GenericListModel<>(list);
			aliasList.setModel(model);;
			
		}else{
			lblProductname.setText("New track");
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
				JLabel lblIcondistributer = new JLabel(IconFactory.HEADPHONES48_ICON);
				panelTop.add(lblIcondistributer);
			}
			{
				JLabel lblProductForm = new JLabel("Track:");
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
							TrackAliasForm dialog = new TrackAliasForm(TrackForm.this.getOwner(), "", new TrackAlias(), TrackForm.this);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						}
					});
					
					toolBar.add(btnAdd);
					
					JButton btnEdit = new JButton(IconFactory.EDIT24_ICON);
					btnEdit.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(aliasList.getSelectedValue() != null){
								TrackAliasForm dialog = new TrackAliasForm(TrackForm.this.getOwner(), "Edit alias", aliasList.getSelectedValue(), TrackForm.this);
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
								dialog.setVisible(true);
							}
						}
					});
					toolBar.add(btnEdit);
					
					JButton btnRemove = new JButton(IconFactory.DELETE24_ICON);
					btnRemove.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(aliasList.getSelectedValue() != null){
								int selection = JOptionPane.showOptionDialog(null,
							            "Are you sure want to delete?",
							            "Delete!", JOptionPane.YES_NO_OPTION,
							            JOptionPane.INFORMATION_MESSAGE, null, null, null);

								if(selection == JOptionPane.YES_OPTION)
								{
									model.removeElement(aliasList.getSelectedIndex());
								}
							}
						}
					});
					toolBar.add(btnRemove);
					
					aliasList = new JList<TrackAlias>();
					panelAlias.add(new JScrollPane(aliasList));
					
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
						pushDataToProduct();
						listener.saveTrack(track);
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
			track.setName(txtName.getText());
			
			Set<TrackAlias> set = new HashSet<TrackAlias>();
			set.addAll(model.getList());
			track.setAliasSet(set);
		}catch(Exception e){
			
		}
	}

	@Override
	public void saveTrackAlias(TrackAlias alias) {
		if(alias != null && alias.getId() < 1){
			alias.setTrack(track);
			model.addElement(alias);
		}else{
			model.repaintObjectInList(alias);
		}
	}
}
