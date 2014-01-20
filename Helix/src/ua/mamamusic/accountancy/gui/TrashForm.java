package ua.mamamusic.accountancy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.EmptyBorder;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.IconFactory;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.ProductRow;
import ua.mamamusic.accountancy.model.ProductRowTableModel;
import ua.mamamusic.accountancy.model.TRightType;
import ua.mamamusic.accountancy.session.DistributorManager;
import ua.mamamusic.accountancy.session.DistributorManagerImpl;
import ua.mamamusic.accountancy.session.ProductRowManager;
import ua.mamamusic.accountancy.session.ProductRowManagerImpl;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JComboBox;

import java.awt.Component;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

import org.hibernate.mapping.Set;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

public class TrashForm extends AbstractJPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JDatePickerImpl dateStart;
	private JDatePickerImpl dateEnd;
	private JComboBox<Distributor> comboBoxDistributor;
	private JButton btnDelete;
	private JButton btnPreload;
	private ProductRowManager prm;
	private JComboBox comboBoxRightType;


	/**
	 * Create the dialog.
	 */
	public TrashForm() {
		build();
		init();
	}
	
	private void init() {
		
		DistributorManager pm = new DistributorManagerImpl();
		List<Distributor> distribList = pm.loadAllProductsOrderedBy("name");
		
		prm = new ProductRowManagerImpl();
		
		comboBoxDistributor.setModel(new DefaultComboBoxModel<>(distribList.toArray(new Distributor[distribList.size()])));
		
		btnPreload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date start = ((Calendar)dateStart.getModel().getValue()).getTime();
				Date end = ((Calendar)dateEnd.getModel().getValue()).getTime();
				
				TRightType rightType = null;
				
				switch(comboBoxRightType.getSelectedIndex()){
				case 0:
					rightType = null;
					break;
				case 1:
					rightType = TRightType.AUTHOR;
					break;
				case 2:
					rightType = TRightType.RELATED;
					break;
				}
				
				List<ProductRow> dataRowList = prm.loadAllDataRowsByPeriod(start, end, (Distributor)comboBoxDistributor.getSelectedItem(), rightType);
				
				TableModel model = new ProductRowTableModel(dataRowList);
				table.setModel(model);
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProductRowTableModel model = (ProductRowTableModel)table.getModel();
				if(model.getRowCount() > 0){
					prm.deleteProductRowList(model.getList());
					table.setModel(new DefaultTableModel());
				}
			}
		});
	}
	
	private void build() {
		setBounds(100, 100, 650, 610);
		setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPanel.add(tabbedPane, BorderLayout.CENTER);
		
		AbstractFormatter ab = new AbstractFormatter() {
			@Override
			public String valueToString(Object value) throws ParseException {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Date date = new Date();
				if(value != null) {
					date = ((Calendar)value).getTime();
					return sdf.format(date);
				}
				return "";
			}
			
			@Override
			public Object stringToValue(String text) throws ParseException {
				return null;
			}
		};
		
		{
			JPanel panelMain = new JPanel();
			tabbedPane.addTab("Restore data", null, panelMain, null);
			panelMain.setLayout(null);
		}
		
		{
			JPanel panelDelete = new JPanel();
			tabbedPane.addTab("Delete data", null, panelDelete, null);
			panelDelete.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel.setBorder(new EmptyBorder(0, 5, 0, 5));
				panelDelete.add(panel, BorderLayout.WEST);
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				{
					Box verticalBox = Box.createVerticalBox();
					panel.add(verticalBox);
					{
						Component verticalStrut = Box.createVerticalStrut(20);
						verticalBox.add(verticalStrut);
					}
					{
						JPanel panel_1 = new JPanel();
						panel_1.setBackground(Color.WHITE);
						panel_1.setBorder(new TitledBorder(null, "Distributor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
						verticalBox.add(panel_1);
						panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
						{
							comboBoxDistributor = new JComboBox<Distributor>();
							comboBoxDistributor.setMaximumSize(new Dimension(32767, 25));
							comboBoxDistributor.setPreferredSize(new Dimension(150, 20));
							panel_1.add(comboBoxDistributor);
						}
					}
					{
						Component verticalStrut = Box.createVerticalStrut(20);
						verticalBox.add(verticalStrut);
					}
					{
						JPanel panel_2 = new JPanel();
						panel_2.setBackground(Color.WHITE);
						panel_2.setBorder(new TitledBorder(null, "Date", TitledBorder.LEADING, TitledBorder.TOP, null, null));
						verticalBox.add(panel_2);
						panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
						{
							Box horizontalBox = Box.createHorizontalBox();
							panel_2.add(horizontalBox);
							{
								
								JDatePanelImpl pan = new JDatePanelImpl(null);
								
								JLabel lblFrom = new JLabel("From:");
								horizontalBox.add(lblFrom);
								dateStart = new JDatePickerImpl(pan, ab);
								horizontalBox.add((Component)dateStart);
								
							}
						}
						{
							Component rigidArea = Box.createRigidArea(new Dimension(20, 10));
							panel_2.add(rigidArea);
						}
						{
							Box horizontalBox = Box.createHorizontalBox();
							panel_2.add(horizontalBox);
							{
								JDatePanelImpl pan = new JDatePanelImpl(null);
								
								JLabel lblFrom = new JLabel("Till:    ");
								horizontalBox.add(lblFrom);
								dateEnd = new JDatePickerImpl(pan, ab);
								horizontalBox.add((Component)dateEnd);
							}
						}
					}
					{
						Component verticalStrut = Box.createVerticalStrut(20);
						verticalBox.add(verticalStrut);
					}
					{
						Box horizontalBox = Box.createHorizontalBox();
						horizontalBox.setBorder(new EmptyBorder(0, 5, 0, 5));
						verticalBox.add(horizontalBox);
						{
							JLabel lblRights = new JLabel("Rights: ");
							horizontalBox.add(lblRights);
						}
						{
							comboBoxRightType = new JComboBox();
							comboBoxRightType.setMaximumSize(new Dimension(32767, 25));
							comboBoxRightType.setModel(new DefaultComboBoxModel(new String[] {"Total (Combined)", "Author right", "Related right"}));
							comboBoxRightType.setFont(new Font("Tahoma", Font.PLAIN, 14));
							horizontalBox.add(comboBoxRightType);
						}
					}
					{
						Component verticalStrut = Box.createVerticalStrut(20);
						verticalBox.add(verticalStrut);
					}
					{
						Box horizontalBox = Box.createHorizontalBox();
						verticalBox.add(horizontalBox);
						{
							btnPreload = new JButton("Preload", IconFactory.RELOAD32_ICON);
							btnPreload.setFont(new Font("Tahoma", Font.PLAIN, 14));
							horizontalBox.add(btnPreload);
						}
						{
							btnDelete = new JButton("Delete", IconFactory.DELETE32_ICON);
							btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
							horizontalBox.add(btnDelete);
						}
					}
					{
						Component verticalStrut = Box.createVerticalStrut(20);
						verticalBox.add(verticalStrut);
					}
				}
			}
			{
				table = new JTable();
				table.setBackground(Color.WHITE);
				JScrollPane scrollPane = new JScrollPane(table);
				panelDelete.add(scrollPane, BorderLayout.CENTER);
			}
		}

	}
}