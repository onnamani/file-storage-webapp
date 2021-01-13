package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private UserService userService;
    private NoteService noteService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/home/note")
    public String saveUserNote(
            @ModelAttribute("noteObject") Note noteObject,
            Model model,
            Authentication authentication) {

        User user = userService.getUser(authentication.getName());
        noteObject.setUserid(user.getUserId());

        Integer noteId = noteService.saveUserNote(noteObject);

        if (noteId == null) {
            model.addAttribute("fileError", "Ooops, something went wrong. Tray again!");
            return "result";
        }

        model.addAttribute("success", true);
        model.addAttribute("hrefValue", "/home?nav=notes");
        return "result";
    }
}
