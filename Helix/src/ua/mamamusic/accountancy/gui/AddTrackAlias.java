package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.TracksListListener;
import ua.mamamusic.accountancy.UploadFormListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.GenericListModelFilter;
import ua.mamamusic.accountancy.model.GenericListRenderer;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackAlias;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;
import ua.mamamusic.accountancy.session.TrackManager;
import ua.mamamusic.accountancy.session.TrackManagerImpl;

import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.Component;

public class AddTrackAlias extends JDialog implements TracksListListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtAlias;
	private List<Track> trackList;
	private String aliasName;
	private JList<Track> list;
	private Artist artist;
	private JTextField txtFilter;
	private GenericListModelFilter<Track> filteredModel;
	private UploadFormListener listener;
	private DataRow product;

	/**
	 * Create the dialog.
	 */
	public AddTrackAlias(Window owner, DataRow product, UploadFormListener listener) {
		super(owner, product.getArtist().getName(), Dialog.ModalityType.DOCUMENT_MODAL);
		SortedSet<Track> set = new TreeSet<>(product.getArtist().getTrackSet());
		this.trackList = new ArrayList<Track>(set);
		this.aliasName = product.getColumnTrack();
		this.artist = product.getArtist();
		this.listener = listener;
		this.product = product;
		init();
		populateForm();
		
	}
	private void populateForm(){
		txtAlias.setText(aliasName);
		GenericListModel<Track> model = new GenericListModel<Track>(trackList);
		
		filteredModel = new GenericListModelFilter<Track>(model);
		list.setModel(filteredModel);
		list.setCellRenderer(new GenericListRenderer());
		
	}
	private void init(){
		setBounds(100, 100, 500, 340);
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
				txtAlias.setEditable(false);
				txtAlias.setColumns(40);
			}
		}
		{
			JPanel panelMain = new JPanel();
			panelMain.setBorder(new LineBorder(Color.LIGHT_GRAY));
			contentPanel.add(panelMain, BorderLayout.CENTER);
			panelMain.setLayout(new BorderLayout(0, 0));
			{
				
				list = new JList<Track>();
				list.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.LIGHT_GRAY));
				
				JScrollPane scrollPane = new JScrollPane(list);
				scrollPane.setBorder(null);
				panelMain.add(scrollPane, BorderLayout.CENTER);
			}
			{
				JToolBar toolBar = new JToolBar();
				panelMain.add(toolBar, BorderLayout.NORTH);
				{
					JButton btnAdd = new JButton(IconFactory.ADD24_ICON);
					btnAdd.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							JDialog trackForm = new TrackForm(AddTrackAlias.this,"Add Track", new Track(), AddTrackAlias.this);
							trackForm.setVisible(true);
						}
					});
					toolBar.add(btnAdd);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JPanel panel = new JPanel();
				panel.setBorder(new EmptyBorder(0, 0, 0, 80));
				buttonPane.add(panel);
				{
					JLabel lblFilter = new JLabel("Filter:");
					panel.add(lblFilter);
				}
				{
					txtFilter = new JTextField();
					panel.add(txtFilter);
					txtFilter.setAlignmentX(Component.LEFT_ALIGNMENT);
					txtFilter.setColumns(20);
					txtFilter.addKeyListener(new KeyAdapter()
					{
						public void keyReleased(KeyEvent ke)
						{
							filteredModel.setFilter(new GenericListModelFilter.Filter() {
					        public boolean accept(Object element) {
					        	
					        	String track = ((Track)element).toString();
					            return track.toLowerCase().contains(txtFilter.getText().toLowerCase());
					        }
				    });
						}
					});
					
				}
			}
			{
				JButton okButton = new JButton("OK", IconFactory.OK_ICON);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Track selectedTrack = list.getSelectedValue();
						
						if(selectedTrack != null){
							save(selectedTrack);
							listener.fireTableChanged();
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

	private void save(Track track){
		TrackAlias alias = new TrackAlias();
		alias.setTrack(track);
		alias.setName(txtAlias.getText());
		
		if(track.getAliasSet() != null){
			track.getAliasSet().add(alias);
		}else{
			Set<TrackAlias> set = new HashSet<TrackAlias>();
			set.add(alias);
			track.setAliasSet(set);
		}
		
		TrackManager tm = new TrackManagerImpl();
		tm.updateTrack(track);
		
		product.setTrack(track);
	}
	
	@Override
	public void saveTrack(Track track) {
//		if(track != null && track.getId() < 1){
//			track.setArtist(artist);
//			
//			if(artist.getTrackSet() != null){
//				artist.getTrackSet().add(track);
//			}else{
//				Set<Track> set = new HashSet<Track>();
//				set.add(track);
//				artist.setTrackSet(set);
//			}
//			
//			ArtistManager am = new ArtistManagerImpl();
//			am.updateArtist(artist);
//			
//			((GenericListModelFilter<Track>) list.getModel()).addElement(track);
//		}
	}
}
