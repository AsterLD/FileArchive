package com.ld.filearchive.controllers;

import com.ld.filearchive.models.Category;
import com.ld.filearchive.services.CategoryService;
import com.ld.filearchive.services.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ContentService contentService;

    public CategoryController(CategoryService categoryService, ContentService contentService) {
        this.categoryService = categoryService;
        this.contentService = contentService;
    }

    @GetMapping
    public String showAllCategories(Model model) {
        return categoryService.showAllCategories(model);
    }

    @GetMapping("/{categoryFolder}")
    public String showCategoryContent(Model model, @PathVariable String categoryFolder) {
        return contentService.showCategoryContent(model, categoryFolder);
    }

    @GetMapping("/new")
    public String createNewCategory() {
        return "category/newCategoryPage";
    }

    @PostMapping
    public String saveNewCategory(Category category) {
        return categoryService.saveNewCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable String categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }
}
