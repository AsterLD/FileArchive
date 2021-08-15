package com.ld.filearchive.services;

import com.ld.filearchive.models.Category;
import com.ld.filearchive.repos.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.Objects;

/*
 * Класс CategoryService, хранит в себе логику, для работы CategoryController.
 * showAllCategories - Отображает список всех разделов,
 * saveNewCategory - Сохраняет новый раздел в БД,
 * deleteCategoryById - Удаляет раздел из БДЮ
 */

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public String showAllCategories(Model model) {
        model.addAttribute("categoryList", categoryRepo.findAll());
        return "category/categoryListPage";
    }

    public String saveNewCategory(Category category) {
        if(!Objects.equals(categoryRepo.findCategoryByCategoryFolderNameIgnoreCase(category.getCategoryFolderName()),
                null) || !Objects.equals(
                        categoryRepo.findCategoryByCategoryTitleIgnoreCase(category.getCategoryTitle()), null)) {
            return "category/newCategoryPage";
        }
        categoryRepo.save(category);
        return "redirect:/categories";
    }

    public String deleteCategoryById(String sectionId) {
        categoryRepo.deleteById(sectionId);
        return "redirect:/categories";
    }

}
