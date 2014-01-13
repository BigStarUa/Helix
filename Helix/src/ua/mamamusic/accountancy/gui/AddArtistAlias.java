package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.UploadFormListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.GenericListModelFilter;
import ua.mamamusic.accountancy.model.GenericListRenderer;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;

import javax.swing.border.LineBorder;

import java.awt.Color;

public class AddArtistAlias extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtAlias;
	private List<Artist> artistList;
	private String aliasName;
	private JList<Artist> list;
	private UploadFormListener listener;
	private JTextField txtFilter;
	private GenericListModelFilter<Artist> filteredModel;

	/**
	 * Create the dialog.
	 */
	public AddArtistAlias(Window owner, List<Artist> artistList, String alias, UploadFormListener listener) {
		super(owner, "Select artist", Dialog.ModalityType.DOCUMENT_MODAL);
//		SortedSet<Artist> set = new TreeSet<>(artistList);
//		this.artistList = new ArrayList<Artist>(set);
		this.artistList = artistList;
		this.aliasName = alias;
		this.listener = listener;
		init();
		populateForm();
		
	}
	private void populateForm(){
		txtAlias.setText(aliasName);
		
		GenericListModel<Artist> model = new GenericListModel<Artist>(artistList);
		filteredModel = new GenericListModelFilter<Artist>(model);
		list.setModel(filteredModel);
		list.setCellRenderer(new GenericListRenderer());
		
	}
	private void init(){
		setBounds(100, 100, 520, 300);
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
				txtAlias.setEditable(false);
				panel.add(txtAlias);
				txtAlias.setColumns(40);
			}
		}
		{
			JPanel panelMain = new JPanel();
			contentPanel.add(panelMain, BorderLayout.CENTER);
			panelMain.setLayout(new BorderLayout(0, 0));
			{
				
				list = new JList<Artist>();
				list.setBorder(new LineBorder(Color.LIGHT_GRAY));
				
				JScrollPane scrollPane = new JScrollPane(list);
				
				panelMain.add(scrollPane, BorderLayout.CENTER);
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
						        	String track = ((Artist)element).toString();
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
						Artist selectedArtist = list.getSelectedValue();
						
						if(selectedArtist != null){
							save(selectedArtist);
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

	private void save(Artist artist){
		ArtistAlias alias = new ArtistAlias();
		alias.setArtist(artist);
		alias.setName(txtAlias.getText());
		
		if(artist.getAliasSet() != null){
			artist.getAliasSet().add(alias);
		}else{
			Set<ArtistAlias> set = new HashSet<ArtistAlias>();
			set.add(alias);
			artist.setAliasSet(set);
		}
		
		ArtistManager tm = new ArtistManagerImpl();
		tm.updateArtist(artist);
	}
}
