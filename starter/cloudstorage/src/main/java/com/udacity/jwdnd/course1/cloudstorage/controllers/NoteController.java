package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private UserService userService;
    private NoteService noteService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home/note/{noteid}")
    public String deleteUserNote(@PathVariable("noteid") Integer noteId, Model model) {
        Integer deleteNote = noteService.deleteUserNote(noteId);

        if (deleteNote < 1) {
            model.addAttribute("fileError", "Note delete failed!! Try again.");
            return "result";
        }

        model.addAttribute("success", true);
        return "result";

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
