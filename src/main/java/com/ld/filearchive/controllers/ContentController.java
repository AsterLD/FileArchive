package com.ld.filearchive.controllers;

import com.ld.filearchive.models.Content;
import com.ld.filearchive.services.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }


    @RequestMapping("/download/{fileName}")
    public void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        contentService.downloadFile(response, fileName);
    }

    @GetMapping("/new")
    public String uploadNewFilePage(Model model) {
        return contentService.showUploadPage(model);
    }

    @GetMapping("/{contentId}")
    public String showContentPage(@PathVariable String contentId, Model model) {
        return contentService.showContentPage(model, contentId);
    }

    @PostMapping
    public String addNewFile(@RequestParam("file") MultipartFile file, Content content) {
        return contentService.addNewFileInfo(file, content);
    }

    @DeleteMapping("/{contentId}")
    public String deleteContent(@PathVariable("contentId") String contentId) {
        return contentService.deleteContentById(contentId);
    }
}
