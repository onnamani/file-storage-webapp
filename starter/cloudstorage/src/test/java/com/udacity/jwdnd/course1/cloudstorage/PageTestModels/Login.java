package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login {

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "loginSubmit")
    private WebElement loginSubmit;


}
