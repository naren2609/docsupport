package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Application;
import com.docsupport.jp.pojo.Employer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {
    List<Application> findByPersonIs(@Param("employerName") String name);
   // List<Application> findByPersonIsOrJob(@Param("employerName") String name);
}