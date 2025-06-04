package com.EEG_Project.Final.Year.Project.Controller;

import com.EEG_Project.Final.Year.Project.DTO.BrainMetricsDTO;
import com.EEG_Project.Final.Year.Project.Model.EEG_Data;
import com.EEG_Project.Final.Year.Project.Service.EEG_Service;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EEG_Controller {

    private EEG_Service eegService;
    public EEG_Controller(EEG_Service eegService){
        this.eegService = eegService;
    }
//    @PostMapping("/eeg/submitData")
//    public ResponseEntity<?> submitDate(@RequestBody EEG_Data eegData){
//        try{
//            eegService.saveEEG_Date(eegData);
//            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Data received successfully\"}");
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Failed to process data\"}");
//
//        }
//    }

    @PostMapping("/eeg/submitData")
    public ResponseEntity<?> submitData(@RequestBody EEG_Data eegData) {
        try {

            eegService.processAndSaveDate(eegData);  // Store the data in the database
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Data received successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message","Failed to process data"));
        }
    }

    @GetMapping("/eeg/userResult/{userId}")
    public BrainMetricsDTO getData(@PathVariable Long userId){
        return eegService.getBrainMetricsByUserId(userId);
    }

    @GetMapping("/eeg/history/{userId}")
    public List<EEG_Data> getUserResults(@PathVariable Long userId) {
        return eegService.findByUserId(userId);
    }

}
