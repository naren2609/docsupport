package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.PasswordResetToken;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import java.util.List;

@RestResource(exported = false)
@RepositoryRestResource(collectionResourceRel = "tokens", path = "tokens")
public interface PasswordResetTokenRepository extends PagingAndSortingRepository<PasswordResetToken, Long> {
    List<PasswordResetToken> findByToken(String token);
    PasswordResetToken findByUserName(String userName);
    Integer deleteByUserName(String userName);
}

