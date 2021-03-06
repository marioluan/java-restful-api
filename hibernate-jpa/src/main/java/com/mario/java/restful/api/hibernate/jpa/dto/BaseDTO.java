package com.mario.java.restful.api.hibernate.jpa.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Base DTO. </br>
 * <strong>All DTOs should extend this class.</strong>
 * @author msouz23
 */
public abstract class BaseDTO extends DatedDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * Since all DTOs may extend this class,
	 * we need to prevent them from serializing these property.
	 */
	@JsonIgnore
	private List<String> propertiesToBeDisplayed;

	/**
	 * @return the id
	 */
	public Long getId(){
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Returns the list of properties which should be displayed when serializing the instance.
	 * @return the propertiesToBeDisplayed
	 */
	public List<String> getPropertiesToBeDisplayed(){
		return this.propertiesToBeDisplayed;
	}

	/**
	 * Sets the list of properties which should be displayed when serializing the instance.
	 * @param propertiesToBeDisplayed the propertiesToBeDisplayed to set
	 */
	public void setPropertiesToBeDisplayed(List<String> propertiesToBeDisplayed){
		this.propertiesToBeDisplayed = propertiesToBeDisplayed;
	}
}