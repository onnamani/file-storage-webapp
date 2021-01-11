package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;


    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam MultipartFile fileUpload,
            Authentication authentication,
            Model model) {

        if (fileUpload.isEmpty()) {
            model.addAttribute("fileUploadError", "You must select a file to upload");
            return "result";
        }

        User user =  this.userService.getUser(authentication.getName());

        if (this.fileService.isFileNameAvailable(fileUpload.getOriginalFilename(), user.getUserId())) {

            try {
                Integer fileId = this.fileService.insertFile(fileUpload, user.getUserId());

                if (fileId == null) {
                    model.addAttribute("uploadError", true);
                    return "result";
                }

                File file = new File();

                file.setFileId(fileId);
                file.setFilename(fileUpload.getOriginalFilename());
                user.addUserFile(file);

                model.addAttribute("success", true);
                return "result";

            } catch (Exception e) {
                model.addAttribute("fileUploadError", e.getMessage());
                return "result";
            }


        } else {
            model.addAttribute("fileUploadError", "Kindly provide unique file name");
            return "result";
        }
    }
}
