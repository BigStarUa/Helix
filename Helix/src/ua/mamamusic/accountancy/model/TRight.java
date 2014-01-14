package ua.mamamusic.accountancy.model;

public class TRight {

	private long id;
	private Track track;
	private Artist artist;
	private int author;
	private int related;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Track getTrack() {
		return track;
	}
	public void setTrack(Track track) {
		this.track = track;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public int getRelated() {
		return related;
	}
	public void setRelated(int related) {
		this.related = related;
	}
}
