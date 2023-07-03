/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugasrancang.postgree.controller;

import com.tugasrancang.postgree.service.OracleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
