package com.test.mvc.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Assignments")
public class AssignmentModel {
    @Id
    @Column(name = "citizen_code", length = 13)
    private String citizenCode;

    @Column(name = "shelter_code")
    private String shelterCode;

    @Column(name = "start_date")
    private String startDate;

    public String getCitizenCode() {
        return citizenCode;
    }

    public void setCitizenCode(String citizenCode) {
        this.citizenCode = citizenCode;
    }

    public String getShelterCode() {
        return shelterCode;
    }

    public void setShelterCode(String shelterCode) {
        this.shelterCode = shelterCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
