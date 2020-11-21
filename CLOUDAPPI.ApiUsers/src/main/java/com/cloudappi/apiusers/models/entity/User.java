package com.cloudappi.apiusers.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = -6771954726572485415L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=true, unique=false)
	private String name;
	
	@Column(nullable=true, unique=false)
	private String email;
	
	@Column(nullable=true, unique=false)
	private LocalDateTime birthDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Address address;	

	public User() {

	}

	public User(String name, String email, LocalDateTime birthDate, Address address) {
		super();
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.address = address;
	}

	public User(Long id, String name, String email, LocalDateTime birthDate, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
