package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}
