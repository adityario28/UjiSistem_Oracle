/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree.service;

import com.tugasrancang.postgree.InsertTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ryo Aditya
 */
@Service
public class OracleServices {

    private final InsertTable insertTable;

    @Autowired
    public OracleServices(InsertTable insertTable) {
        this.insertTable = insertTable;
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
    
    public String insertDataFromByte(String header, String value) {
        try {
            String status = insertTable.InsertByte(header, value);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occured while inserting data";
        }
    }
}
