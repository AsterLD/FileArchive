package com.ld.filearchive.controllers;

import com.ld.filearchive.repos.ContentRepo;
import com.ld.filearchive.services.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainPageController {

    public MainPageController(ContentService contentService) {
        this.contentService = contentService;
    }

    private final ContentService contentService;


    @GetMapping
    public String showMainPage(Model model) {
        return contentService.showAllContentByIsAvailableForAll( model, true);
    }
}
