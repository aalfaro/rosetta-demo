package com.rosetta.demo.components.categorie.entity;

public class Category {
	
	
	private String identifier;
	private String name;
	private String productsURL;
	private String resourceId;
	private String shortDescription;
	private String thumbnail;
	private long uniqueID;
	
	/**
	 * 
	 */
	public Category() {
		super();
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the productsURL
	 */
	public String getProductsURL() {
		return productsURL;
	}

	/**
	 * @param productsURL the productsURL to set
	 */
	public void setProductsURL(String productsURL) {
		this.productsURL = productsURL;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shortDescription the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the uniqueID
	 */
	public long getUniqueID() {
		return uniqueID;
	}

	/**
	 * @param uniqueID the uniqueID to set
	 */
	public void setUniqueID(long uniqueID) {
		this.uniqueID = uniqueID;
	}	

}
