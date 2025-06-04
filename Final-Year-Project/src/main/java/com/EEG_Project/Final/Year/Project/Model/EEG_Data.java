package com.EEG_Project.Final.Year.Project.Model;

import com.EEG_Project.Final.Year.Project.DTO.BrainMetricsDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "eeg_values")
public class EEG_Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private double delta;
    private double theta;
    private double alpha;
    private double beta;


    private double gamma;

    @Column(nullable = true)
    private double normalizedDelta;
    @Column(nullable = true)
    private double normalizedTheta;
    @Column(nullable = true)
    private double normalizedAlpha;
    @Column(nullable = true)
    private double normalizedBeta;
    @Column(nullable = true)
    private double normalizedGamma;

    // Brain metrics
    @Column(nullable = true)
    private Double cognitiveLoad;
    @Column(nullable = true)
    private Double stressRelaxation;
    @Column(nullable = true)
    private Double focusAttention;
    @Column(nullable = true)
    private Double memoryRetention;
    @Column(nullable = true)
    private Double mentalFatigue;

    private String suggestion;

    @CreationTimestamp
    @Column(nullable = true, updatable = false)
    private LocalDateTime timestamp;
    @Column(nullable = true)
    private Integer answer;
    @Column(nullable = true)
    private Double timeTaken;

    public Integer  getAnswer() {
        return answer;
    }

    public void setAnswer(Integer  answer) {
        this.answer = answer;
    }

    public Double getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setCognitiveLoad(Double cognitiveLoad) {
        this.cognitiveLoad = cognitiveLoad;
    }

    public void setStressRelaxation(Double stressRelaxation) {
        this.stressRelaxation = stressRelaxation;
    }

    public void setFocusAttention(Double focusAttention) {
        this.focusAttention = focusAttention;
    }

    public void setMemoryRetention(Double memoryRetention) {
        this.memoryRetention = memoryRetention;
    }

    public void setMentalFatigue(Double mentalFatigue) {
        this.mentalFatigue = mentalFatigue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BrainMetricsDTO toBrainMetricsDTO() {
        return new BrainMetricsDTO(
                this.cognitiveLoad,
                this.stressRelaxation,
                this.focusAttention,
                this.memoryRetention,
                this.mentalFatigue,
                this.suggestion
        );
    }


    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public double getNormalizedDelta() {
        return normalizedDelta;
    }

    public void setNormalizedDelta(double normalizedDelta) {
        this.normalizedDelta = normalizedDelta;
    }

    public double getNormalizedTheta() {
        return normalizedTheta;
    }

    public void setNormalizedTheta(double normalizedTheta) {
        this.normalizedTheta = normalizedTheta;
    }

    public double getNormalizedAlpha() {
        return normalizedAlpha;
    }

    public void setNormalizedAlpha(double normalizedAlpha) {
        this.normalizedAlpha = normalizedAlpha;
    }

    public double getNormalizedBeta() {
        return normalizedBeta;
    }

    public void setNormalizedBeta(double normalizedBeta) {
        this.normalizedBeta = normalizedBeta;
    }

    public double getNormalizedGamma() {
        return normalizedGamma;
    }

    public void setNormalizedGamma(double normalizedGamma) {
        this.normalizedGamma = normalizedGamma;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }
}
