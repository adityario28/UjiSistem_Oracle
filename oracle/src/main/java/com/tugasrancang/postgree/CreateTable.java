/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;


//import static com.tugasrancang.postgree.PostgreeApplication.DB_URL;
//import static com.tugasrancang.postgree.PostgreeApplication.PASS;
//import static com.tugasrancang.postgree.PostgreeApplication.USER;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author miche
 */
@Component
public class CreateTable {
    final static String line_map_path = "map/line_map_1.txt";
    final static String table_map_path = "map/table_map_1.txt";
   
    private static JdbcTemplate jdbcTemplate;
    
    @Autowired
    public CreateTable(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    

    
  public static String CreateAllTable() {
        String status = null;
        //Convert txt to Hashmap
        Map<String, String> line_map = Mapping.HashMapFromTextFile(line_map_path);
        Map<String, String> table_map = Mapping.HashMapFromTextFile(table_map_path);
        try {
            DropTable("TABLESTOCK");
            DropTable("LINESTOCK");

            CreateTable(table_map, "TABLESTOCK");
            CreateTable(line_map, "LINESTOCK");

            status = "Table Created Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static void DropTable(String tablename) {
        try {
            String dropQuery = "DROP TABLE " + tablename;
            jdbcTemplate.execute(dropQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CreateTable(Map<String, String> map, String tablename) {
        try {

            String createQuery = "CREATE TABLE " + tablename + " (";
            for (Map.Entry<String, String> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + ";" + entry.getValue());
                createQuery += entry.getKey() + " " + entry.getValue() + ", ";
//                System.out.println(entry.getKey() + ";" + entry.getValue());
            }
            if (!map.isEmpty()) {
                createQuery = createQuery.substring(0, createQuery.length() - 2);
            }
            createQuery += ") ";
            jdbcTemplate.execute(createQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
