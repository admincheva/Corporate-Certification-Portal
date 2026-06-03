package org.example.corporatecertificationportal.controller;

import lombok.RequiredArgsConstructor;
import org.example.corporatecertificationportal.dto.DashboardDTO;
import org.example.corporatecertificationportal.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{username}")
    public DashboardDTO getDashboard(
            @PathVariable String username
    ) {

        return dashboardService
                .getDashboard(username);
    }
}
