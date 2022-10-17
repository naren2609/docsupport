package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "cityId", unique = true, nullable = false)
    private Long cityId;

    @Column(name = "city", nullable = false, length = 30)
    private String city;

    @Column(name = "state", nullable = false, length = 30)
    private String state;

    @Column(name = "country", nullable = false, length = 30)
    private String country;

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

//    @JsonManagedReference(value="job-city")
    @RestResource(exported=false)
    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, mappedBy="city")
    private List<Job> jobList;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

}
