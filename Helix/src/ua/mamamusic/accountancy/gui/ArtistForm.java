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
import ua.mamamusic.accountancy.TracksListListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.DistributorAlias;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.GenericListRenderer;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JToolBar;

public class ArtistForm extends AbstractJDialog implements ArtistAliasListListener, TracksListListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Artist artist;
	private JTextField txtName;
	private JLabel lblProductname;
	private JList<ArtistAlias> aliasList;
	private GenericListModel<ArtistAlias> model;
	private GenericListModel<Track> trackListModel;
	private ArtistsListListener listener;
	//private JList<Track> trackList;
	private JButton btnRemove;
	private JButton btnRemoveTrack;
	private JButton btnEditTrack;
	private JToolBar toolBarTrack;
	private JToolBar toolBarAlias;
	private JList<Track> trackList;
	private JTextField txtIncomepercent;

	public ArtistForm(Window owner, String title, ModalityType modalityType, Artist artist, ArtistsListListener listener) {
		super(owner, title, modalityType);
		setPreferredSize(new Dimension(600, 440));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.artist = artist;
		this.listener = listener;
		init();
		populateForm();
	}

	private void populateForm(){		
		txtName.setText(artist.getName());
		if(artist != null && artist.getId() > 0){
			lblProductname.setText(artist.getName());
			txtIncomepercent.setText(String.valueOf(artist.getIncomePercent()));
			
			List<ArtistAlias> list;
			if(artist.getAliasSet() != null){
				list = new ArrayList<>(artist.getAliasSet());
			}else{
				list = new ArrayList<>();
			}
			
			List<Track> tList;
			if(artist.getTrackSet() != null){
				tList = new ArrayList<>(artist.getTrackSet());
			}else{
				tList = new ArrayList<>();
			}
			trackListModel = new GenericListModel<>(tList);
			trackList.setModel(trackListModel);
			trackList.setCellRenderer(new GenericListRenderer());
			
			model = new GenericListModel<>(list);
			aliasList.setModel(model);
			aliasList.setCellRenderer(new GenericListRenderer());
			
			ListSelectionModel listSelectionModelTrack = trackList.getSelectionModel();
			listSelectionModelTrack.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(!e.getValueIsAdjusting()){		
						btnEditTrack.setEnabled(true);
						btnRemoveTrack.setEnabled(true);
						toolBarTrack.repaint();
						toolBarTrack.revalidate();
					}
					
				}
			});
			
			ListSelectionModel listSelectionModel = aliasList.getSelectionModel();
			listSelectionModel.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(!e.getValueIsAdjusting()){		
						btnRemove.setEnabled(true);
						toolBarAlias.repaint();
						toolBarAlias.revalidate();
					}
					
				}
			});
			
		}else{
			lblProductname.setText("New Artist");
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
				JLabel lblIcondistributer = new JLabel(IconFactory.ARTIST48_ICON);
				panelTop.add(lblIcondistributer);
			}
			{
				JLabel lblProductForm = new JLabel("Artist:");
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
					lblName.setBounds(10, 27, 79, 20);
					panelMain.add(lblName);
				
					
					txtName = new JTextField();
					txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtName.setBounds(143, 22, 191, 25);
					panelMain.add(txtName);
					txtName.setColumns(10);
					
					JLabel lblPercentOfIncome = new JLabel("Income percent:");
					lblPercentOfIncome.setFont(new Font("Tahoma", Font.PLAIN, 16));
					lblPercentOfIncome.setBounds(10, 58, 127, 25);
					panelMain.add(lblPercentOfIncome);
					
					txtIncomepercent = new JTextField();
					txtIncomepercent.setFont(new Font("Tahoma", Font.PLAIN, 14));
					txtIncomepercent.setBounds(143, 58, 86, 24);
					panelMain.add(txtIncomepercent);
					txtIncomepercent.setColumns(10);
					
					JLabel lblMax = new JLabel("max(100)");
					lblMax.setFont(new Font("Tahoma", Font.PLAIN, 16));
					lblMax.setBounds(239, 61, 69, 18);
					panelMain.add(lblMax);
				}
				{
					JPanel panelAlias = new JPanel();
					tabbedPane.addTab("Alias", null, panelAlias, null);
					panelAlias.setLayout(new BorderLayout(0, 0));
					
					toolBarAlias = new JToolBar();
					panelAlias.add(toolBarAlias, BorderLayout.NORTH);
					
					JButton btnAdd = new JButton(IconFactory.ADD24_ICON);
					btnAdd.setFocusable(false);
					btnAdd.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							ArtistAliasForm dialog = new ArtistAliasForm(ArtistForm.this.getOwner(), "", new ArtistAlias(), ArtistForm.this);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						}
					});
					
					toolBarAlias.add(btnAdd);
					
					btnRemove = new JButton(IconFactory.DELETE24_ICON);
					btnRemove.setEnabled(false);
					btnRemove.setFocusable(false);
					btnRemove.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							int index = aliasList.getSelectedIndex();
							if(index >= 0){
								int selection = JOptionPane.showOptionDialog(null,
							            "Are you sure want to delete?",
							            "Delete!", JOptionPane.YES_NO_OPTION,
							            JOptionPane.INFORMATION_MESSAGE, null, null, null);
	
								if(selection == JOptionPane.YES_OPTION)
								{
									model.removeElement(index);
									btnRemove.setEnabled(false);
								}
							}
						}
					});
					toolBarAlias.add(btnRemove);
					
					aliasList = new JList<ArtistAlias>();
					JScrollPane scrollPane = new JScrollPane(aliasList);
					panelAlias.add(scrollPane);
					
				}
				{
					JPanel panelCharacteristic = new JPanel();
					tabbedPane.addTab("Tracks", null, panelCharacteristic, null);
					panelCharacteristic.setLayout(new BorderLayout(0, 0));
					
					toolBarTrack = new JToolBar();
					panelCharacteristic.add(toolBarTrack, BorderLayout.NORTH);
					
					JButton btnAddTrack = new JButton(IconFactory.ADD24_ICON);
					btnAddTrack.setFocusable(false);
					btnAddTrack.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
//							TrackForm dialog = new TrackForm(ArtistForm.this.getOwner(), "", new Track(), ArtistForm.this);
//							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//							dialog.setVisible(true);
						}
					});
					toolBarTrack.add(btnAddTrack);
					
					btnEditTrack = new JButton(IconFactory.EDIT24_ICON);
					btnEditTrack.setEnabled(false);
					btnEditTrack.setFocusable(false);
					btnEditTrack.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
//							if(trackList.getSelectedValue() != null){
//								Track track = trackList.getSelectedValue();
//								TrackForm dialog = new TrackForm(ArtistForm.this.getOwner(), "", track, ArtistForm.this);
//								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//								dialog.setVisible(true);
//							}
						}
					});
					toolBarTrack.add(btnEditTrack);
					
					btnRemoveTrack = new JButton(IconFactory.DELETE24_ICON);
					btnRemoveTrack.setEnabled(false);
					btnRemoveTrack.setFocusable(false);
					btnRemoveTrack.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
//							int index = trackList.getSelectedIndex();
//							if(index >= 0){
//								int selection = JOptionPane.showOptionDialog(null,
//							            "Are you sure want to delete?",
//							            "Delete!", JOptionPane.YES_NO_OPTION,
//							            JOptionPane.INFORMATION_MESSAGE, null, null, null);
//
//								if(selection == JOptionPane.YES_OPTION)
//								{
//									trackListModel.removeElement(index);
//									btnRemoveTrack.setEnabled(false);
//									btnEditTrack.setEnabled(false);
//								}
//								
//							}
						}
					});
					toolBarTrack.add(btnRemoveTrack);
					
					trackList = new JList<Track>();
					JScrollPane scrollPane = new JScrollPane(trackList);
					panelCharacteristic.add(scrollPane, BorderLayout.CENTER);
					
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
						try {
							pushDataToProduct();
							listener.saveArtist(artist);
							dispose();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(ArtistForm.this,
								    "Error! Check form data!",
								    "Warning",
								    JOptionPane.WARNING_MESSAGE);
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
			artist.setName(txtName.getText());
			
			int incomePercent = Integer.parseInt(txtIncomepercent.getText());
			if(incomePercent > 100 || incomePercent < 0) throw new Exception();
			
			artist.setIncomePercent(incomePercent);
			
			Set<ArtistAlias> set = new HashSet<ArtistAlias>();
			set.addAll(model.getList());
			artist.setAliasSet(set);
			
			Set<Track> trackSet = new HashSet<Track>();
			trackSet.addAll(trackListModel.getList());
			artist.setTrackSet(trackSet);
	
	}

	@Override
	public void saveAlias(ArtistAlias alias) {
		if(alias != null && alias.getId() < 1){
			//GenericListModel<DistributorAlias> model = (GenericListModel<DistributorAlias>)aliasList.getModel();
			alias.setArtist(artist);
			model.addElement(alias);
		}else{
			model.repaintObjectInList(alias);
		}
	}

	@Override
	public void saveTrack(Track track) {
//		if(track != null && track.getId() < 1){
//			track.setArtist(artist);
//			trackListModel.addElement(track);
//		}else{
//			trackListModel.repaintObjectInList(track);
//		}
	}
}
