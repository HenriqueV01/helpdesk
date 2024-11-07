package com.project.helpdesk.config;

import com.project.helpdesk.services.DBService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Autowired
    private  DBService dbService;
    /*@Bean*/
    @PostConstruct
    public void instaciaDB(){
        this.dbService.instaciaDB();
    }
}
