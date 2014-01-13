package ua.mamamusic.accountancy.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class GenericListModelFilter<E> extends AbstractListModel<E>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static interface Filter {
        boolean accept(Object element);
    }

    private final GenericListModel<E> _source;
    private Filter _filter;
    private final ArrayList<Integer> _indices = new ArrayList<Integer>();

    public GenericListModelFilter(GenericListModel<E> source) {
        if (source == null)
            throw new IllegalArgumentException("Source is null");
        _source = source;
        _source.addListDataListener(new ListDataListener() {
            public void intervalRemoved(ListDataEvent e) {
                doFilter();
            }

            public void intervalAdded(ListDataEvent e) {
                doFilter();
            }

            public void contentsChanged(ListDataEvent e) {
                doFilter();
            }
        });
    }

    public void setFilter(Filter f) {
        _filter = f;
        doFilter();
    }

    private void doFilter() {
        _indices.clear();

        Filter f = _filter;
        if (f != null) {
            int count = _source.getSize();
            for (int i = 0; i < count; i++) {
                Object element = _source.getElementAt(i);
                if (f.accept(element)) {
                    _indices.add(i);
                }
            }
            fireContentsChanged(this, 0, getSize() - 1);
        }
    }

    public int getSize() {
        return (_filter != null) ? _indices.size() : _source.getSize();
    }

    public E getElementAt(int index) {
        return (_filter != null) ? _source.getElementAt(_indices.get(index)) : _source.getElementAt(index);
    }
    
    public void addElement(E element) {
        _source.addElement(element);
        fireIntervalAdded(_source, getSize()-1, getSize()-1);
    }
}