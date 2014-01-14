package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;

import ua.mamamusic.accountancy.ArtistAliasListListener;
import ua.mamamusic.accountancy.DistributerAliasListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.TRightFormListener;
import ua.mamamusic.accountancy.TracksAliasListListener;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.DistributorAlias;
import ua.mamamusic.accountancy.model.GenericComboBoxModel;
import ua.mamamusic.accountancy.model.GenericListModel;
import ua.mamamusic.accountancy.model.TRight;
import ua.mamamusic.accountancy.model.TrackAlias;
import ua.mamamusic.accountancy.session.ArtistManager;
import ua.mamamusic.accountancy.session.ArtistManagerImpl;

import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

import java.awt.Color;

public class TRightForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private String title;
	private TRight right;
	private JLabel lblDistribobject;
	private TRightFormListener listener;
	private JTextField txtAuthor;
	private JTextField txtRelated;
	private JLabel lblRelatedMax;
	private JLabel lblAuthorMax;
	private int authorRight;
	private int relatedRight;
	private ArtistManager am;
	private JComboBox<Artist> comboBox;
	private JLabel lblNewRight;


	/**
	 * Create the dialog.
	 */
	public TRightForm() {
		
		init();
		
	}
	public TRightForm(Window owner, String title,
			TRight right, TRightFormListener listener, int authorRight, int relatedRight) {
		super(owner, title, Dialog.ModalityType.DOCUMENT_MODAL);
		this.title = title;
		this.right = right;
		this.listener = listener;
		this.authorRight = authorRight;
		this.relatedRight = relatedRight;
		
		init();
		populateForm();
		
	}
	
	private void populateForm(){
		
		am = new ArtistManagerImpl();
		List<Artist> list = am.loadAllArtistsOrderedBy("name");
		comboBox.setModel(new GenericComboBoxModel<Artist>(list));
		
		if(right != null){
			txtAuthor.setText(String.valueOf(right.getAuthor()));
			txtRelated.setText(String.valueOf(right.getRelated()));
			if(right.getArtist() != null){
				comboBox.setSelectedIndex(getObjectIndexFromModel(right.getArtist()));
				lblNewRight.setText(right.getArtist().getName());
			}
		}
		lblDistribobject.setText(title);
		lblAuthorMax.setText("max(" + String.valueOf(authorRight) + ")");
		lblRelatedMax.setText("max(" + String.valueOf(relatedRight) + ")");
	}
	
	private int getObjectIndexFromModel(Artist artist){
		Artist art;
		for(int i = 0; i < comboBox.getItemCount(); i++){
			art = comboBox.getItemAt(i);
			if(art.getId() == artist.getId()){
				return i;
			}
		}
		return -1;
	}
	
	private void init(){
		setBounds(100, 100, 450, 292);
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
				JLabel lblDistributor = new JLabel("Right:");
				lblDistributor.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblDistributor);
			}
			{
				lblNewRight = new JLabel("New right");
				lblNewRight.setFont(new Font("Tahoma", Font.BOLD, 14));
				panel.add(lblNewRight);
			}
			{
				lblDistribobject = new JLabel("");
				lblDistribobject.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblDistribobject);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(1, 0, 0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JLabel lblName = new JLabel("Artist:");
					lblName.setBounds(15, 6, 36, 20);
					lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					panel_1.add(lblName);
				}
				{
					comboBox = new JComboBox<Artist>();
					comboBox.setBounds(90, 6, 220, 20);
					panel_1.add(comboBox);
				}
				
				JLabel lblAuthor = new JLabel("Author:");
				lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblAuthor.setBounds(15, 46, 69, 20);
				panel_1.add(lblAuthor);
				
				JLabel lblRelated = new JLabel("Related:");
				lblRelated.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblRelated.setBounds(15, 80, 69, 20);
				panel_1.add(lblRelated);
				
				txtAuthor = new JTextField();
				txtAuthor.setBounds(92, 48, 86, 20);
				panel_1.add(txtAuthor);
				txtAuthor.setColumns(10);
				
				txtRelated = new JTextField();
				txtRelated.setBounds(92, 82, 86, 20);
				panel_1.add(txtRelated);
				txtRelated.setColumns(10);
				
				lblAuthorMax = new JLabel();
				lblAuthorMax.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblAuthorMax.setBounds(188, 46, 94, 20);
				panel_1.add(lblAuthorMax);
				
				lblRelatedMax = new JLabel();
				lblRelatedMax.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblRelatedMax.setBounds(188, 80, 94, 20);
				panel_1.add(lblRelatedMax);
			}
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
						if(listener != null && right != null){
							
							int author = Integer.parseInt(txtAuthor.getText());
							int related = Integer.parseInt(txtRelated.getText());
							
							if(comboBox.getSelectedItem() == null || author > authorRight || related > relatedRight){
								JOptionPane.showMessageDialog(TRightForm.this, "Fill all fields correct!");
								return;
							}
							fillAlias();
							listener.saveRight(right);
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
	
	private void fillAlias(){
		right.setAuthor(Integer.parseInt(txtAuthor.getText()));
		right.setRelated(Integer.parseInt(txtRelated.getText()));
		right.setArtist((Artist)comboBox.getSelectedItem());
	}
}
