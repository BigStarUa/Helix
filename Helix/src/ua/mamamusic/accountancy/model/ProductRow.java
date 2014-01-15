package ua.mamamusic.accountancy.model;

import java.util.Date;

public class ProductRow {

	private long id;
	private Track track;
	private Artist artist;
	private TrackType type;
	private int quantity;
	private Double income;
	private Date date;
	private Distributor distributor;
	private TRightType rightType;
	
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
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	public TRightType getRightType() {
		return rightType;
	}
	public void setRightType(TRightType rightType) {
		this.rightType = rightType;
	}
	
	
}
