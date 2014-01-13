package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

public class GenericListModel<E> extends AbstractListModel<E> implements
		ListModel<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<E> list;
	
	public GenericListModel(List<E> list){
		this.list = list;
	}
	@Override
	public E getElementAt(int index) {
		return list.get(index);
	}

	@Override
	public int getSize() {
		return list.size();
	}

	public void addElement(E element){
		list.add(element);
		fireIntervalAdded(element, getSize(), getSize());
	}
	
	public void repaintObjectInList(E element){
		int index = list.indexOf(element);
		fireContentsChanged(element, index, index);
	}
	
	public List<E> getList(){
		return list;
	}
	
	public void removeElement(int index){
		list.remove(index);
		fireIntervalRemoved(list, index, index);
	}
	
	public boolean contains(E element){
		
		if(list.contains(element)) return true;
		return false;
	}
}
