package com.docsupport.jp;

import com.docsupport.jp.pojo.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@SpringBootApplication
//@EnableSwagger2
public class DocsupportApplication implements RepositoryRestConfigurer {


    public static void main(String[] args) {

        //docsupp20@gmail.com / qwe123$$

        SpringApplication.run(DocsupportApplication.class, args);

        ObjectMapper mapper = new ObjectMapper();

        // Java object to JSON file
       // mapper.writeValue(new File("c:\\test\\staff.json"), new Staff());

        // Java object to JSON string
        try {
            System.out.println("Employer: "+ mapper.writeValueAsString(new Employer()));
            System.out.println("Job: "+mapper.writeValueAsString(new Job()));
            System.out.println("Application: "+mapper.writeValueAsString(new Application()));
            System.out.println("Category: "+mapper.writeValueAsString(new Category()));
            System.out.println("Educational: "+mapper.writeValueAsString(new Qualification()));
            System.out.println("Person: "+mapper.writeValueAsString(new Person()));
            System.out.println("Skill: "+mapper.writeValueAsString(new Skill()));
            System.out.println("PasswordResetToken: "+mapper.writeValueAsString(new PasswordResetToken()));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Job.class);
        config.exposeIdsFor(Category.class);
        config.exposeIdsFor(Person.class);
        config.exposeIdsFor(Credential.class);
        config.exposeIdsFor(Application.class);
        config.exposeIdsFor(Employer.class);
        config.exposeIdsFor(Skill.class);
        config.exposeIdsFor(Qualification.class);
        config.exposeIdsFor(City.class);
        cors.addMapping("/**").allowedOrigins("*")
                .allowedMethods("*").allowedHeaders("*");
       // cors.addMapping("/**").allowedOrigins("http://localhost:4200/,http://localhost:8080/")
        //        .allowedMethods("*").allowedHeaders("*");

    }


/*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
            }
        };
    }
*/
/*
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.docssupport.jp")).build();
    }
*/
}
