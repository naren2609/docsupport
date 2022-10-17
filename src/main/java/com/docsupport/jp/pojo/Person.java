package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Person")
@PrimaryKeyJoinColumn(name = "userId")
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "userId", scope = Person.class)
public class Person extends Credential implements Serializable {

	private static final long serialVersionUID = 6841097441101418764L;

	@NotBlank(message = "firstName is mandatory")
	@Column(name = "firstName", nullable = false, length = 20)
	private String firstName;

	@NotBlank(message = "lastName is mandatory")
	@Column(name = "lastName", nullable = false, length = 20)
	private String lastName;

	@Column(name = "streetAddress", length = 40)
	private String streetAddress;

	@Column(name = "city", length = 20)
	private String city;

	@Column(name = "state", length = 20)
	private String state;

	@Column(name = "country", length = 20)
	private String country;

	@Column(name = "zipCode", length = 20)
	private String zipCode;

	@NotBlank(message = "Email is mandatory")
	@Column(name = "emailId", unique = true, nullable = false, length = 40)
	private String emailId;

	@NotBlank(message = "Mobile is mandatory")
	@Column(name = "mobile", length = 20, unique = true, nullable = false)
	private String mobile;

	@NotNull(message = "dob is mandatory")
	@Column(name = "dob", nullable = false, length = 20)
	private Date dob;

	@NotBlank(message = "gender is mandatory")
	@Column(name = "gender", nullable = false, length = 20)
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registerDate", nullable = false)
	private Date registerDate;

	@Column(name = "totalExperience")
	private float totalExperience;

	@Column(name = "expectedSal")
	private Long expectedSal;

	@Column(name = "currentSal")
	private Long currentSal;

	@Column(name = "preferredCity", length = 20)
	private String preferredCity;

	@Column(name = "currentLocation", length = 20)
	private String currentLocation;

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "photo", length = 500)
	private byte[] photo;


	@Column(name = "highestQualification", length = 20)
	private String highestQualification;

	@Column(name = "currentCompany", length = 50)
	private String currentCompany;

	@RestResource(exported=false)
	@ManyToMany
	@JoinTable(name = "person_skill",
			joinColumns = {
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "skill_id", nullable = false, updatable = false) })
	private List<Skill> skillList;

	@RestResource(exported=false)
	@ManyToMany
	@JoinTable(name = "person_qual",
			joinColumns = {
					@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "qual_id", nullable = false, updatable = false) })
	private List<Qualification> qualList;

	@RestResource(exported=false)
//	@JsonBackReference(value="person-cat")
	@ManyToOne
	@JoinColumn(name="catId")
	private Category category;

	@Column(name = "resumeLocation", length = 200)
	private String resumeLocation;

	@RestResource(exported=false)
//	@JsonManagedReference(value="person-appl")
	@OneToMany(mappedBy = "person")
	private Set<Application> application;

	public Set<Application> getApplication() {
		return application;
	}

	public void setApplication(Set<Application> application) {
		this.application = application;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public float getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(float totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getPreferredCity() {
		return preferredCity;
	}

	public void setPreferredCity(String preferredCity) {
		this.preferredCity = preferredCity;
	}

	public Long getExpectedSal() {
		return expectedSal;
	}

	public void setExpectedSal(Long expectedSal) {
		this.expectedSal = expectedSal;
	}

	public Long getCurrentSal() {
		return currentSal;
	}

	public void setCurrentSal(Long currentSal) {
		this.currentSal = currentSal;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

	public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getResumeLocation() {
		return resumeLocation;
	}

	public void setResumeLocation(String resumeLocation) {
		this.resumeLocation = resumeLocation;
	}

	public List<Skill> getSkillList() {
		if (skillList == null) {
			return new ArrayList<Skill>();
		}
		return skillList;
	}

	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Qualification> getQualList() {
		if (qualList == null) {
			return new ArrayList<Qualification>();
		}
		return qualList;
	}

	public void setQualList(List<Qualification> qualList) {
		this.qualList = qualList;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@PrePersist
	private void onCreate() {
		registerDate = new Date();
	}
}
