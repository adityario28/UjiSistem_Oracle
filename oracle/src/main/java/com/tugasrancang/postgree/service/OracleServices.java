/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree.service;

import com.tugasrancang.postgree.InsertTable;
import com.tugasrancang.postgree.ReadTable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ryo Aditya
 */
@Service
public class OracleServices {

    private final InsertTable insertTable;
    private final ReadTable readTable;

    @Autowired
    public OracleServices(InsertTable insertTable, ReadTable readTable) {
        this.insertTable = insertTable;
        this.readTable = readTable;
    }

    public List<Map<String, Object>> readAllData(String tableName, String tableName2) {
        return readTable.readAllTable(tableName, tableName2);
    }

    public String insertDataFromCSV() {
        try {
            String status = insertTable.InsertAllTable();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occured while inserting data";
        }
    }

    public double insertDataFromByte(String header, String value) {
        double waktu = 0;
        try {
            waktu = insertTable.InsertByte(header, value);
            return waktu;
        } catch (Exception e) {
            e.printStackTrace();
            return waktu;
        }
    }
}
