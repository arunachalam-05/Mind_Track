package com.EEG_Project.Final.Year.Project.Service;

import com.EEG_Project.Final.Year.Project.DTO.BrainMetricsDTO;
import com.EEG_Project.Final.Year.Project.Model.EEG_Data;
import com.EEG_Project.Final.Year.Project.Repository.EEG_Date_Repo;
import com.EEG_Project.Final.Year.Project.Repository.EEG_Date_Repo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EEG_Service {
    private EEG_Date_Repo eegDataRepo;

    public EEG_Service(EEG_Date_Repo eegDataRepo) {
        this.eegDataRepo = eegDataRepo;
    }

    public EEG_Data saveEEG_Data(EEG_Data eegData) {
        return eegDataRepo.save(eegData);
    }

    public EEG_Data processAndSaveDate(EEG_Data eegData) {
        double total = eegData.getAlpha() + eegData.getBeta() + eegData.getGamma() + eegData.getDelta() + eegData.getTheta();

        double normalizedDelta = eegData.getDelta() / total;
        double normalizedTheta = eegData.getTheta() / total;
        double normalizedAlpha = eegData.getAlpha() / total;
        double normalizedBeta = eegData.getBeta() / total;
        double normalizedGamma = eegData.getGamma() / total;



        double cognitiveLoad = normalizedBeta / (normalizedAlpha + normalizedTheta);
        double stressRelaxation = normalizedBeta / normalizedAlpha;
        double focusAttention = normalizedBeta / (normalizedTheta + normalizedDelta);
        double memoryRetention = normalizedGamma / (normalizedTheta + normalizedAlpha);
        double mentalFatigue = (normalizedTheta + normalizedDelta) / normalizedBeta;

        double totalNormalized = cognitiveLoad + stressRelaxation + focusAttention + memoryRetention + mentalFatigue;

        double normalizedCognitiveLoad = Math.round((cognitiveLoad / totalNormalized) * 100);
        double normalizedStressRelaxation = Math.round((stressRelaxation / totalNormalized) * 100);
        double normalizedFocusAttention = Math.round((focusAttention / totalNormalized) * 100);
        double normalizedMemoryRetention = Math.round((memoryRetention / totalNormalized) * 100);
        double normalizedMentalFatigue = Math.round((mentalFatigue / totalNormalized) * 100);


        eegData.setCognitiveLoad(normalizedCognitiveLoad);
        eegData.setFocusAttention(normalizedFocusAttention);
        eegData.setMemoryRetention(normalizedMemoryRetention);
        eegData.setStressRelaxation(normalizedStressRelaxation);
        eegData.setMentalFatigue(normalizedMentalFatigue);

        eegData.setNormalizedAlpha(normalizedAlpha);
        eegData.setNormalizedBeta(normalizedBeta);
        eegData.setNormalizedDelta(normalizedDelta);
        eegData.setNormalizedGamma(normalizedGamma);
        eegData.setNormalizedTheta(normalizedTheta);


        // Call Flask AI service
        String flaskUrl = "http://127.0.0.1:5000/predict";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("Cognitive_Load", normalizedCognitiveLoad);
        requestData.put("Stress", normalizedStressRelaxation);
        requestData.put("Focus", normalizedFocusAttention);
        requestData.put("Memory", normalizedMemoryRetention);
        requestData.put("Fatigue", normalizedMentalFatigue);

        requestData.put("Time_Seconds", eegData.getTimeTaken());     // You can adjust the value
        requestData.put("Correctness", eegData.getAnswer());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

        try {
                ResponseEntity<Map> response = restTemplate.exchange(
                        flaskUrl, HttpMethod.POST, entity, Map.class);

                if (response.getBody() != null && response.getBody().get("suggestion") != null) {
                    String suggestion = (String) response.getBody().get("suggestion");
                    eegData.setSuggestion(suggestion);
                } else {
                    System.out.println("Flask response did not contain suggestion.");
                    eegData.setSuggestion("No suggestion available.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                eegData.setSuggestion("Error communicating with Flask AI.");
                System.out.println("Error calling Flask API, saving EEG data without suggestion.");
            }


            return eegDataRepo.save(eegData);
    }

    public BrainMetricsDTO getBrainMetricsByUserId(Long userId){
        Optional<EEG_Data> eegDataOptional = eegDataRepo.findTopByUserIdOrderByTimestampDesc(userId);

        if(eegDataOptional.isPresent()){
            EEG_Data eegData = eegDataOptional.get();
            return eegData.toBrainMetricsDTO();
        }else {
            throw new RuntimeException("EEG Data not found for User ID: " + userId);
        }
    }


    public List<EEG_Data> findByUserId(Long userId) {
        return eegDataRepo.findByUserId(userId);
    }
}
