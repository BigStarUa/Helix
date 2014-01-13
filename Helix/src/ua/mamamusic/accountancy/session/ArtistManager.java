package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Query;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.ArtistAlias;

public interface ArtistManager {

	public Artist findArtistById(long id);
	
	public void saveNewArtist(Artist product);
	
	public void mergeArtist(Artist product);
	
	public void updateArtist(Artist product);
	
	public void deleteArtist(Artist product);
	
	public List<Artist> loadAllArtists();
	
	public List<Artist> loadArtists(Query query);
	
	public List<Artist> loadAllArtistsOrderedBy(String criteria);
	
	public List<ArtistAlias> loadAllArtistAliases();

}
