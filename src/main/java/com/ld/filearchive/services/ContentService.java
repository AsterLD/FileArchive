package com.ld.filearchive.services;

import com.ld.filearchive.models.Content;
import com.ld.filearchive.models.Category;
import com.ld.filearchive.repos.ContentRepo;
import com.ld.filearchive.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Objects;
import java.util.UUID;

/*
 * Класс ContentService, хранит в себе логику, для работы ContentController.
 * showContentPage - Отображает информацию о загруженном ранее файле,
 * showAllContentByIsAvailableForAll - Отображает список загруженных файлов,
 * у которых указанно отображение в общей ленте,
 * showCategoryContent - Отображает список файлов, относящихся к конкретной категории,
 * showUploadPage - Отображает страницу сохранения нового файла,
 * addNewFileInfo - В информацию о файле сохраняется добавивший его пользователь, если файл был загружен,
 * вызывается метод saveFile,
 * deleteContentById - Удаляется информация о файле из БД,
 * downloadFile - при запросе пользователем файла, проверяется его существование, после этого,
 * пользователю возвращается отображения, или сам файл, в зависимости от формата,
 * saveFile:
 * 1. Проверяется, существует ли путь, куда нужно сохранить файл, если нет, создаются недостающие папки,
 * 2. используя UUID и старое имя файла, генерирует новое, чтобы исключить возможность совпадения имен,
 * 3. сохраняет файл в папке, используя новое имя,
 * добавляет в объект класса информацию об имени файла и папке, куда сохранен файл.
 */

@Service
public class ContentService {

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepo categoryRepo;
    private final ContentRepo contentRepo;

    public ContentService(CategoryRepo categoryRepo, ContentRepo contentRepo) {
        this.categoryRepo = categoryRepo;
        this.contentRepo = contentRepo;
    }

    public String showContentPage(Model model, String contentId) {
        model.addAttribute("content", contentRepo.findContentByContentId(contentId));
        return "content/contentPage";
    }

    public String showAllContentByIsAvailableForAll(Model model, Boolean availableForAll) {
        model.addAttribute("contentList", contentRepo.getContentByIsAvailableForAll(availableForAll));
        return "homePage";
    }

    public String showCategoryContent(Model model, String categoryFolder) {
        if (Objects.equals(categoryRepo.findCategoryByCategoryFolderNameIgnoreCase(categoryFolder), null)) {
            return "redirect:/categories";
        }
        model.addAttribute("contentList",
                contentRepo.getContentByContentCategoryId(
                        categoryRepo.findCategoryByCategoryFolderNameIgnoreCase(categoryFolder).getCategoryId()
                ));
        return "content/categoryContentPage";
    }

    public String showUploadPage(Model model) {
        model.addAttribute("categoryList", categoryRepo.findAll());
        return "content/newContentPage";
    }

    public String addNewFileInfo (MultipartFile file, Content content) {
        content.setUploadedUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if(file.getSize() > 0) {
            saveFile(file, content);
        }
        contentRepo.save(content);
        return "redirect:/";
    }

    public String deleteContentById(String contentId) {
        contentRepo.deleteById(contentId);
        return "redirect:/";
    }

    public void downloadFile(HttpServletResponse response, String fileName){
        Content content = contentRepo.findContentByContentFileName(fileName);
        File file = new File(uploadPath + "/" + content.getContentCategoryFolder() + "/"  + fileName);
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            response.setContentLength((int) file.length());
            try {
                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(MultipartFile file, Content content){
        Category category = categoryRepo.findByCategoryId(content.getContentCategoryId());
        String fullPath = uploadPath + "/" + category.getCategoryFolderName();
        File sectionDir = new File(fullPath);
        if (!sectionDir.exists()){
            sectionDir.mkdirs();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        try {
            file.transferTo(new File(fullPath + "/" + resultFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.setContentCategoryFolder(category.getCategoryFolderName());
        content.setContentFileName(resultFilename);
    }
}
