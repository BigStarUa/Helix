package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

import ua.mamamusic.accountancy.ArtistsListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.TracksListListener;
import ua.mamamusic.accountancy.UploadFormListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.GenericListRenderer;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackAlias;
import ua.mamamusic.accountancy.model.TrackType;
import ua.mamamusic.accountancy.model.TrackTypeAlias;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.TrackManager;
import ua.mamamusic.accountancy.session.TrackManagerImpl;
import ua.mamamusic.accountancy.session.TrackTypeManager;
import ua.mamamusic.accountancy.session.TrackTypeManagerImpl;

import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.MatteBorder;

public class AddTypeAlias extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtAlias;
	private List<TrackType> typeList;
	private String aliasName;
	private JList<TrackType> list;
	private UploadFormListener listener;

	/**
	 * Create the dialog.
	 */
	public AddTypeAlias(Window owner, List<TrackType> typeList, String alias, UploadFormListener listener) {
		super(owner, "Select track type", Dialog.ModalityType.DOCUMENT_MODAL);
//		SortedSet<TrackType> set = new TreeSet<>(typeList);
//		this.typeList = new ArrayList<TrackType>(set);
		this.typeList = typeList;
		this.aliasName = alias;
		this.listener = listener;
		init();
		populateForm();
		
	}
	private void populateForm(){
		txtAlias.setText(aliasName);
		ListModel<TrackType> model = new GenericListModel<TrackType>(typeList);
		list.setModel(model);
		list.setCellRenderer(new GenericListRenderer());
		
	}
	private void init(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEADING);
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				JLabel lblAlias = new JLabel("Alias:");
				panel.add(lblAlias);
			}
			{
				txtAlias = new JTextField();
				panel.add(txtAlias);
				txtAlias.setColumns(40);
			}
		}
		{
			JPanel panelMain = new JPanel();
			contentPanel.add(panelMain, BorderLayout.CENTER);
			panelMain.setLayout(new BorderLayout(0, 0));
			{
				
				list = new JList<TrackType>();
				list.setBorder(new LineBorder(Color.LIGHT_GRAY));
				panelMain.add(list, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK", IconFactory.OK_ICON);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						TrackType selectedArtist = list.getSelectedValue();
						
						if(selectedArtist != null){
							save(selectedArtist);
							dispose();
						}
					}
				});
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

	private void save(TrackType type){
		TrackTypeAlias alias = new TrackTypeAlias();
		alias.setName(txtAlias.getText());
		
		if(type.getTypeSet() != null){
			type.getTypeSet().add(alias);
		}else{
			Set<TrackTypeAlias> set = new HashSet<TrackTypeAlias>();
			set.add(alias);
			type.setTypeSet(set);
		}
		
		TrackTypeManager tm = new TrackTypeManagerImpl();
		tm.updateTrack(type);
		
		listener.fireTableChanged();
	}
}
