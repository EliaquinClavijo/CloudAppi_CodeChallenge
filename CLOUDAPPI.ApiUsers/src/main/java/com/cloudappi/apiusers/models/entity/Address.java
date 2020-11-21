package com.cloudappi.apiusers.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {

	private static final long serialVersionUID = 8912878137036215235L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=true, unique=false)
	private String street;
	
	@Column(nullable=true, unique=false)
	private String state;
	
	@Column(nullable=true, unique=false)
	private String city;
	
	@Column(nullable=true, unique=false)
	private String country;
	
	@Column(nullable=true, unique=false)
	private String zip;
		

	public Address() {
	}

	public Address(Long id, String street, String state, String city, String country, String zip) {
		super();
		this.id = id;
		this.street = street;
		this.state = state;
		this.city = city;
		this.country = country;
		this.zip = zip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
