package ua.mamamusic.accountancy.session;


import java.util.List;

import org.hibernate.Query;

import ua.mamamusic.accountancy.model.TrackType;

public interface TrackTypeManager {

	public TrackType findTrackById(long id);
	
	public void saveNewTrack(TrackType track);
	
	public void mergeTrack(TrackType track);
	
	public void updateTrack(TrackType track);
	
	public void deleteTrack(TrackType track);
	
	public List<TrackType> loadAllTrackTypesOrderedBy(String criteria);

}
