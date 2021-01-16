package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Result {

    @FindBy(id = "successFlash")
    private WebElement successFlash;

    @FindBy(id = "successContinue")
    private WebElement successContinue;

    public Result(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getSuccessFlash() { return this.successFlash; }

    public WebElement getSuccessContinue() { return this.successContinue; }
}
