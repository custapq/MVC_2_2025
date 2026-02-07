package com.test.mvc.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.test.mvc.repositories.AssignmentRepository;
import com.test.mvc.repositories.CitizenRepository;
import com.test.mvc.repositories.ShelterRepository;

import com.test.mvc.models.CitizenModel;
import com.test.mvc.models.ShelterModel;
import com.test.mvc.models.AssignmentModel;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CitizenRepository citizenRepository;
    private final ShelterRepository shelterRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
            CitizenRepository citizenRepository, ShelterRepository shelterRepository) {
        this.assignmentRepository = assignmentRepository;
        this.citizenRepository = citizenRepository;
        this.shelterRepository = shelterRepository;
    }

    // กำหนดศุนย์พักพิงให้ประชาชน
    public void assignCitizenToShelter(String citizenCode, String shelterCode) throws Exception {

        if (assignmentRepository.existsByCitizenCode(citizenCode)) {
            throw new Exception("ประชาชนคนนี้ถูกจัดสรรที่พักไปแล้ว");
        }

        CitizenModel citizen = citizenRepository.findById(citizenCode).orElse(null);
        ShelterModel shelter = shelterRepository.findById(shelterCode).orElse(null);

        if (citizen == null || shelter == null) {
            throw new Exception("ไม่พบข้อมูลประชาชน หรือ ศูนย์พักพิง");
        }

        // เช็คความจุ
        if (shelter.getCurrentOccupancy() >= shelter.getCapacity()) {
            throw new Exception("ศูนย์พักพิงนี้เต็มแล้ว! กรุณาเลือกศูนย์อื่น");
        }

        // เช็คว่ากลุ่มเสี่ยงต้องไปที่เสี่ยงต่ำเท่านั้น
        if (citizen.getCitizenType().equalsIgnoreCase("กลุ่มเสี่ยง")
                && !shelter.getRiskLevel().equalsIgnoreCase("ต่ำ")) {
            throw new Exception(
                    "ไม่สามารถจัดสรรได้: 'กลุ่มเสี่ยง' ต้องพักในศูนย์ความเสี่ยง 'ต่ำ' เท่านั้น");
        }

        // บันทึกข้อมูล
        AssignmentModel assignment = new AssignmentModel();
        assignment.setCitizenCode(citizenCode);
        assignment.setShelterCode(shelterCode);
        assignment.setStartDate(LocalDate.now().toString());

        assignmentRepository.save(assignment);

        // อัพเดตจำนวนผู้เข้าพักในศูนย์
        shelter.setCurrentOccupancy(shelter.getCurrentOccupancy() + 1);
        shelterRepository.save(shelter);
    }


    // ดึงรายการประชาชนที่ยังไม่ได้รับการจัดสรรโดยเรียงลำดำับความสำคัญ
    public List<CitizenModel> getUnassignedCitizensSorted() {
        List<CitizenModel> allCitizens = citizenRepository.findAll();

        return allCitizens.stream()
                .filter(c -> !assignmentRepository.existsByCitizenCode(c.getCitizenCode()))
                .sorted(Comparator.comparingInt(CitizenModel::getPriorityScore).reversed())
                .collect(Collectors.toList());
    }

    // ดึงกลุ่มประชาชนที่มีลำดับความสำคัญสูงสุด
    public List<CitizenModel> getTopPriorityCitizens() {
        List<CitizenModel> sortedList = getUnassignedCitizensSorted();
        
        if (sortedList.isEmpty()) {
            return sortedList;
        }
        int maxScore = sortedList.get(0).getPriorityScore();

        return sortedList.stream().filter(c -> c.getPriorityScore() == maxScore)
                .collect(Collectors.toList());
    }

}
