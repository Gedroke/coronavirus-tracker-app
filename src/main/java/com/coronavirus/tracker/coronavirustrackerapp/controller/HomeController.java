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
        model.addAttribute("homeTitle", "Coronavirus Tracker App");
        model.addAttribute("locationStats", coronavirusDataService.getAllStats());
        return ViewMapping.HOME_VIEW;
    }
}
