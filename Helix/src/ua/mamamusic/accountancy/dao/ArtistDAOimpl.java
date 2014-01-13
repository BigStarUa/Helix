package ua.mamamusic.accountancy.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.Distributor;

public class ArtistDAOimpl extends AbstractGenericDAO<Artist, Long> implements ArtistDAO{

	@Override
	public List<ArtistAlias> findAllAliases(Class<ArtistAlias> clazz) {
			Session session = this.getSession();
			List<ArtistAlias> list;
			Query q = session.createQuery("from "+ clazz.getName());
			list = q.list();
			return list;
		
	}

}
