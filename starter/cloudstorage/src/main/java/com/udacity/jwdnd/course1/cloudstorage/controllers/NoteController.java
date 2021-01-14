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

        Integer noteId;

        if (noteObject.getNoteid() == null) {
            User user = userService.getUser(authentication.getName());
            noteId = noteService.saveUserNote(noteObject, user);

        } else {
            noteId = noteService.updateUserNote(noteObject);
        }

        if (noteId == null) {
            model.addAttribute("fileError", "Ooops, something went wrong. Try again!");
            return "result";
        }

        model.addAttribute("success", true);
        return "result";

    }

    @ModelAttribute("hrefValue")
    public String hrefValue() { return "/home?nav=notes"; }
}
