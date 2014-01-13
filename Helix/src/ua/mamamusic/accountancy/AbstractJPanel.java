package ua.mamamusic.accountancy;

import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JToolBar;

public abstract class AbstractJPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GeneralObservable<GeneralListener> listener = new GeneralObservableImpl<>();
	
	public void addListener(GeneralListener listener) {
		this.listener.addListener(listener);
	}

	public void removeListener(GeneralListener listener) {
		this.listener.removeListener(listener);
	}

	public GeneralObservable<GeneralListener> getListener() {
		return listener;
	}

	public void setListener(GeneralObservable<GeneralListener> listener) {
		this.listener = listener;
	}
	
}
