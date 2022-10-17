package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Employer;
import com.docsupport.jp.pojo.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin
public interface EmployerRepository extends PagingAndSortingRepository<Employer, Long> {
    List<Employer> findByEmployerName(@Param("employerName") String name);

   // Employer save( Employer employer);

    @Override
    @PostAuthorize("hasRole('ADMIN') or hasRole('USER') or returnObject.get().userName == authentication.name")
    Optional<Employer> findById(Long aLong);

    @Override
    @PostFilter("hasRole('ADMIN') or hasRole('USER') or filterObject.userName == authentication.name")
    Iterable<Employer> findAll();

    @Override
    @PostFilter("hasRole('ADMIN') or hasRole('USER') or filterObject.userName == authentication.name")
    Iterable<Employer> findAll(Sort sort);

    @Override
    // @PostFilter("hasRole('ADMIN') or hasRole('EMPLOYEE') or #   userName == authentication.name")
    //@Query("select p from Person p where p.userName like ?#{ hasAnyRole('ADMIN','EMPLOYER' ) ? '%' : principal.username }")
    @Query("select e from Employer e where e.userName like "+
            "?#{hasAnyRole('ADMIN','USER') ? '%' : principal.username}") //
    Page<Employer> findAll(Pageable pageable);

}