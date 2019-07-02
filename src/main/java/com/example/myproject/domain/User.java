package com.example.myproject.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User extends Entities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	@Column(nullable = false,unique = true)
	private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = true)
    private String profilePicture;
    @Column(nullable = true,length = 65535,columnDefinition="Text")
    private String introduction;
    @Column(nullable = false)
    private Long createTime;
    @Column(nullable =false)
    private Long lastModifyTime;
    @Column(nullable=true)
    private String outDate;
    @Column(nullable=true)
    private String validataCode;
    @Column(nullable=true)
    private String backgroundPicture;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Party> partyCreatedList = new ArrayList<>();
   /* @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Party> partyCanceledList = new ArrayList<>();*/
    public User() {
		super();
	}
	public User(String email, String nickName, String password, String userName) {
		super();
		this.email = email;
		this.password = password;
		this.userName = userName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public String getValidataCode() {
		return validataCode;
	}
	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}
	public String getBackgroundPicture() {
		return backgroundPicture;
	}
	public void setBackgroundPicture(String backgroundPicture) {
		this.backgroundPicture = backgroundPicture;
	}
	public List<Party> getPartyCreatedList(){
		return partyCreatedList;
	}
	public void setPartyCreatedList(List<Party> partyCreatedList){
		this.partyCreatedList = partyCreatedList;
	}
	public void addToPartyCreatedList(Party party){
		partyCreatedList.add(party);
	}
	
	/*public List<Party> getPartyCanceledList(){
		return partyCanceledList;
	}
	public void setPartyCanceledList(List<Party> partyCanceledList){
		this.partyCanceledList = partyCanceledList;
	}
	public void addToPartyCanceledList(Party party){
		partyCanceledList.add(party);
	}	*/
}
