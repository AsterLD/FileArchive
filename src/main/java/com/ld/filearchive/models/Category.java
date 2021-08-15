package com.ld.filearchive.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Класс Category, используется для хранения информации об одном разделе, для хранения файлов,
 * Строка categoryFolderName хранит название папки, где сохраняются все файлы данного раздела.
 * Строка categoryTitle идексируется.
 */

@Document(collection = "Category")
public class Category {

    @Id
    private String categoryId;

    @Indexed(unique=true)
    private String categoryTitle;

    private String categoryDescription;

    private String categoryFolderName;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryFolderName() {
        return categoryFolderName;
    }

    public void setCategoryFolderName(String categoryFolderName) {
        this.categoryFolderName = categoryFolderName;
    }
}
