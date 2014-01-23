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
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.Track;

public class TrackManagerImpl implements TrackManager{

	private TrackDAO trackDAO = new TrackDAOimpl();
	public static List<Track> TRACK_LIST_ORDERED;
	@Override
	public Track findTrackById(long id) {
		Track track = null;
		try{
			HibernateUtil.beginTransaction();
			track = trackDAO.findById(Track.class, id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return track;
	}

	@Override
	public void saveNewTrack(Track track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.save(track);
			HibernateUtil.commitTransaction();
			TRACK_LIST_ORDERED.add(track);
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void deleteTrack(Track track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.delete(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
		
	}

	@Override
	public List<Track> loadAllTracks() {
		List<Track> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = trackDAO.findAll(Track.class);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
	
	@Override
	public List<Track> loadAllTracksOrderedBy(String criteria) {
		if(TRACK_LIST_ORDERED != null) return TRACK_LIST_ORDERED;
		List<Track> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(Track.class);
			c.addOrder(Order.asc(criteria));
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		TRACK_LIST_ORDERED = list;
		return TRACK_LIST_ORDERED;
	}

	@Override
	public void mergeTrack(Track track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.merge(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void updateTrack(Track track) {
		try{
			HibernateUtil.beginTransaction();
			trackDAO.update(track);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}		
	}

	@Override
	public List<Track> loadTracks(Query query) {
		List<Track> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = trackDAO.findMany(query);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}

}
