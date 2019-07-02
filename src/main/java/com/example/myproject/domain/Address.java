package com.example.myproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address extends Entities implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=true)
	private String country;
	@Column(nullable=true)
	private String province;
	@Column(nullable=true)
	private String city;
	@Column(nullable=true)
	private String district;
	@Column(nullable=true)
	private String street;
	@Column(nullable=true)
	private String buildingNumber;
	@Column(nullable=true)
	private String extraInfo;
   
	public Address(){
		super();
	}
	public Address(String country, String province, String city, String district, String street,
			String buildingNumber, String extraInfo) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
		this.district = district;
		this.street = street;
		this.buildingNumber = buildingNumber;
		this.extraInfo = extraInfo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBuildingNumber() {
		return buildingNumber;
	}
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
}
