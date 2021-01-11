package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("fileUpload")MultipartFile fileUpload,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (fileUpload.isEmpty()) {
            redirectAttributes.addFlashAttribute("fileUploadError", "You must select a file to upload");
            return "redirect:/result";
        }

        UserModel user =  this.userService.getUser(authentication.getName());

        if (this.fileService.isFileNameAvailable(fileUpload.getOriginalFilename(), user.getUserId())) {

            try {
                Integer fileId = this.fileService.insertFile(fileUpload, user.getUserId());

                if (fileId == null) {
                    redirectAttributes.addFlashAttribute("fileUploadError", true);
                    return "redirect:/result";
                }

                FileModel fileModel = new FileModel();

                fileModel.setFileId(fileId);
                fileModel.setFilename(fileUpload.getOriginalFilename());
                user.addUserFile(fileModel);

                redirectAttributes.addFlashAttribute("success", true);
                return "redirect:/result";

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("fileUploadError", e.getMessage());
                return "redirect:/result";
            }


        } else {
            redirectAttributes.addFlashAttribute("fileUploadError", "Kindly provide unique file name");
            return "redirect:/result";
        }
    }
}
