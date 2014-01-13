package ua.mamamusic.accountancy.model;

public class TrackTypeAlias {

	private long id;
	private String name;
	private TrackType type;
	
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
	public TrackType getType() {
		return type;
	}
	public void setType(TrackType type) {
		this.type = type;
	}
	public String toString(){
		return name;
	}
}
