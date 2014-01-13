package ua.mamamusic.accountancy.dao;


import java.util.List;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;
import ua.mamamusic.accountancy.model.Distributor;

public interface ArtistDAO extends GenericDAO<Artist, Long> {

	public List<ArtistAlias> findAllAliases(Class<ArtistAlias> clazz);
}
