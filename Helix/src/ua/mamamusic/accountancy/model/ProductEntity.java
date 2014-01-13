package ua.mamamusic.accountancy.model;

public class ProductEntity {

	private long id;
	private Track track;
	private String columnArtist;
	private String columnTrack;
	private String columnPrice;
	private Artist artist;
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
	public String getColumnArtist() {
		return columnArtist;
	}
	public void setColumnArtist(String columnArtist) {
		this.columnArtist = columnArtist;
	}
	public String getColumnTrack() {
		return columnTrack;
	}
	public void setColumnTrack(String columnTrack) {
		this.columnTrack = columnTrack;
	}
	public String getColumnPrice() {
		return columnPrice;
	}
	public void setColumnPrice(String columnPrice) {
		this.columnPrice = columnPrice;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
}
