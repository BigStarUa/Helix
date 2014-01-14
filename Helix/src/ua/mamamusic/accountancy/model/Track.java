package ua.mamamusic.accountancy.model;

import java.util.Set;

public class Track implements Comparable<Track> {

	private long id;
	private String name;
	//private Artist artist;
	private Set<TrackAlias> aliasSet;
	private Set<TRight> rightSet;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Artist getArtist() {
//		return artist;
//	}
//	public void setArtist(Artist artist) {
//		this.artist = artist;
//	}
	public Set<TrackAlias> getAliasSet() {
		return aliasSet;
	}
	public void setAliasSet(Set<TrackAlias> aliasSet) {
		this.aliasSet = aliasSet;
	}
	public String toString(){
		return name;
	}
	@Override
	public int compareTo(Track track) {
		return name.compareTo(track.getName());
	}
	public Set<TRight> getRightSet() {
		return rightSet;
	}
	public void setRightSet(Set<TRight> rightSet) {
		this.rightSet = rightSet;
	}
}
