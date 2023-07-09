/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ryo Aditya
 */
@Component
public class ReadTable {

    static private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReadTable(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //All table
    public List<Map<String, Object>> readAllTable(String tableName, String tableName2) {
        try {
//            String readQuery = "SELECT * FROM " + tableName;
            String readQuery = "SELECT * FROM " + tableName + " INNER JOIN " + tableName2 + " ON 1=1";

            return jdbcTemplate.queryForList(readQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
