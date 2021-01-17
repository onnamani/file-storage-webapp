package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class Login {

    @FindBy(id = "labelUsername")
    private WebElement labelUsername;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "loginSubmit")
    private WebElement loginSubmit;

    @FindBy(id = "signupSuccess")
    private WebElement signUpSuccess;

    @FindBy(id = "clickToSignUp")
    private WebElement clickToSignUp;


    public Login(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password) {

        this.username.clear();
        this.password.clear();

        this.username.sendKeys(username);
        this.password.sendKeys(password);

        this.loginSubmit.click();

    }

    public WebElement getSignUpSuccess() { return this.signUpSuccess; }

    public WebElement getLabelUsername() { return this.labelUsername; }

    public WebElement getUsername() { return this.username; }

    public WebElement getPassword() { return this.password; }

    public WebElement getLoginSubmit() { return this.loginSubmit; }

    public WebElement getClickToSignUp() { return this.clickToSignUp; }

}
