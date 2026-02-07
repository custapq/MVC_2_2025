package com.test.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.mvc.models.AssignmentModel;

public interface AssignmentRepository extends JpaRepository<AssignmentModel, String> {
    // ตรวจสอบว่ามีการจัดสรรประชาชนคนนี้แล้วรึยัง
    boolean existsByCitizenCode(String citizenCode);
}
