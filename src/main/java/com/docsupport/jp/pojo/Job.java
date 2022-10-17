package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table (name = "Job")
public class Job {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "jobId", unique = true, nullable = false)
	private Long jobId;
	
	@Column (name = "title", nullable = false, length = 40)
	private String title;
	
//	@Column (name = "location", nullable = false, length = 40)
//	private String location;

	@RestResource(exported=false)
//	@JsonBackReference(value="job-city")
	@ManyToOne
	@JoinColumn(name="cityId")
	private City city;
	
	@Column (name = "requirement", length = 255)
	private String requirement;

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="catId", nullable = false, updatable = false)
	@RestResource(exported=false)
//	@JsonBackReference(value="job-cat")
	@ManyToOne
	@JoinColumn(name="catId")
	private Category category;

	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "date")
	private Date date;

	@Column (name = "status", length = 40)
	private String status;

	@Column(name = "description", nullable = false, length = 500)
	private String description;

	@RestResource(exported=false)
//	@JsonManagedReference(value="job-appl")
	@OneToMany(mappedBy = "job")
	private Set<Application> application;

	@Column(name = "openings", columnDefinition = "integer default 1")
	private Integer openings;

	public Integer getOpenings() {
		return openings;
	}

	public void setOpenings(Integer openings) {
		this.openings = openings;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {

		this.category = category;

	}

	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Set<Application> getApplication() {
		return application;
	}

	public void setApplication(Set<Application> application) {
		this.application = application;
	}

	//(cascade = CascadeType.ALL)
	@RestResource(exported=false)
//	@JsonBackReference(value="emp-job")
	@ManyToOne
	@JoinColumn(name="userId", nullable = false)
	private Employer employer;
	   
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@PrePersist
	private void onCreate() {
		date = new Date();
	}


}
