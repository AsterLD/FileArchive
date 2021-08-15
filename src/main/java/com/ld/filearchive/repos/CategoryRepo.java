package com.ld.filearchive.repos;

import com.ld.filearchive.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepo extends MongoRepository<Category, String> {
    List<Category>findAll();
    Category findCategoryByCategoryTitleIgnoreCase(String categoryTitle);
    Category findCategoryByCategoryFolderNameIgnoreCase(String categoryFolderName);
    Category findByCategoryId(String categoryId);
}
