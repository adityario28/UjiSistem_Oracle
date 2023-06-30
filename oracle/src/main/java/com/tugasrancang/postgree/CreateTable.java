/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;


import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author miche
 */
public class CreateTable {
    final static String line_map_path = "map/line_map.txt";
    final static String table_map_path = "map/table_map.txt";
    private static JdbcTemplate jdbcTemplate;
    
    public static String CreateAllTable(String url, String user, String pass){
        String status =null;
        //Convert txt to Hashmap
        Map<String, String> line_map = Mapping.HashMapFromTextFile(line_map_path);
        Map<String, String> table_map = Mapping.HashMapFromTextFile(table_map_path);
        try {
            CreateTable(table_map,"TABLESTOCK",url,user,pass);
            CreateTable(line_map,"LINESTOCK",url,user,pass);
            status = "Table Created Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static void CreateTable(Map<String,String> map,String tablename, String url, String user, String pass){
        try {
            SingleConnectionDataSource ds = new SingleConnectionDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl(url);
            ds.setUsername(user);
            ds.setPassword(pass);
            
            jdbcTemplate = new JdbcTemplate( ds);
            //jdbcTemplate.execute("DROP TABLE IF EXIST"+ tablename);            
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS "+ tablename +"(id bigint)");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getValue().equals("Varchar")){
                    jdbcTemplate.execute("ALTER TABLE " + tablename + " ADD if not exists " + entry.getKey() + " "+entry.getValue()+"(255)");
                }else{                
                    jdbcTemplate.execute("ALTER TABLE " + tablename + " ADD if not exists " + entry.getKey() + " "+entry.getValue());
                }
            }
            ds.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
