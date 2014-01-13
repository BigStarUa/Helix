package ua.mamamusic.accountancy.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.GeneralListener;
import ua.mamamusic.accountancy.IconFactory;

import java.awt.Font;

public class TabPane extends JTabbedPane implements GeneralListener{

	private static TabPane instance;
	
	private TabPane(){
		setFont(new Font("Tahoma", Font.PLAIN, 14));
	}
	
	public static synchronized TabPane getInstance(){
		if(instance == null){
			instance = new TabPane();
		}
		return instance;
	}
	public void addClosableTab(AbstractJPanel component, String title) {
		
		// Add the tab to the pane without any label
		addTab(null, component);
		int position = indexOfComponent(component);
		
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 0);
		
		JPanel tab = new JPanel(layout);
		tab.setOpaque(false);
		
		JLabel label = new JLabel(title);
		//label.setIcon(icon);
		
		JButton button = new JButton();
		button.setOpaque(false);
		
		button.setRolloverIcon(IconFactory.CLOSE_TAB_ICON_HOVER);
		button.setRolloverEnabled(true);
		button.setIcon(IconFactory.CLOSE_TAB_ICON);
		
		button.setBorder(null);
		
		button.setFocusable(false);
		button.addActionListener(new CloseActionListener(component));
		
		tab.add(label);
		tab.add(button);
		
		tab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		
		setTabComponentAt(position, tab);
		
		component.addListener(this);
		setSelectedComponent(component);
	}
	
	class CloseActionListener implements ActionListener{

		private AbstractJPanel c;
		public CloseActionListener(AbstractJPanel c){
			this.c = c;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			remove(c);
		}
		
	}

	@Override
	public void updateFired() {
		System.out.println("Fired");
		fireStateChanged();
	}
}
