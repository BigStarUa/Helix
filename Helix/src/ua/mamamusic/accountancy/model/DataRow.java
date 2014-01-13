package ua.mamamusic.accountancy.model;

import java.util.Date;

public class DataRow {

	private long id;
	private Artist artist;
	private Track track;
	private TrackType type;
	private int quantity;
	private Double income;
	private Date date;
	private Distributor distributor;
	
	private String columnArtist;
	private String columnTrack;
	private String columnPrice;
	private String columnQuantity;
	private String columnTrackType;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public Track getTrack() {
		return track;
	}
	public void setTrack(Track track) {
		this.track = track;
	}
	public TrackType getType() {
		return type;
	}
	public void setType(TrackType type) {
		this.type = type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getColumnQuantity() {
		return columnQuantity;
	}
	public void setColumnQuantity(String columnQuantity) {
		this.columnQuantity = columnQuantity;
	}
	public String getColumnTrackType() {
		return columnTrackType;
	}
	public void setColumnTrackType(String columnTrackType) {
		this.columnTrackType = columnTrackType;
	}
	public boolean isValid() {
		if(getArtist() != null && getTrack() != null && getType() != null) return true;
		
		return false;
	}
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

}
