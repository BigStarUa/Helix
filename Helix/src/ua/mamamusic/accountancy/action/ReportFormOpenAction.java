package ua.mamamusic.accountancy.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.gui.DistributorsList;
import ua.mamamusic.accountancy.gui.ReportForm;
import ua.mamamusic.accountancy.gui.TabPane;
import ua.mamamusic.accountancy.gui.UploadForm;

public class ReportFormOpenAction extends OpenAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractJPanel component;
	private String title;

	public ReportFormOpenAction(TabPane tabbedPane, String title){
		super(tabbedPane, title);
		this.title = title;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TabPane tabbedPane = getTabbedPane();
		//component = UploadForm.getInstance();
		component = new ReportForm();
		
		
		if(isExist(tabbedPane)){
			tabbedPane.setSelectedComponent(component);
		}else{
			tabbedPane.addClosableTab(component, title);
		}
	}
	
	
	private boolean isExist(TabPane tabbedPane){
		for(Component comp : tabbedPane.getComponents()){
			if(comp instanceof ReportForm){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public AbstractJPanel getComponent() {
		return component;
	}

}
