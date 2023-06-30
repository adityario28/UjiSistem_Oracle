/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;

import static com.tugasrancang.postgree.Coba.dir;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author miche
 */
public class Mapping {
    
    public static Map<String, String> HashMapFromTextFile(String filePath)
    {
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader br = null;
            try {
                // create file object
                File file = new ClassPathResource(filePath).getFile();
                // create BufferedReader object from the File
                br = new BufferedReader(new FileReader(file));
                String line = null;
                // read file line by line
                while ((line = br.readLine()) != null) {
                    // split the line by :
                    String[] parts = line.split(":");
                    // first part is name, second is number
                    String name = parts[0].trim();
                    String number = parts[1].trim();
                    // put name, number in HashMap if they are
                    // not empty
                    if (!name.equals("") && !number.equals(""))
                        map.put(name, number);
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
        }
        return map;
    }
    
}
