package com.test.mvc.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Citizens")
public class CitizenModel {
    @Id
    @Column(name = "citizen_code", length = 13)
    private String citizenCode;

    @Column(name = "age")
    private int age;

    @Column(name = "health_condition")
    private String healthCondition = "ปกติ";

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "citizen_type")
    private String citizenType;

    public String getCitizenCode() {
        return citizenCode;
    }

    public void setCitizenCode(String citizenCode) {
        this.citizenCode = citizenCode;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCitizenType() {
        return citizenType;
    }

    public void setCitizenType(String citizenType) {
        this.citizenType = citizenType;
    }

    public int getPriorityScore() {
        if (this.age <= 12 || this.age >= 60) {
            return 3;
        }
        if ("กลุ่มเสี่ยง".equalsIgnoreCase(this.citizenType)) {
            return 2;
        }
        return 1;
    }
}
