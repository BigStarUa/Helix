package ua.mamamusic.accountancy.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import ua.mamamusic.accountancy.AbstractJPanel;
import ua.mamamusic.accountancy.gui.ArtistsList;
import ua.mamamusic.accountancy.gui.DistributorsList;
import ua.mamamusic.accountancy.gui.TabPane;
import ua.mamamusic.accountancy.gui.TrackList;
import ua.mamamusic.accountancy.gui.UploadForm;

public class TrackListOpenAction extends OpenAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractJPanel component;
	private String title;

	public TrackListOpenAction(TabPane tabbedPane, String title){
		super(tabbedPane, title);
		this.title = title;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TabPane tabbedPane = getTabbedPane();
		//component = new TrackList();
		component = isExist(tabbedPane);
		if(component != null){
			tabbedPane.setSelectedComponent(component);
		}else{
			tabbedPane.addClosableTab(new TrackList(), title);
		}
	}
	
	
	private AbstractJPanel isExist(TabPane tabbedPane){
		for(Component comp : tabbedPane.getComponents()){
			if(comp instanceof TrackList){
				return (AbstractJPanel)comp;
			}
		}
		return null;
	}
	
	@Override
	public AbstractJPanel getComponent() {
		// TODO Auto-generated method stub
		return component;
	}

}
