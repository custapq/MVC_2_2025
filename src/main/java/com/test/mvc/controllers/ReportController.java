package com.test.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.mvc.repositories.AssignmentRepository;
import com.test.mvc.services.AssignmentService;

@Controller
public class ReportController {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentService assignmentService;

    public ReportController(AssignmentRepository assignmentRepository,
            AssignmentService assignmentService) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentService = assignmentService;
    }

    // ดึงข้อมูลเพื่อไปแสดงผลที่หน้า report
    @GetMapping("/report")
    public String report(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        model.addAttribute("unassignedCitizens", assignmentService.getUnassignedCitizensSorted());

        return "report";
    }
}
