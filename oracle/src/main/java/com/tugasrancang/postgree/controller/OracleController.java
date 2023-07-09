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
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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
    public double insertDataFromByte(@RequestBody String requestData){
        JSONObject object = new JSONObject(requestData);
        String[] keys = JSONObject.getNames(object);
        String header = "";
        String valuee = "";
        for (String key : keys)
        {
            Object value = object.get(key);
//            System.out.println(value.toString());
//            System.out.println(key);
            header = key;
            valuee = value.toString();
            // Determine type of value and do something with it...
        }
        double waktu = oracleServices.insertDataFromByte(header, valuee);
        System.out.println("Table Inserted Succesfully // Time " + String.format("%.9f", waktu) + " second");
        return waktu;
    }
    
    @GetMapping("/allData")
//    public String readAllData(){
//        postgreeService.readAllData();
//        
//        return "read All Data";
//    }
    public ResponseEntity<List<Map<String, Object>>> readAllData() {
        List<Map<String, Object>> data = oracleServices.readAllData("LINESTOCK", "TABLESTOCK");
        return ResponseEntity.ok(data);
    }
    
}
