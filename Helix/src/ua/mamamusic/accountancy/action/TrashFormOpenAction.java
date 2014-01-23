package ua.mamamusic.accountancy.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.gui.DistributorsList;
import ua.mamamusic.accountancy.gui.ReportForm;
import ua.mamamusic.accountancy.gui.TabPane;
import ua.mamamusic.accountancy.gui.TrashForm;
import ua.mamamusic.accountancy.gui.UploadForm;

public class TrashFormOpenAction extends OpenAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractJPanel component;
	private String title;

	public TrashFormOpenAction(TabPane tabbedPane, String title){
		super(tabbedPane, title);
		this.title = title;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TabPane tabbedPane = getTabbedPane();
		component = isExist(tabbedPane);
		if(component != null){
			tabbedPane.setSelectedComponent(component);
		}else{
			tabbedPane.addClosableTab(new TrashForm(), title);
		}
	}
	
	
	private AbstractJPanel isExist(TabPane tabbedPane){
		for(Component comp : tabbedPane.getComponents()){
			if(comp instanceof TrashForm){
				return (AbstractJPanel)comp;
			}
		}
		return null;
	}
	
	@Override
	public AbstractJPanel getComponent() {
		return component;
	}

}
