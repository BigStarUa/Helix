package ua.mamamusic.accountancy.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.gui.DistributorsList;
import ua.mamamusic.accountancy.gui.TabPane;
import ua.mamamusic.accountancy.gui.TrackList;
import ua.mamamusic.accountancy.gui.UploadForm;

public class UploadFormOpenAction extends OpenAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractJPanel component;
	private String title;

	public UploadFormOpenAction(TabPane tabbedPane, String title){
		super(tabbedPane, title);
		this.title = title;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TabPane tabbedPane = getTabbedPane();
		//component = UploadForm.getInstance();
		component = isExist(tabbedPane);
		if(component != null){
			tabbedPane.setSelectedComponent(component);
		}else{
			tabbedPane.addClosableTab(new UploadForm(), title);
		}
	}
	
	
	private AbstractJPanel isExist(TabPane tabbedPane){
		for(Component comp : tabbedPane.getComponents()){
			if(comp instanceof UploadForm){
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
