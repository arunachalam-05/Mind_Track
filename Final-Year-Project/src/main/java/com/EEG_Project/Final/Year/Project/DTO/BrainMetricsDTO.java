package com.EEG_Project.Final.Year.Project.DTO;

public class BrainMetricsDTO {
    private double cognitiveLoad;
    private double stressRelaxation;
    private double focusAttention;
    private double memoryRetention;
    private double mentalFatigue;
    private String suggestion;

    public BrainMetricsDTO() {
    }

    public BrainMetricsDTO(double cognitiveLoad, double stressRelaxation, double focusAttention, double memoryRetention, double mentalFatigue, String suggestion) {
        this.cognitiveLoad = cognitiveLoad;
        this.stressRelaxation = stressRelaxation;
        this.focusAttention = focusAttention;
        this.memoryRetention = memoryRetention;
        this.mentalFatigue = mentalFatigue;
        this.suggestion = suggestion;
    }

    public double getCognitiveLoad() {
        return cognitiveLoad;
    }

    public void setCognitiveLoad(double cognitiveLoad) {
        this.cognitiveLoad = cognitiveLoad;
    }

    public double getStressRelaxation() {
        return stressRelaxation;
    }

    public void setStressRelaxation(double stressRelaxation) {
        this.stressRelaxation = stressRelaxation;
    }

    public double getFocusAttention() {
        return focusAttention;
    }

    public void setFocusAttention(double focusAttention) {
        this.focusAttention = focusAttention;
    }

    public double getMemoryRetention() {
        return memoryRetention;
    }

    public void setMemoryRetention(double memoryRetention) {
        this.memoryRetention = memoryRetention;
    }

    public double getMentalFatigue() {
        return mentalFatigue;
    }

    public void setMentalFatigue(double mentalFatigue) {
        this.mentalFatigue = mentalFatigue;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
