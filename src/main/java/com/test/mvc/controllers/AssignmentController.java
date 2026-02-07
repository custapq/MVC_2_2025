package com.test.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.test.mvc.repositories.CitizenRepository;
import com.test.mvc.repositories.ShelterRepository;
import com.test.mvc.services.AssignmentService;

@Controller
@RequestMapping("/assign")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final ShelterRepository shelterRepository;

    public AssignmentController(AssignmentService assignmentService,
            ShelterRepository shelterRepository, CitizenRepository citizenRepository) {
        this.assignmentService = assignmentService;
        this.shelterRepository = shelterRepository;
    }

    @GetMapping
    public String viewAssign(Model model) {
        model.addAttribute("shelters", shelterRepository.findAll());
        model.addAttribute("dropdownCitizens", assignmentService.getTopPriorityCitizens());
        model.addAttribute("allCitizens", assignmentService.getUnassignedCitizensSorted());

        return "assign";
    }

    @PostMapping
    public String assign(@RequestParam String citizenCode, @RequestParam String shelterCode,
            RedirectAttributes redirectAttributes) {
        try {
            assignmentService.assignCitizenToShelter(citizenCode, shelterCode);
            redirectAttributes.addFlashAttribute("successMessage", "จัดสรรที่พักสำเร็จ!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/assign";
    }
}
