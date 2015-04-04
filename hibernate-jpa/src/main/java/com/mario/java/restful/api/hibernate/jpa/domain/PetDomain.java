package com.mario.java.restful.api.hibernate.jpa.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Domain class which represents a pet on database.
 * @author marioluan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "pets")
public class PetDomain extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    @Range(min = 1, max = 100)
    private int age;

    @NotNull
    @ManyToOne(optional=false)
    @PrimaryKeyJoinColumn
    private UserDomain user;

    @Transient
    private Long userId;

    /**
     * Default constructor, creates an empty instance.
     */
    public PetDomain(){}

    /**
     * @param name the name to set
     * @param age the age to set
     */
    public PetDomain(String name, int age) {
        this.name = name;
        this.age = age;
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

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @param user the user to set
     */
    private void setUser(UserDomain user) {
        this.user = user;
    }

    /**
     * @return the user
     */
    private UserDomain getUser() {
        return this.user;
    }

    /**
     * @return the userId
     */
    public Long getUserId(){
        UserDomain user = this.getUser();
        if(user != null) return user.getId();
        else return null;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId){
        UserDomain user = new UserDomain();
        user.setId(userId);
        this.setUser(user);
        this.userId = userId;
    }

    /**
     * Fill all null/missing attributes from the current {@link PetDomain} petDomain instance with attributes from another {@link PetDomain} petDomain instance.
     *
     * @param petDomain the petDomain to take the attributes from
     */
    public void patch(PetDomain petDomain) {
        if (this.getName() == null) {
            this.setName(petDomain.getName());
        }

        if (this.getAge() < 1) {
            this.setAge(petDomain.getAge());
        }

        if (this.getUserId() == null) {
            this.setUserId(petDomain.getUserId());
        }

        this.setCreatedAt(petDomain.getCreatedAt());
    }
}