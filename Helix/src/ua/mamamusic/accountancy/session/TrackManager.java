package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Query;

import ua.mamamusic.accountancy.model.Artist;
import ua.mamamusic.accountancy.model.Distributor;
import ua.mamamusic.accountancy.model.Track;

public interface TrackManager {

	public Track findTrackById(long id);
	
	public void saveNewTrack(Track track);
	
	public void mergeTrack(Track track);
	
	public void updateTrack(Track track);
	
	public void deleteTrack(Track track);
	
	public List<Track> loadAllTracks();
	
	public List<Track> loadTracks(Query query);
	
	public List<Track> loadAllTracksOrderedBy(String criteria);

}
