package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

import ua.mamamusic.accountancy.HibernateUtil;
import ua.mamamusic.accountancy.dao.ArtistDAO;
import ua.mamamusic.accountancy.dao.ArtistDAOimpl;
import ua.mamamusic.accountancy.dao.TrackDAO;
import ua.mamamusic.accountancy.dao.TrackDAOimpl;
import ua.mamamusic.accountancy.dao.TrackTypeDAO;
import ua.mamamusic.accountancy.dao.TrackTypeDAOimpl;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.Track;
import ua.mamamusic.accountancy.model.TrackType;

public class TrackTypeManagerImpl implements TrackTypeManager{

	private TrackTypeDAO trackDAO = new TrackTypeDAOimpl();
	public static List<TrackType> TRACK_TYPE_LIST_ORDERED;
	
	@Override
	public TrackType findTrackById(long id) {
		TrackType track = null;
		try{
			HibernateUtil.beginTransaction();
			track = trackDAO.findById(TrackType.class, id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return track;
	}

	@Override
	public void saveNewTrack(TrackType track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.save(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void deleteTrack(TrackType track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.delete(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
		
	}
	
	@Override
	public List<TrackType> loadAllTrackTypesOrderedBy(String criteria) {
		if(TRACK_TYPE_LIST_ORDERED != null) return TRACK_TYPE_LIST_ORDERED;
		
		List<TrackType> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(TrackType.class);
			c.addOrder(Order.asc(criteria));
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		TRACK_TYPE_LIST_ORDERED = list;
		return TRACK_TYPE_LIST_ORDERED;
	}

	@Override
	public void mergeTrack(TrackType track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.merge(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void updateTrack(TrackType track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.update(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}		
	}

}
