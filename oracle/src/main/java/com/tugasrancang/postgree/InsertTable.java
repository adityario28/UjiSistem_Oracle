/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;

import static com.tugasrancang.postgree.CreateTable.CreateTable;
import static com.tugasrancang.postgree.CreateTable.line_map_path;
import static com.tugasrancang.postgree.CreateTable.table_map_path;
import static com.tugasrancang.postgree.Mapping.HashMapFromTextFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author miche
 */
public class InsertTable {
    
    final static String line_map_val = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\All_Data-5\\Data-5\\ALL\\";
    private static JdbcTemplate jdbcTemplate;
    
    public static String InsertAllTable(String url, String user, String pass) throws Exception{
        String status =null;
        //Convert txt to Hashmap
        try {
            InsertIntoTable(line_map_val,url,user,pass);
            status = "Table Inserted Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static void InsertIntoTable(String data, String url, String user, String pass){
        Map<String, String> tipe_l = HashMapFromTextFile ("map/line_map.txt");
        Map<String, String> tipe_t = HashMapFromTextFile ("map/table_map.txt"); 
        BufferedReader br = null;
        
        String[] files = new File(data).list();
        List<String> listrequest = new ArrayList<>();
        
        SingleConnectionDataSource ds = new SingleConnectionDataSource();
        //postgre
//        ds.setDriverClassName("org.postgresql.Driver");
        //oracle
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(pass);
        JdbcTemplate jdbcTemplate = new JdbcTemplate( ds);
        
        try {
            for (String filename : files) {
                String header = "";
                int i = 0;
                FileReader reader = new FileReader(data + filename);
                BufferedReader bfr = new BufferedReader(reader);
                String line = bfr.readLine();  
                boolean firstline = true;

                while(line != null){
                    String h = "";
                    String v = "";
                   
                    if (!firstline) {
                        String replace = line.replace("\"", "");
                        String[] headerr = header.split(",");
                        String[] value = replace.split(",");
                        
                    for (i = 0; i < headerr.length; i++) {
                        String head = headerr[i].trim();
                        String val = value[i].trim();
                        String tipel = tipe_l.get(headerr[i].trim());
                        String tipet = tipe_t.get(headerr[i].trim());
                        
                        
                        while(!h.contains(head)) {
                            //MenentukanTableGanda
                            if (head.equals("MODE")) {
                                if (i != headerr.length - 1 ) {
                                    v = v + "\'" + val + "\'" + ",";
                                    h = h + head + "_1,";
                                } else {
                                    v = v + "\'" + val + "\'";
                                    h = h + head + "_1";
                                }

                            }
                            else if (head.equals("OPERATOR")) {
                                if (i != headerr.length - 1 ) {
                                    v = v + "\'" + val + "\'" + ",";
                                    h = h + head + "_1,";
                                } else {
                                    v = v + "\'" + val + "\'";
                                    h = h + head + "_1";
                                }

                            }
                            //MenentukanDataKosong
                            else if (val.equals("")) {
                                String b = null;
                                if (i != headerr.length - 1 ) {
                                    v = v + b + ",";
                                    h = h + head + ",";
                                } else {
                                    v = v + b;
                                    h = h + head;
                                }

                            }
                            //Menentukan INT
                            else if ((tipel != null && tipel.equals("int")) || (tipet != null && tipet.equals("int"))) {
                                if (i != headerr.length - 1 ) {
                                    v = v + val + ",";
                                    h = h + head + ",";
                                } else {
                                    v = v + val;
                                    h = h + head;
                                }

                            }
                            //Menentukan Date
                            else if ((tipel != null && tipel.equals("Date")) || (tipet != null && tipet.equals("Date"))) {
                                String b = "\'" + val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8) + "\'";
                                if (i != headerr.length - 1 ) {
                                    v = v + "TO_DATE(" + b + ",'YYYY-MM-DD')" + ",";
                                    h = h + head + ",";
                                } else {
                                    v = v + b;
                                    h = h + head;
                                }

                            }
                            //Menentukan Time
                            else if (head.equals("RCVTIME")) {
                                String b = null;
                                if (i != headerr.length - 1 ) {
                                    v = v + b + ",";
                                    h = h + head + ",";
                                } else {
                                    v = v + b;
                                    h = h + head;
                                }

                            }
                            else {
                                if (i != headerr.length - 1 ) {
                                    v = v + "\'" + val + "\'" + ",";
                                    h = h + head + ",";
                                } else {
                                    v = v + "\'" + val + "\'";
                                    h = h + head;
                                }

                            }
                        }
                    }
                    } else {
                        header = line;
                        h = null;
                        firstline = false;
                    }
                    
                    if (h != null) {
                        try {
                            if (h.contains("HoyaItemType")){
                                   jdbcTemplate.execute("INSERT INTO TABLESTOCK (" + h + ") VALUES (" + v + ")");
                            } else {
                                   jdbcTemplate.execute("INSERT INTO LINESTOCK (" + h + ") VALUES (" + v + ")");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    line = bfr.readLine();
                }
                bfr.close();
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
            ds.destroy();
        }
    }
}
