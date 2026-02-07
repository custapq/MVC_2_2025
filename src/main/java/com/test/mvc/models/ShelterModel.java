package com.test.mvc.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Shelters")
public class ShelterModel {

    @Id
    @Column(name = "shelter_code")
    private String shelterCode;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "current_occupancy")
    private int currentOccupancy;

    @Column(name = "risk_level")
    private String riskLevel;

    public String getShelterCode() {
        return shelterCode;
    }

    public void setShelterCode(String shelterCode) {
        this.shelterCode = shelterCode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
