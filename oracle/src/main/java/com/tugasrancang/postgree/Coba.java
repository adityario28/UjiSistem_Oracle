/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miche
 */
public class Coba {
    static String dir = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\Data-5\\Data-5\\Line Stock\\1\\";
    static String fil = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\Data-5\\Data-5\\Line Stock\\1\\AA30SalesLineStock_0bb8dd5c-77ef-40a4-8912-48a867f72da8.csv";
//    public static void main(String[] args) throws Exception{
////        readfileheader();
//        readfile();
//    }
    
    public static List<String> readfileheader() throws Exception{
        File[] files = new File(dir).listFiles();
            List<String> data = new ArrayList<>();
            try {
//>1 file
                for(File file : files){
                    FileReader reader = new FileReader(file);
                    BufferedReader buffer = new BufferedReader(reader);
                    String row = buffer.readLine();
                    buffer.close();
                    reader.close();
                    data.add(row);
                    System.out.println(data);
                }
//1 file
//                    FileReader reader = new FileReader(fil);
//                    BufferedReader buffer = new BufferedReader(reader);
//                    String row = buffer.readLine();
//                    buffer.close();
//                    reader.close();
//                    data.add(row);
//                    System.out.println(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
    }
    public static void readfile() throws Exception{
            
        String[] files = new File(dir).list();
        List<String> listrequest = new ArrayList<>();
        for (String filename : files) {
            //System.out.println(filename);

            //readfile
            FileReader reader = new FileReader(dir + filename);
            BufferedReader bfr = new BufferedReader(reader);
            String line = bfr.readLine();  
            boolean firstline = true;
            
            while(line != null){
                if (!firstline) {
                    listrequest.add(line);
                } else {
                    firstline = false;
                }
                line = bfr.readLine();
            }
            bfr.close();
            reader.close();
        }
        for (String line : listrequest) {
            System.out.println(line);
        }
    }
}
