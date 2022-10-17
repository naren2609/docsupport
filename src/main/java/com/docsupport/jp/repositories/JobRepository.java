package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Job;
import com.docsupport.jp.pojo.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface JobRepository extends PagingAndSortingRepository<Job, Long> {
    @RestResource(path = "loc", rel = "location")
    Page<Job> findByCity_CityId(Long cityId, Pageable pageable);
    @RestResource(path = "cat", rel = "category")
    Page<Job> findByCategory_CatId(Long catId, Pageable pageable);
    @RestResource(path = "catandloc", rel = "CategoryAndLocation")
    Page<Job> findByCategory_CatIdAndCity_CityId(Long catId, Long cityId, Pageable pageable);

    //Get JOBS applied by particular Person
    @RestResource(path = "jobsapplied", rel = "JobsAppliedByPerson")
    @Query("SELECT DISTINCT j FROM Job j JOIN j.application a JOIN a.person p WHERE p.userId = ?1")
    Page<Job> findAllJobsAppliedByPerson(Long userId, Pageable pageable);

    //Find All JOBS by Search
    @RestResource(path = "jobssearch", rel = "JobsSearch") // OR ?2 is null OR ?2 = ''
    @Query("SELECT DISTINCT j FROM Job j WHERE (j.category.catId = ?1 OR ?1 is null OR ?1 = '' ) " +
            "AND (j.city.cityId = ?2 OR ?2 is null OR ?2 = '' ) " +
            "AND (j.description like %?3% ) " +
            "AND (j.requirement like %?4% ) " +
            "AND (j.employer.userId = ?5 OR ?5 is null OR ?5 = '' )")
    Page<Job> findAllJobsBySearch(Long catId, Long cityId, String desc, String req, Long empId, Pageable pageable);
}
