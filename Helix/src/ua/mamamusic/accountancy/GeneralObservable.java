package ua.mamamusic.accountancy;

public interface GeneralObservable<T> {
	
	public void addListener(T listener);
	
	public void removeListener(T listener);
	
	public void fireUpdate();
}
