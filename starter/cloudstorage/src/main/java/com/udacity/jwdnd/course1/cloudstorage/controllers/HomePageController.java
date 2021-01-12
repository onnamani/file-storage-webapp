package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomePageController {

    private final FileService fileService;
    private final UserService userService;

    public HomePageController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }


    @GetMapping()
    public String homePageGet(Model model, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        List<File> userFiles = fileService.getUserFileNames(user.getUserId());

        model.addAttribute("userFiles", userFiles);

        return "home";
    }
}
