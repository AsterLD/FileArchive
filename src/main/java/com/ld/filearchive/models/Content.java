package com.ld.filearchive.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Класс Content, используется для хранения информации об одном загруженном файле.
 */

@Document(collection = "Content")
public class Content {

    @Id
    private String contentId;

    private String contentTitle;

    private String contentFileName;

    private String contentCategoryFolder;

    private String uploadedUser;

    private String contentDescription;

    private String contentCategoryId;

    private boolean isAvailableForAll;

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentFileName() {
        return contentFileName;
    }

    public void setContentFileName(String contentFileName) {
        this.contentFileName = contentFileName;
    }

    public String getContentCategoryFolder() {
        return contentCategoryFolder;
    }

    public void setContentCategoryFolder(String contentCategoryFolder) {
        this.contentCategoryFolder = contentCategoryFolder;
    }

    public String getUploadedUser() {
        return uploadedUser;
    }

    public void setUploadedUser(String uploadedUser) {
        this.uploadedUser = uploadedUser;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentCategoryId() {
        return contentCategoryId;
    }

    public void setContentCategoryId(String contentCategoryId) {
        this.contentCategoryId = contentCategoryId;
    }

    public boolean isAvailableForAll() {
        return isAvailableForAll;
    }

    public void setAvailableForAll(boolean availableForAll) {
        isAvailableForAll = availableForAll;
    }
}
