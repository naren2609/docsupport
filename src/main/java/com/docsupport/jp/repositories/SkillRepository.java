package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Skill;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods= {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
public interface SkillRepository extends PagingAndSortingRepository<Skill, Long> {

}
