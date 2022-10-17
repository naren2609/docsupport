package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.City;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface CityRepository  extends PagingAndSortingRepository<City, Long> {
}
