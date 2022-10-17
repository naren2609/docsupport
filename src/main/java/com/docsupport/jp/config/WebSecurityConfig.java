package com.docsupport.jp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.authenticationProvider(authProvider());
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/registeremployer").permitAll()
                .antMatchers("/registerjs").permitAll()
                .antMatchers("/generatetoken/**").permitAll()
                .antMatchers("/newpassword").permitAll()
                .antMatchers("/health").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/cities", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/cities", "POST")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/skills", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/skills", "POST")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/qualifications", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/qualifications", "POST")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/categories", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/categories", "POST")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/jobs/**", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/jobs", "POST")).hasAnyRole("EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/jobs", "PUT")).hasAnyRole("EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/applications", "POST")).hasAnyRole("USER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/applications", "GET")).hasAnyRole("USER","EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/applications", "PUT")).hasAnyRole("USER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/persons", "POST")).hasAnyRole("USER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/persons", "PUT")).hasAnyRole("USER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/persons", "GET")).hasAnyRole("USER","EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/employers", "POST")).hasAnyRole("EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/employers", "PUT")).hasAnyRole("EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/employers", "GET")).hasAnyRole("USER","EMPLOYER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/login", "GET")).hasAnyRole("USER","EMPLOYER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

}