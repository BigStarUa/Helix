package ua.mamamusic.accountancy.action;

import java.awt.event.ActionEvent;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.gui.ArtistsList;
import ua.mamamusic.accountancy.gui.DistributorsList;
import ua.mamamusic.accountancy.gui.TabPane;
import ua.mamamusic.accountancy.gui.TrackTypeList;

public class TrackTypeListOpenAction extends OpenAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractJPanel component;
	private String title;

	public TrackTypeListOpenAction(TabPane tabbedPane, String title){
		super(tabbedPane, title);
		this.title = title;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TabPane tabbedPane = getTabbedPane();
		component = TrackTypeList.getInstance();
		if(isComponentExist()){
			tabbedPane.setSelectedComponent(component);
		}else{
			tabbedPane.addClosableTab(component, title);
		}
	}
	
	@Override
	public AbstractJPanel getComponent() {
		// TODO Auto-generated method stub
		return component;
	}

}
