package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home {

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-notes")
    private WebElement navContentDiv;

    @FindBy(id = "addNote")
    private WebElement addNote;

    @FindBy(id = "noteTitle")
    private WebElement noteTitle;

    @FindBy(id = "noteDescription")
    private WebElement noteDescription;

    @FindBy(id = "note-title")
    private WebElement noteModalTitle;

    @FindBy(id = "note-description")
    private WebElement noteModalDescription;

    @FindBy(id = "noteModalSave")
    private WebElement noteModalSave;

    @FindBy(className = "active")
    private List<WebElement> activeNavBars;

    @FindBy(className = "btn-success")
    private List<WebElement> editButtons;

    @FindBy(className = "btn-danger")
    private List<WebElement> deleteButtons;

    public Home(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutUser() {
        this.logout.click();
    }

    public void userCreatesNote(String titleInput, String descriptionInput) {
        this.noteTab.click();
        this.addNote.click();

        this.noteModalTitle.sendKeys(titleInput);
        this.noteModalDescription.sendKeys(descriptionInput);

        this.noteModalSave.click();

    }

    public void userEditNote(String titleInput, String descriptionInput) {
        this.editButtons.get(0).click();

        this.noteModalTitle.clear();
        this.noteModalDescription.clear();

        this.noteModalTitle.sendKeys(titleInput);
        this.noteModalDescription.sendKeys(descriptionInput);

        this.noteModalSave.click();
    }

    public List<WebElement> getActiveNavBars() { return this.activeNavBars; }

    public WebElement getNoteTab() { return this.noteTab; }

    public WebElement getNavContentDiv() { return this.navContentDiv; }

    public WebElement getNoteTitle() { return this.noteTitle; }

    public WebElement getNoteDescription() { return this.noteDescription; }

    public List<WebElement> getDeleteButtons() { return this.deleteButtons; }
}
