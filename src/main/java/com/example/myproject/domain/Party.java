package com.example.myproject.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Party extends Entities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=true)
	private String partyStartTime;
	@Column(nullable=true)
	private String partyEndTime;
	@Column(nullable=true)
	private String partyAddress;
	@Column(nullable=true)
	private String headCount;
	@Column(nullable=true)
	private String partyType;
	@Column(nullable=true)
	private String estimateCost;
	@Column(nullable=true)
    private String partyDescription;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "userId",referencedColumnName = "id")
	private User user;
	
	public Party(){
		super();
	}
	public Party(String partyStartTime,String partyEndTime,String partyAddress,String headCount,String partyType,String estimateCost,String partyDescription,User user){
		super();
		this.partyStartTime = partyStartTime;
		this.partyEndTime = partyEndTime;
		this.partyAddress = partyAddress;
		this.headCount = headCount;
		this.partyType = partyType;
		this.estimateCost = estimateCost;
		this.partyDescription = partyDescription;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPartyStartTime() {
		return partyStartTime;
	}
	public void setPartyStartTime(String partyStartTime) {
		this.partyStartTime = partyStartTime;
	}
	public String getPartyEndTime() {
		return partyEndTime;
	}
	public void setPartyEndTime(String partyEndTime) {
		this.partyEndTime = partyEndTime;
	}
	public String getPartyAddress() {
		return partyAddress;
	}
	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}
	public String getHeadCount() {
		return headCount;
	}
	public void setHeadCount(String headCount) {
		this.headCount = headCount;
	}
	public String getPartyType() {
		return partyType;
	}
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	public String getEstimateCost() {
		return estimateCost;
	}
	public void setEstimateCost(String estimateCost) {
		this.estimateCost = estimateCost;
	}
	public String getPartyDescription() {
		return partyDescription;
	}
	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
}
