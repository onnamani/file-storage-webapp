package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomePageController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String homePageGet(Authentication authentication, Model model) {
        UserModel user = this.userService.getUser(authentication.getName());
        List<FileModel> userFiles = fileService.getFiles(user.getUserId());

        model.addAttribute("userFiles", userFiles);

        return "home";
    }
}
