package com.ld.filearchive.repos;

import com.ld.filearchive.models.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContentRepo extends MongoRepository<Content, String> {
    Content findContentByContentId(String contentId);
    Content findContentByContentFileName(String contentFileName);
    List<Content>getContentByContentCategoryId(String categoryId);
    List<Content>getContentByIsAvailableForAll(Boolean availableForAll);
}
