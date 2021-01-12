package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/home/file/newFile")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile multipartFile,
            Authentication authentication,
            Model model) {

        if (multipartFile.isEmpty()) {
            model.addAttribute("fileUploadError", "You must select a file to upload");
            return "result";
        }

        User user =  this.userService.getUser(authentication.getName());

        if (this.fileService.isFileNameAvailable(multipartFile.getOriginalFilename())) {

            try {
                Integer fileId = this.fileService.store(multipartFile, user.getUserId());

                if (fileId == null) {
                    model.addAttribute("uploadError", true);
                    return "result";
                }

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
