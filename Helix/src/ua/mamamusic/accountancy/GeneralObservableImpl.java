package ua.mamamusic.accountancy;

import java.util.LinkedList;
import java.util.List;

public class GeneralObservableImpl<T extends GeneralListener> implements GeneralObservable<T> {

	private List<T> listeners = new LinkedList<>();
	
	@Override
	public void addListener(T listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(T listener) {
		listeners.remove(listener);
	}

	@Override
	public void fireUpdate() {
		for(GeneralListener listener : listeners){
			listener.updateFired();
		}
	}

}
