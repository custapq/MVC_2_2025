package com.test.mvc.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.test.mvc.models.CitizenModel;
import com.test.mvc.repositories.CitizenRepository;

@Controller
@RequestMapping("/citizens")
public class CitizenController {

    private final CitizenRepository citizenRepository;

    public CitizenController(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }
    
    // ดึงข้อมูลประชาชนทั้งหมดเพื่อแสดงผลที่หน้า citizens
    @GetMapping
    public String viewCitizens(Model model, @RequestParam(required = false) String error) {
        // ตรวจสอบข้อผิดพลาดจากการเพิ่มข้อมูล
        if ("duplicate".equals(error)) {
            model.addAttribute("errorMessage", "รหัสประชาชนนี้มีอยู่ในระบบแล้ว");
        } else if ("invalid_length".equals(error)) {
            model.addAttribute("errorMessage", "รหัสประชาชนต้องมี 13 หลักและเป็นตัวเลขเท่านั้น");
        }

        model.addAttribute("citizens", citizenRepository.findAll());
        return "citizens";
    }

    // เพิ่มข้อมูลประชาชนใหม่
    @PostMapping("/add")
    public String addCitizen(@ModelAttribute CitizenModel citizen) {
        String code = citizen.getCitizenCode();
        // ตรวจสอบความยาวรหัสประชาชน
        if (code == null || code.length() != 13 || !code.matches("\\d+")) {
            return "redirect:/citizens?error=invalid_length";
        }
        // ตรวจสอบว่ามีข้อมูลซ้ำหรือไม่
        if (citizenRepository.existsById(code)) {
            return "redirect:/citizens?error=duplicate";
        }
        // บันทึกข้อมูลของประชาชน
        citizen.setStartDate(LocalDate.now().toString());
        citizenRepository.save(citizen);

        return "redirect:/citizens";
    }
}
