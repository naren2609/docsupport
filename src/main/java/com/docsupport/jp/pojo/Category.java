package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category{

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "catId", unique = true, nullable = false)
    private Long catId;

    @Column(name = "catName", unique = true, nullable = false, length = 30)
    private String catName;

//    @JsonManagedReference(value="job-cat")
    @RestResource(exported=false)
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER, mappedBy="category")
    private Set<Job> jobList;

//    @JsonManagedReference(value="person-cat")
    @RestResource(exported=false)
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER, mappedBy="category")
    private Set<Person> personList;

    public Set<Job> getJobList() {
        return jobList;
    }

    public void setJobList(Set<Job> jobList) {
        this.jobList = jobList;
    }

    public Set<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(Set<Person> personList) {
        this.personList = personList;
    }
}
