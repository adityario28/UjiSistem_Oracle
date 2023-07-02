/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;


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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author miche
 */
public class CreateTable {
   // final static String line_map_path = "map/line_map.txt";
    //final static String table_map_path = "map/table_map.txt";
    //private static JdbcTemplate jdbcTemplate;
    
  public static String CreateAllTable(String url, String user, String pass){
        List<String> fileNames = new ArrayList<>();
        fileNames.add("D:\\Ujisistem_tugas\\github_ujisistem\\UjiSistem_Oracle\\oracle\\src\\main\\resources\\map\\line_map_1.txt"); // Provide the path to your first text file here
        fileNames.add("D:\\Ujisistem_tugas\\github_ujisistem\\UjiSistem_Oracle\\oracle\\src\\main\\resources\\map\\table_map_1.txt"); // Provide the path to your second text file here
        // Add more file names if necessary
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(url,user,pass);

            for (String fileName : fileNames) {
                // Read the text file
                List<String> tableData = readTableDataFromFile(fileName);

                // Split table name and column data
                String tableName = tableData.get(0);
                List<String> columnData = tableData.subList(1, tableData.size());

                // Check if the table already exists
                if (tableExists(connection, tableName)) {
                    System.out.println("Table " + tableName + " already exists. Skipping creation.");
                    continue;
                }

                // Generate the CREATE TABLE statement
                String createTableQuery = generateCreateTableQuery(tableName, columnData);

                // Execute the CREATE TABLE statement
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableQuery);

                System.out.println("Table created: " + tableName);
            }

            // Close resources
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Success";
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

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM ALL_TABLES WHERE TABLE_NAME = '" + tableName + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        boolean tableExists = resultSet.next();

        resultSet.close();
        statement.close();

        return tableExists;
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
