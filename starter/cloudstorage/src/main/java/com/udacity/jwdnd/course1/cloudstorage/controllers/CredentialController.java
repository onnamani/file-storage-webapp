package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/home/credential/{credentialid}")
    public String deleteUserCredential(@PathVariable("credentialid") Integer credentialId, Model model) {
        Integer deleteCredential = credentialService.deleteUserCredential(credentialId);

        if (deleteCredential < 1) {
            model.addAttribute("fileError", "Ooops, delete credential failed. Try again");
            return "result";
        }

        model.addAttribute("success", true);
        return "result";
    }

    @PostMapping("/home/credential")
    public String postUserCredential(
            @ModelAttribute("credentialObject") Credential credentialObject,
            Authentication authentication,
            Model model) {
        Integer returnedRowCount;

        if (credentialObject.getCredentialid() == null) {
            User user = userService.getUser(authentication.getName());
            returnedRowCount = credentialService.saveUserCredential(credentialObject, user);
        } else {
            returnedRowCount = credentialService.updateUserCredential(credentialObject);
        }

        if (returnedRowCount < 1) {
            model.addAttribute("fileError", "Ooops, something went wrong. Try again!");
            return "result";
        }

        model.addAttribute("success", true);
        return "result";

    }

    @ModelAttribute("hrefValue")
    public String hrefValue() { return "/home?nav=credentials"; }
}
