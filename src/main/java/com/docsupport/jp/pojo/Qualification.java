package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Qualification")
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "qualId", unique = true, nullable = false)
    private Long qualId;

    @Column(name = "qualName", unique = true, nullable = false, length = 35)
    private String qualName;

    @RestResource(exported=false)
    @JsonIgnore
    @ManyToMany(mappedBy = "qualList")
    private List<Person> personList;

    public Long getQualId() {
        return qualId;
    }

    public void setQualId(Long qualId) {
        this.qualId = qualId;
    }

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
