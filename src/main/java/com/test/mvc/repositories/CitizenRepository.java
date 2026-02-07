package com.test.mvc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.test.mvc.models.CitizenModel;

public interface CitizenRepository extends JpaRepository<CitizenModel, String> {
    // ดึงข้อมูลประชาชนที่ยังไม่ได้รับการจัดสรรที่พัก
    @Query("SELECT c FROM CitizenModel c WHERE c.citizenCode NOT IN (SELECT a.citizenCode FROM AssignmentModel a)")
    List<CitizenModel> findUnassignedCitizens();
}
