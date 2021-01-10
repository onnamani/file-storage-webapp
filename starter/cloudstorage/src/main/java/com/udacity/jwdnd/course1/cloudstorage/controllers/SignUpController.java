package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SignUpController {

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String signUpGet(@ModelAttribute("userObject")UserModel userObject) { return "signup"; }
}
