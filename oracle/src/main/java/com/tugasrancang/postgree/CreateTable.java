/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;


import static com.tugasrancang.postgree.PostgreeApplication.DB_URL;
import static com.tugasrancang.postgree.PostgreeApplication.PASS;
import static com.tugasrancang.postgree.PostgreeApplication.USER;
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
   // final static String line_map_path = "map/line_map.txt";
    //final static String table_map_path = "map/table_map.txt";
   
    private static JdbcTemplate jdbcTemplate;
    
    @Autowired
    public CreateTable(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    

    
  public static String CreateAllTable(String url, String user, String pass){
        List<String> fileNames = new ArrayList<>();
        //fileNames.add("D:\\Ujisistem_tugas\\github_ujisistem\\UjiSistem_Oracle\\oracle\\src\\main\\resources\\map\\line_map_1.txt"); // Provide the path to your first text file here
     //fileNames.add("D:\\Ujisistem_tugas\\github_ujisistem\\UjiSistem_Oracle\\oracle\\src\\main\\resources\\map\\table_map_1.txt"); // Provide the path to your second text file here
    fileNames.add("D:\\Tugas\\Semester9\\Pengujian Sistem\\Testing\\src\\main\\java\\map\\line_map_1.txt"); // Provide the path to your first text file here
        fileNames.add("D:\\Tugas\\Semester9\\Pengujian Sistem\\Testing\\src\\main\\java\\map\\table_map_1.txt"); // Provide the path to your second text file here
        // Add more file names if necessary
        
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource(DB_URL, USER, PASS);
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            for (String fileName : fileNames) {
                List<String> tableData = readTableDataFromFile(fileName);

                String tableName = tableData.get(0);
                List<String> columnData = tableData.subList(1, tableData.size());

                if (tableExists(jdbcTemplate, tableName)) {
                    System.out.println("Table " + tableName + " already exists. Skipping creation.");
                    continue;
                }

                String createTableQuery = generateCreateTableQuery(tableName, columnData);

                jdbcTemplate.execute(createTableQuery);

                System.out.println("Table created: " + tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Table Created Successfully";
    }

private static List<String> readTableDataFromFile(String fileName) throws IOException {
        List<String> tableData = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = reader.readLine()) != null) {
            tableData.add(line.trim());
        }

        reader.close();

        return tableData;
    }

    private static boolean tableExists(JdbcTemplate jdbcTemplate, String tableName) {
        Integer result=0;
        try{
        String sql = "SELECT 1 FROM all_tables WHERE table_name = ?";
         result = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result == null;
    }
private static String generateCreateTableQuery(String tableName, List<String> columnData) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tableName).append(" (");

        for (int i = 0; i < columnData.size(); i++) {
            String[] parts = columnData.get(i).split(":");
            String columnName = parts[0].trim();
            String dataType = parts[1].trim();

            sb.append(columnName).append(" ").append(dataType);

            if (i < columnData.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
}

}
