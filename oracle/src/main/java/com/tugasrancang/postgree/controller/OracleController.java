/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree.controller;

import com.tugasrancang.postgree.service.OracleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Ryo Aditya
 */

@RestController
public class OracleController {
    private final OracleServices oracleServices;
    
    @Autowired
    public OracleController(OracleServices oracleServices){
        this.oracleServices = oracleServices;
    }
    
    @PostMapping("/insertData")
    public String insertDataFromCSV(){
        String status = oracleServices.insertDataFromCSV();
        return status;
    }
    
    @PostMapping("/insertByte")
    public String insertDataFromByte(@RequestBody byte[] requestData){
        String data = new String(requestData);
        String[] parts = data.split(":");
        String header = new String(parts[0]);
        String value = new String(parts[1]);
//        String status = oracleServices.insertDataFromByte(header, value);
        System.out.println("Header: " + header);
        System.out.println("Value: " + value);
        return "";
    }
    
}
