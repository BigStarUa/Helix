package ua.mamamusic.accountancy;

import java.awt.Window;
import java.util.Observable;

import javax.swing.JDialog;
import javax.swing.JToolBar;

public class AbstractJDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GeneralObservable<GeneralListener> listener;
	
	public AbstractJDialog(Window owner, String title, ModalityType modalityType){
		super(owner, title, modalityType);
		listener = new GeneralObservableImpl<>();
	}
	
	public void addListener(GeneralListener listener) {
		this.listener.addListener(listener);
	}

	public void removeListener(GeneralListener listener) {
		this.listener.removeListener(listener);
	}

	public GeneralObservable<GeneralListener> getListener() {
		return listener;
	}
}
