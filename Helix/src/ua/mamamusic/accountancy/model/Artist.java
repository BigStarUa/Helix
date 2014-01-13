package ua.mamamusic.accountancy.model;

import java.util.Set;

public class Artist implements Comparable<Artist>{

	private long id;
	private String name;
	private Set<ArtistAlias> aliasSet;
	private Set<Track> trackSet;
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
	public Set<ArtistAlias> getAliasSet() {
		return aliasSet;
	}
	public void setAliasSet(Set<ArtistAlias> aliasSet) {
		this.aliasSet = aliasSet;
	}
	public Set<Track> getTrackSet() {
		return trackSet;
	}
	public void setTrackSet(Set<Track> trackSet) {
		this.trackSet = trackSet;
	}
	public String toString(){
		return name;
	}
	@Override
	public int compareTo(Artist artist) {
		return name.compareTo(artist.getName());
	}
	
	
}
