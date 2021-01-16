package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUp {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signUpSubmit")
    private WebElement submit;

    @FindBy(id = "backToLogin")
    private WebElement backToLogin;

    public SignUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submitSignUp(
            String firstName,
            String lastName,
            String username,
            String password
    ) {
        this.firstName.clear();
        this.lastName.clear();
        this.username.clear();
        this.password.clear();

        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.username.sendKeys(username);
        this.password.sendKeys(password);

        this.submit.click();
    }


}
