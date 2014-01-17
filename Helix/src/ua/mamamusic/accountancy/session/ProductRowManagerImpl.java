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
import ua.mamamusic.accountancy.dao.ProductRowDAO;
import ua.mamamusic.accountancy.dao.ProductRowDAOimpl;
import ua.mamamusic.accountancy.dao.TrackDAO;
import ua.mamamusic.accountancy.dao.TrackDAOimpl;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.DataRow;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.ProductRow;
import ua.mamamusic.accountancy.model.TRightType;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;

public class ProductRowManagerImpl implements ProductRowManager{

	private ProductRowDAO productRowDAO = new ProductRowDAOimpl();
	
	@Override
	public ProductRow findDataRowById(long id) {
		ProductRow row = null;
		try{
			HibernateUtil.beginTransaction();
			row = productRowDAO.findById(ProductRow.class, id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return row;
	}

	@Override
	public void saveNewDataRow(ProductRow row) {
		try{
			HibernateUtil.beginTransaction();
			productRowDAO.save(row);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}
	
	@Override
	public void saveNewDataRowList(List<ProductRow> list) {
		try{
			HibernateUtil.beginTransaction();
			for(ProductRow row : list){
				productRowDAO.save(row);
			}
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void deleteDataRow(ProductRow row) {
		try{
			HibernateUtil.beginTransaction();
			productRowDAO.delete(row);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
		
	}
	
	@Override
	public List<ProductRow> loadAllDataRowsByPeriod(Date start, Date end, Distributor distributor) {
		List<ProductRow> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(ProductRow.class);
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
			Criteria c = HibernateUtil.getSession().createCriteria(ProductRow.class);
			
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
	
	@Override
	public List<Object[]> loadData(Artist[] artList, Distributor distributor, TrackType type, Date start,
			Date end, boolean groupDistrib, boolean groupType, boolean groupDate, boolean groupRight, TRightType rightType) {
		List<Object[]> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(ProductRow.class);
			
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
			if(rightType != null){
				c.add( Restrictions.eq("rightType", rightType) );
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
			//projList.add(Projections.groupProperty("quantity"));
			projList.add(Projections.sum("income"));
			if(groupRight){
				projList.add(Projections.groupProperty("rightType"));
			}
			if(groupDate){
				projList.add(Projections.groupProperty("date"));
			}
			c.setProjection(projList);
			
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}

	@Override
	public void deleteProductRowList(List<ProductRow> list) {
		try{
			HibernateUtil.beginTransaction();
			for(ProductRow row : list){
				productRowDAO.delete(row);
			}
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}
}
