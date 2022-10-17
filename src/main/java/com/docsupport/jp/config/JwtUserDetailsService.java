package com.docsupport.jp.config;

import com.docsupport.jp.pojo.Credential;
import com.docsupport.jp.pojo.Exceptions;
import com.docsupport.jp.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Credential credential = credentialRepository.findByUserName(username);
        if (credential == null) {
            throw new UsernameNotFoundException(username);
        }
        if (!credential.getActive()) {
            throw new Exceptions.UserDisabledException();
        }
        UserDetails user = User.withUsername(credential.getUserName()).password(credential.getPassword()).roles(credential.getRoleType()).build();
        return user;
    }
}