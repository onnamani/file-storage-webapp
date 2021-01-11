package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {
    private UserService userService;

    public SignUpController(UserService userService) { this.userService = userService; }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String signUpGet(@ModelAttribute("userObject") User userObject) { return "signup"; }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUpPost(
            @ModelAttribute("userObject") User userObject,
            Model model,
            RedirectAttributes redirectAttributes) {

        Integer userId = 0;

        if (userService.isUsernameAvailable(userObject.getUsername())) {
            userId = userService.createUser(userObject);
        } else {
            model.addAttribute("signupError", "The username already exists. Try another one.");
            model.addAttribute("errorFlash", true);
            return "signup";
        }

        if (userId == null) {
            model.addAttribute("signupError", "Ooops...Something went wrong. Please try again later");
            model.addAttribute("errorFlash", true);
            return "signup";
        } else {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        }
    }
}
