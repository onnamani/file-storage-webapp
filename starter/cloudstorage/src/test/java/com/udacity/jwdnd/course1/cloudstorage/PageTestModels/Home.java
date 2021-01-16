package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home {

    @FindBy(id = "logout")
    private WebElement logout;

    public Home(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutUser() {
        this.logout.click();
    }
}
