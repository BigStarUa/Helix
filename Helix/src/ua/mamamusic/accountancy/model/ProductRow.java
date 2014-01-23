package ua.mamamusic.accountancy.model;

import java.util.Date;

import ua.mamamusic.accountancy.gui.UploadForm;

public class ProductRow {

	private long id;
	private Track track;
	private Artist artist;
	private TrackType type;
	private long quantity;
	private Double income;
	private Date date;
	private Distributor distributor;
	private TRightType rightType;
	private int rightPercent;
	private double artistIncome;
	private double companyIncome;
	
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
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
		artistIncome = UploadForm.round(((double)artist.getIncomePercent() / 100) * income, 2);
		companyIncome = income - artistIncome;
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
	public int getRightPercent() {
		return rightPercent;
	}
	public void setRightPercent(int rightPercent) {
		this.rightPercent = rightPercent;
	}
	public double getArtistIncome(){
		return artistIncome;
	}
	public double getCompanyIncome(){
		return companyIncome;
	}
	
}
