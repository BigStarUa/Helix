package ua.mamamusic.accountancy.model;

import java.util.Set;

public class TrackType implements Comparable<TrackType>{

	private long id;
	private String name;
	private Set<TrackTypeAlias> typeSet;
	
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
	public Set<TrackTypeAlias> getTypeSet() {
		return typeSet;
	}
	public void setTypeSet(Set<TrackTypeAlias> typeSet) {
		this.typeSet = typeSet;
	}
	public String toString(){
		return name;
	}
	@Override
	public int compareTo(TrackType type) {
		return name.compareTo(type.getName());
	}
}
