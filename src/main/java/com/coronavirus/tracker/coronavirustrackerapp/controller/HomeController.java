package com.coronavirus.tracker.coronavirustrackerapp.controller;

import com.coronavirus.tracker.coronavirustrackerapp.mapping.AttributeMapping;
import com.coronavirus.tracker.coronavirustrackerapp.mapping.ViewMapping;
import com.coronavirus.tracker.coronavirustrackerapp.service.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // == Service autowired ==
    @Autowired
    CoronavirusDataService coronavirusDataService;

    // == Home Mapping ==
    @GetMapping(AttributeMapping.HOME_ATTRIBUTE)
    public String home(Model model) {

        // Title attribute
        model.addAttribute("homeTitle", "Coronavirus Tracker App");

        // Sum the total cases in each country to get the global number
        int total = coronavirusDataService.getAllStats().stream()
                .mapToInt(stat -> stat.getLatestTotalCases())
                .sum();
        model.addAttribute("globalReportedCases", "GLOBAL REPORTED CASES : " + total);

        // Sum the total new cases in each country to get the global number
        int totalNewCases = coronavirusDataService.getAllStats().stream()
                .mapToInt(stat -> stat.getDelta())
                .sum();
        model.addAttribute("totalNewCases", "GLOBAL NEW REPORTED CASES : " + "+ " + totalNewCases);

        // Memory Object Attribute
        model.addAttribute("locationStats", coronavirusDataService.getAllStats());

        return ViewMapping.HOME_VIEW;
    }
}
