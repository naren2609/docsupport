package com.docsupport.jp.repositories;

import com.docsupport.jp.pojo.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByUserName(String userName);

    @Query("select c from Credential c where c.userName = ?1")
    Credential searchCredentialByUserName(String userName);

}
