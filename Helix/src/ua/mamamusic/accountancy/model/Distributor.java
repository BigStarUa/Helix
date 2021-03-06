package ua.mamamusic.accountancy.model;

import java.util.Set;

public class Distributor {

	private long id;
	private String name;
	private String ruleFilePath;
	private int columnCount;
	private int columnId;
	private int columnPrice;
	private int columnArtist;
	private int columnTrack;
	private int columnTrackType;
	private int columnQuantity;
	private Set<DistributorAlias> aliasSet;
	
	
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
	
	public String toString(){
		return name;
	}
	public String getRuleFilePath() {
		return ruleFilePath;
	}
	public void setRuleFilePath(String rule) {
		this.ruleFilePath = rule;
	}
	public int getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	public int getColumnId() {
		return columnId;
	}
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	public int getColumnPrice() {
		return columnPrice;
	}
	public void setColumnPrice(int columnPrice) {
		this.columnPrice = columnPrice;
	}
	public Set<DistributorAlias> getAliasSet() {
		return aliasSet;
	}
	public void setAliasSet(Set<DistributorAlias> aliasSet) {
		this.aliasSet = aliasSet;
	}
	public int getColumnArtist() {
		return columnArtist;
	}
	public void setColumnArtist(int columnArtist) {
		this.columnArtist = columnArtist;
	}
	public int getColumnTrack() {
		return columnTrack;
	}
	public void setColumnTrack(int columnTrack) {
		this.columnTrack = columnTrack;
	}
	public int getColumnTrackType() {
		return columnTrackType;
	}
	public void setColumnTrackType(int columnTrackType) {
		this.columnTrackType = columnTrackType;
	}
	public int getColumnQuantity() {
		return columnQuantity;
	}
	public void setColumnQuantity(int columnQuantity) {
		this.columnQuantity = columnQuantity;
	}
	
	
}
