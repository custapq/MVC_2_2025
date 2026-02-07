package com.test.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.mvc.repositories.AssignmentRepository;
import com.test.mvc.repositories.CitizenRepository;

@Controller
public class ReportController {

    private final CitizenRepository citizenRepository;
    private final AssignmentRepository assignmentRepository;

    public ReportController(
            CitizenRepository citizenRepository,
            AssignmentRepository assignmentRepository
    ) {
        this.citizenRepository = citizenRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/report")
    public String report(Model model) {
        model.addAttribute("citizens", citizenRepository.findAll());
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "report";
    }
}