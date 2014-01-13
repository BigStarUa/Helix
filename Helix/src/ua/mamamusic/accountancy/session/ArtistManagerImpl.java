package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

import ua.mamamusic.accountancy.HibernateUtil;
import ua.mamamusic.accountancy.dao.ArtistDAO;
import ua.mamamusic.accountancy.dao.ArtistDAOimpl;
import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;

public class ArtistManagerImpl implements ArtistManager{

	private ArtistDAO artistDAO = new ArtistDAOimpl();
	public static List<Artist> ARTIST_LIST_ORDERED;
	
	@Override
	public Artist findArtistById(long id) {
		Artist artist = null;
		try{
			HibernateUtil.beginTransaction();
			artist = artistDAO.findById(Artist.class, id);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return artist;
	}

	@Override
	public void saveNewArtist(Artist artist) {
		try{
			HibernateUtil.beginTransaction();
			artistDAO.save(artist);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void deleteArtist(Artist artist) {
		try{
			HibernateUtil.beginTransaction();
			artistDAO.delete(artist);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
		
	}

	@Override
	public List<Artist> loadAllArtists() {
		List<Artist> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = artistDAO.findAll(Artist.class);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
	
	@Override
	public List<Artist> loadAllArtistsOrderedBy(String criteria) {
		
		if(ARTIST_LIST_ORDERED != null) return ARTIST_LIST_ORDERED;
		
		List<Artist> list = null;
		try{
			HibernateUtil.beginTransaction();
			Criteria c = HibernateUtil.getSession().createCriteria(Artist.class);
			c.addOrder(Order.asc(criteria));
			list = c.list();
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		ARTIST_LIST_ORDERED = list;
		return ARTIST_LIST_ORDERED;
	}

	@Override
	public void mergeArtist(Artist artist) {
		try{
			HibernateUtil.beginTransaction();
			artistDAO.merge(artist);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public void updateArtist(Artist artist) {
		try{
			HibernateUtil.beginTransaction();
			artistDAO.update(artist);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			HibernateUtil.rollbackTransaction();
		}		
	}

	@Override
	public List<Artist> loadArtists(Query query) {
		List<Artist> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = artistDAO.findMany(query);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}
	
	@Override
	public List<ArtistAlias> loadAllArtistAliases() {
		List<ArtistAlias> list = null;
		try{
			HibernateUtil.beginTransaction();
			list = artistDAO.findAllAliases(ArtistAlias.class);
			HibernateUtil.commitTransaction();
		}catch(HibernateException he){
			
		}
		return list;
	}

}
