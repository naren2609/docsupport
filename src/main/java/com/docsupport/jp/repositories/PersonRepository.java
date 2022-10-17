package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Job;
import com.docsupport.jp.pojo.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    @RestResource(path = "totalexpbw", rel = "totalexperiancebetween")
    Page<Person> findByTotalExperienceBetween(Float start, Float end, Pageable pageable);
    @RestResource(path = "perferredcityin", rel = "perferredcityin")
    Page<Person> findByPreferredCityIn(List<String> locations, Pageable pageable);
    @RestResource(path = "highqualin", rel = "highestqualificationin")
    Page<Person> findByHighestQualificationIn(List<String> qualifications, Pageable pageable);

    @RestResource(path = "skillidsin", rel = "skillidsin")
    @Query("select p from Person p left join p.skillList q where q.skillId  in ?1")
    Page<Person> findBySkillsIn(List<Long> skillIds, Pageable pageable);


    @Override
    @PostAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER') or returnObject.get().userName == authentication.name")
    Optional<Person> findById(Long aLong);

    @Override
    @PostFilter("hasRole('ADMIN') or hasRole('EMPLOYER') or filterObject.userName == authentication.name")
    Iterable<Person> findAll();

    @Override
    @PostFilter("hasRole('ADMIN') or hasRole('EMPLOYER') or filterObject.userName == authentication.name")
    Iterable<Person> findAll(Sort sort);

    @Override
   // @PostFilter("hasRole('ADMIN') or hasRole('EMPLOYEE') or #   userName == authentication.name")
    //@Query("select p from Person p where p.userName like ?#{ hasAnyRole('ADMIN','EMPLOYER' ) ? '%' : principal.username }")
    @Query("select p from Person p where p.userName like "+
            "?#{hasAnyRole('ADMIN','EMPLOYER') ? '%' : principal.username}") //
    Page<Person> findAll(Pageable pageable);

    //Get All Persons applied for a particular Job.
    @RestResource(path = "personsappliedforjob", rel = "PersonsAppliedForJob")
    @Query("SELECT DISTINCT p FROM Person p JOIN p.application a JOIN a.job j WHERE j.jobId = ?1")
    Page<Person> findAllPersonsAppliedForJob(Long jobId, Pageable pageable);

    //Find All Persons by Search
    @RestResource(path = "personssearch", rel = "PersonsSearch")
    @Query("SELECT DISTINCT p FROM Person p JOIN p.skillList s JOIN p.qualList q WHERE (p.category.catId = ?1  OR ?1 is null OR ?1 = '' ) " +
            "AND (p.city like %?2%  OR ?2 is null OR ?2 = ''  ) " +
            "AND (p.description like %?3% ) " +
            "AND (p.preferredCity like %?4% ) " +
            "AND (s.skillId = ?5 OR ?5 is null OR ?5 = '' )" +
            "AND (q.qualId = ?6 OR ?6 is null OR ?6 = '' )" +
            "AND (p.userName like ?#{hasAnyRole('ADMIN','EMPLOYER') ? '%' : principal.username})")
    Page<Person> findAllPersonsBySearch(Long catId, String cityName, String desc, String preferredCity, Long skillId, Long qualId, Pageable pageable);
}
