package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import ua.mamamusic.accountancy.ArtistAliasListListener;
import ua.mamamusic.accountancy.DistributerAliasListListener;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.DistributorAlias;
import ua.mamamusic.accountancy.model.GenericListModel;

public class ArtistAliasForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private String title;
	private ArtistAlias alias;
	private JLabel lblDistribobject;
	private ArtistAliasListListener listener;


	/**
	 * Create the dialog.
	 */
	public ArtistAliasForm() {
		
		init();
		
	}
	public ArtistAliasForm(Window owner, String title,
			ArtistAlias alias, ArtistAliasListListener listener) {
		super(owner, title, Dialog.ModalityType.DOCUMENT_MODAL);
		this.title = title;
		this.alias = alias;
		this.listener = listener;
		
		init();
		populateForm();
		
	}
	
	private void populateForm(){
		if(alias != null && alias.getId() > 0){
			txtName.setText(alias.getName());
		}
		lblDistribobject.setText(title);
	}
	
	private void init(){
		setBounds(100, 100, 450, 152);
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
				JLabel lblDistributor = new JLabel("Distributor:");
				lblDistributor.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblDistributor);
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
				panel_1.setBorder(new EmptyBorder(0, 10, 0, 0));
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				panel.add(panel_1);
				{
					JLabel lblName = new JLabel("Name:");
					lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					panel_1.add(lblName);
				}
				{
					txtName = new JTextField();
					txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
					panel_1.add(txtName);
					txtName.setText("name");
					txtName.setColumns(20);
				}
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
						if(listener != null && alias != null){
							fillAlias();
							listener.saveAlias(alias);
							ArtistAliasForm.this.dispose();
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel", IconFactory.CANCEL_ICON);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void fillAlias(){
		alias.setName(txtName.getText());
	}

}
