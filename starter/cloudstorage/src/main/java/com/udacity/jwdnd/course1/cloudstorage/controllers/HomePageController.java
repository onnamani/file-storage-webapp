package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomePageController {

    private final NoteService noteService;
    private final FileService fileService;
    private final UserService userService;

    public HomePageController(FileService fileService,
                              UserService userService,
                              NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String homePageGet(
            @RequestParam(value="nav", defaultValue = "files") String nav,
            @ModelAttribute("noteObject") Note noteObject,
            Model model,
            Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        List<File> userFiles = fileService.getUserFileNames(user.getUserId());
        List<Note> userNotes = noteService.getUserNotes(user.getUserId());

        model.addAttribute("userId", user.getUserId());
        model.addAttribute("userNotes", userNotes);
        model.addAttribute("userFiles", userFiles);

        if (nav.equals("notes")) {
            model.addAttribute("addNavClass", "active");

        } else {
            model.addAttribute("addFileClass", true);

        }
        return "home";

    }
}
