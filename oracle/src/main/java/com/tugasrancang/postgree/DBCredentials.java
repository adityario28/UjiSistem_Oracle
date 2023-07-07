/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author Ryo Aditya
 */
@Configuration
public class DBCredentials {
    @Value("${spring.datasource.url}")
    private String DBURL;
    
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    
    @Value("${spring.datasource.password}")
    private String DB_PWD;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(DBURL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PWD);
        System.out.println("driver name : " + driverClassName);
        System.out.println("url : "  + DBURL);
        System.out.println("username : "  + DB_USERNAME);
        System.out.println("password : "  + DB_PWD);
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
}
