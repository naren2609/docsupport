package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "application",uniqueConstraints =
		{
				@UniqueConstraint(name = "UniqueUserAndJob", columnNames = { "person_user_id", "job_job_id" })
		})
public class Application {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "applyId", unique = true, nullable = false)
	private Long applyId;

//	@JsonBackReference(value="person-appl")
//	@RestResource(exported=false)
	@JsonIgnore
	@ManyToOne
	private Person person;

//	@RestResource(exported=false)
//	@JsonBackReference(value="job-apßpl")
	@JsonIgnore
	@ManyToOne
	private Job job;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appliedDate", nullable = false)
	private Date appliedDate;

	@Column(name = "applicationStatus", columnDefinition = "boolean default true")
	private Boolean applicationStatus = true;

	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public Boolean getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(Boolean applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	@PrePersist
	private void onCreate() {
		appliedDate = new Date();
	}
}
