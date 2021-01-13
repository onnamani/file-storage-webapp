package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/home/file/delete/{fileId}")
    public String deleteFile(Model model, @PathVariable("fileId") Integer fileId) {
        Integer deleteFile = fileService.deleteFile(fileId);

        if (deleteFile == null) {
            model.addAttribute("fileError", "Oooops..Something went wrong during deletion. Try again.");
            return "result";
        }

        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/home/file/view/{fileId:.+}")
    public ResponseEntity<ByteArrayResource> downloadFile(Model model, @PathVariable("fileId") Integer fileId) {
        File file = fileService.getFileById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFilePayload()));
    }

    @PostMapping("/home/file/newFile")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile multipartFile,
            Authentication authentication,
            Model model) {

        if (multipartFile.isEmpty()) {
            model.addAttribute("fileError", "You must select a file to upload");
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
                model.addAttribute("hrefValue", "/home");
                return "result";

            } catch (Exception e) {
                model.addAttribute("fileError", e.getMessage());
                return "result";
            }

        } else {
            model.addAttribute("fileError", "Kindly provide unique file name");
            return "result";
        }
    }
}
