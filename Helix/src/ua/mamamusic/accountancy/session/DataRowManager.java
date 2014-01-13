package ua.mamamusic.accountancy.session;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;

public interface DataRowManager {

	public DataRow findDataRowById(long id);
	
	public void saveNewDataRow(DataRow row);
	
	public void saveNewDataRowList(List<DataRow> list);
	
	public void deleteDataRow(DataRow row);

	public List<DataRow> loadAllDataRowsByPeriod(Date start, Date end, Distributor distributor);
	
	public List<Object[]> loadDataRowsByCriterias(Artist[] artList, Distributor distributor, TrackType type, Date start, Date end, boolean groupDistrib, boolean groupType);

}
