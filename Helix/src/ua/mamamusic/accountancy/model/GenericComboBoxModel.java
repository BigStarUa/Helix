package ua.mamamusic.accountancy.model;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class GenericComboBoxModel<E> extends AbstractListModel<E> implements ComboBoxModel<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<E> list;
	E selected = null;
	String selection = null;

  
	public GenericComboBoxModel(List<E> list){
		this.list = list;
	}
  
	public E getElementAt(int index) {
		return list.get(index);
	}

	public int getSize() {
		return list.size();
	}
	
	public void setElementAt(int index, E element){
		list.set(index, element);
		fireIntervalAdded(element, index, index);
	}

	public void setSelectedItem(Object anItem) {
	    selection = ((Object) anItem).toString(); // to select and register an
	    selected = (E) anItem;
	    fireContentsChanged(anItem, 0, getSize()-1);
	} // item from the pull-down list

  // Methods implemented from the interface ComboBoxModel
	public E getSelectedItem() {
		return selected;
	}
}