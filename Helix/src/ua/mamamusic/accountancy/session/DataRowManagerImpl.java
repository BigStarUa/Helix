package ua.mamamusic.accountancy.session;


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ua.mamamusic.accountancy.HibernateUtil;
import ua.mamamusic.accountancy.dao.ArtistDAO;
import ua.mamamusic.accountancy.dao.ArtistDAOimpl;
import ua.mamamusic.accountancy.dao.DataRowDAO;
import ua.mamamusic.accountancy.dao.DataRowDAOimpl;
import ua.mamamusic.accountancy.dao.TrackDAO;
import ua.mamamusic.accountancy.dao.TrackDAOimpl;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;

public class DataRowManagerImpl implements DataRowManager{

	private DataRowDAO trackDAO = new DataRowDAOimpl();
	
	@Override
	public DataRow findDataRowById(long id) {
		DataRow row = null;
		try{
			HibernateUtil.beginTransaction();
			row = trackDAO.findById(DataRow.class, id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return row;
	}

	@Override
	public void saveNewDataRow(DataRow row) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.save(row);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}
	
	@Override
	public void saveNewDataRowList(List<DataRow> list) {
		try{
			HibernateUtil.beginTransaction();
			for(DataRow row : list){
				trackDAO.save(row);
			}
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void deleteDataRow(DataRow row) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.delete(row);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
		
	}
	
	@Override
	public List<DataRow> loadAllDataRowsByPeriod(Date start, Date end, Distributor distributor) {
		List<DataRow> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(DataRow.class);
			c.add( Restrictions.between("date", start, end) );
			c.add( Restrictions.eq("distributor", distributor) );
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
	
	@Override
	public List<Object[]> loadDataRowsByCriterias(Artist[] artList, Distributor distributor,
			TrackType type, Date start, Date end, boolean groupDistrib, boolean groupType) {
		List<Object[]> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(DataRow.class);
			
			c.add( Restrictions.between("date", start, end) );
			
			if(distributor != null){
				c.add( Restrictions.eq("distributor", distributor) );
			}
			if(artList != null){
				c.add( Restrictions.in("artist", artList) );
			}
			if(type != null){
				c.add( Restrictions.eq("type", type) );
			}
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("artist"));
			projList.add(Projections.groupProperty("track"));
			if(groupType){
				projList.add(Projections.groupProperty("type"));
			}
			if(groupDistrib){
				projList.add(Projections.groupProperty("distributor"));
			}
			projList.add(Projections.sum("quantity"));
			projList.add(Projections.sum("income"));

			c.setProjection(projList);
			
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
}
