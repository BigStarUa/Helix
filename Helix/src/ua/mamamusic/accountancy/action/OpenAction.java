package ua.mamamusic.accountancy.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JComponent;

import ua.mamamusic.accountancy.gui.TabPane;


public abstract class OpenAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TabPane tabbedPane;
	private JComponent component;

	
	public OpenAction(TabPane tabbedPane, String title){
		super();
		this.tabbedPane = tabbedPane;
	}
	
	@Override
	public abstract void actionPerformed(ActionEvent e);
	
	public boolean isComponentExist(){
		TabPane tabbed = getTabbedPane();
		JComponent c = getComponent();
		int index = tabbed.indexOfComponent(c);
		if(index > -1){
			return true;
		}
		
		return false;
	}
	
	public void setTabbedPane(TabPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	public JComponent getComponent() {
		// TODO Auto-generated method stub
		return component;
	}

	public TabPane getTabbedPane(){
		return tabbedPane;
	}

}
