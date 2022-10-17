package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Qualification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface QualificationRepository extends PagingAndSortingRepository<Qualification, Long>{
}
