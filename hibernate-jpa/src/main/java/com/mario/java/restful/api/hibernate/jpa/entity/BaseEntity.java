package com.mario.java.restful.api.hibernate.jpa.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Base entity which should be extended by all entities from the application.
 * @author marioluan
 */
@MappedSuperclass
public abstract class BaseEntity extends DatedEntity {

	/**
	 * Primary key
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * Properties from the entity which might be serialized
	 */
	@Transient
	private List<String> propertiesToBeDisplayed;

	/**
	 * @return the propertiesToBeDisplayed
	 */
	public List<String> getPropertiesToBeDisplayed() {
		return this.propertiesToBeDisplayed;
	}

	/**
	 * @param propertiesToBeDisplayed the propertiesToBeDisplayed to set
	 */
	public void setPropertiesToBeDisplayed(List<String> propertiesToBeDisplayed) {
		this.propertiesToBeDisplayed = propertiesToBeDisplayed;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
