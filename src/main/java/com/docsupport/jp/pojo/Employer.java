package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Employer")
@PrimaryKeyJoinColumn(name= "userId")
public class Employer extends Credential implements Serializable{
	public void setJobList(Set<Job> jobList) {
		this.jobList = jobList;
	}

	private static final long serialVersionUID = -2183702379075362120L;

	@NotBlank(message = "employerName is mandatory")
	@Column(name = "employerName", nullable = false, length = 40)
	private String employerName;

	@NotBlank(message = "personName is mandatory")
	@Column(name = "personName", nullable = false, length = 40)
	private String personName;
	
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
	
	@Column(name = "websiteUrl", length = 20)
	private String websiteUrl;

	@NotBlank(message = "Email is mandatory")
	@Column(name = "emailId", unique = true, nullable = false, length = 40)
	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@NotBlank(message = "mobile is mandatory")
	@Column(name = "mobile", unique = true, nullable = false, length = 20)
	private String mobile;

	@NotBlank(message = "description is mandatory")
	@Column(name = "description", nullable = false, length = 500)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registerDate", length = 20)
	private Date registerDate;

//	@JsonManagedReference(value="emp-job")
//	@RestResource(exported=false)
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="employer")
	private Set<Job> jobList;
	
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
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
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Set<Job> getJobList() {
		if(jobList == null){
			return new HashSet<Job>();
		}
		return jobList;
	}

	@PrePersist
	private void onCreate() {
		registerDate = new Date();
	}
}
