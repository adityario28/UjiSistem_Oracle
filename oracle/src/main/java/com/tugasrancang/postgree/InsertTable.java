/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;

//import static com.tugasrancang.postgree.CreateTable.CreateTable;
//import static com.tugasrancang.postgree.CreateTable.line_map_path;
//import static com.tugasrancang.postgree.CreateTable.table_map_path;
import static com.tugasrancang.postgree.Mapping.HashMapFromTextFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author LENOVO IP SLIM 3
 */

@Component
public class InsertTable {
    

//    final static String line_map_val = "D:\\Ujisistem_tugas\\Data-5\\All\\";

//    final static String line_map_val = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\All_Data-5\\Data-5\\ALL\\";
    final static String line_map_val = "D:\\Tugas\\Semester9\\Pengujian Sistem\\Data-5\\Data-5\\Full\\";

    private static JdbcTemplate jdbcTemplate;
    
    @Autowired
    public InsertTable(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public static String InsertAllTable() throws Exception{
        String status = null;
        try {
            InsertIntoTable(line_map_val);
            status = "Table Inserted Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static void InsertIntoTable(String data){
        //Ambil data map dan key
        Map<String, String> tipe_l = HashMapFromTextFile ("map/line_map.txt");
        String key = "";
        for (Map.Entry<String, String> entry : tipe_l.entrySet()) {
            key = key + entry.getKey() + ",";
        }
        Map<String, String> tipe_t = HashMapFromTextFile ("map/table_map.txt"); 
        for (Map.Entry<String, String> entry : tipe_t.entrySet()) {
            key = key + entry.getKey() + ",";
        }
        
        BufferedReader br = null;
        String[] files = new File(data).list();
        
//        //Setting koneksi
//        SingleConnectionDataSource ds = new SingleConnectionDataSource();
//        //oracle
//        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        ds.setUrl(url);
//        ds.setUsername(user);
//        ds.setPassword(pass);
//        JdbcTemplate jdbcTemplate = new JdbcTemplate( ds);
        
        try {
            for (String filename : files) {
                String header = "";
                int i = 0;
                int j = 0;
                FileReader reader = new FileReader(data + filename);
                BufferedReader bfr = new BufferedReader(reader);
                String line = bfr.readLine();  
                boolean firstline = true;

                while(line != null){
                    List<String> listheader = new ArrayList<>();
                    String h = "";
                    List<String> listvalue = new ArrayList<>();
                    String v = "";
                   
                    if (!firstline) {
                        String replace = line.replace("\"", "");
                        String[] headerr = header.split(",");
                        String[] value = replace.split(",");
                        int length = headerr.length;
                        
                    for (i = 0; i < length; i++) {
                        String head = headerr[i].trim();
                        String val = value[i].trim();
                        String tipel = tipe_l.get(headerr[i].trim());
                        String tipet = tipe_t.get(headerr[i].trim());
                        
                        //Mengeliminasi kolom ganda dan nama kolom salah
                        if ((!listheader.contains(head)) && ((key.toUpperCase()).contains(head.toUpperCase()))) {
                            //Mengganti nama kolom
                            if (head.equals("MODE")) {
                                listvalue.add("\'" + val + "\'");
                                listheader.add(head + "_1");
                                
                            }
                            else if (head.equals("OPERATOR")) {
                                listvalue.add("\'" + val + "\'");
                                listheader.add(head + "_1");

                            }
                            //MenentukanDataKosong
                            else if (val.equals("")) {
                                String b = null;
                                listvalue.add(b);
                                listheader.add(head);
                            
                            }
                            //Menentukan INT
                            else if ((tipel != null && tipel.equals("int")) || (tipet != null && tipet.equals("int"))) {
                                listvalue.add(val);
                                listheader.add(head);

                            }
                            //Menentukan Date
                            else if ((tipel != null && tipel.equals("Date")) || (tipet != null && tipet.equals("Date"))) {
                                String b = "\'" + val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8) + "\'";
                                listvalue.add("TO_DATE(" + b + ",'YYYY-MM-DD')");
                                listheader.add(head);

                            }
                            //Menentukan Time
                           /* else if (head.equals("RCVTIME")) {
                                String b = null;
                                listvalue.add(b);
                                listheader.add(head);

                            }*/
                            else {
                                listvalue.add("\'" + val + "\'");
                                listheader.add(head);

                            }
                        }
                        
                    }
                    } else {
                        header = line;
                        h = null;
                        firstline = false;
                    }
                    
                    //Menggabungkan data
                    for (j = 0; j < listheader.size(); j++) {
                        if (j != listheader.size() - 1){
                            v = v + listvalue.get(j) + ",";
                            h = h + listheader.get(j) + ",";
                        } else {
                            v = v + listvalue.get(j);
                            h = h + listheader.get(j);
                        }
                    }
                    
                    //Insert data
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
//            ds.destroy();
        }
    }
}
