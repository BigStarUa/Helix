package ua.mamamusic.accountancy.session;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.ProductRow;
import ua.mamamusic.accountancy.model.TRightType;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;

public interface ProductRowManager {

	public ProductRow findDataRowById(long id);
	
	public void saveNewDataRow(ProductRow row);
	
	public void saveNewDataRowList(List<ProductRow> list);
	
	public void deleteDataRow(ProductRow row);
	
	public void deleteProductRowList(List<ProductRow> list);

	public List<ProductRow> loadAllDataRowsByPeriod(Date start, Date end, Distributor distributor);
	
	public List<Object[]> loadDataRowsByCriterias(Artist[] artList, Distributor distributor, TrackType type, Date start, Date end, boolean groupDistrib, boolean groupType);

	public List<Object[]> loadData(Artist[] artList, Distributor distributor, TrackType type, Date start, Date end, boolean groupDistrib, boolean groupType, boolean groupDate, boolean groupRight, TRightType rightType);
}
