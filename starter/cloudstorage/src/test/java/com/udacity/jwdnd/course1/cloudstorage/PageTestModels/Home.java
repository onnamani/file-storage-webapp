package com.udacity.jwdnd.course1.cloudstorage.PageTestModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home {

    @FindBy(className = "nav-item")
    private List<WebElement> navBar;

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-notes")
    private WebElement navContentDiv;

    @FindBy(id = "addNote")
    private WebElement addNote;


    @FindBy(className = "noteTitle")
    private List<WebElement> noteTitles;

    @FindBy(className = "noteDescription")
    private List<WebElement> noteDescriptions;

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

    @FindBy(id = "addCredential")
    private WebElement addCredential;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "nav-credentials")
    private WebElement credentialContentDiv;

    @FindBy(id = "credential-url")
    private WebElement credentialModalUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialModalUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialModalPassword;

    @FindBy(id = "credentialModalSave")
    private WebElement credentialModalSave;

    @FindBy(className = "credentialUrl")
    private List<WebElement> credentialUrls;

    @FindBy(className = "credentialUsername")
    private List<WebElement> credentialUsernames;

    @FindBy(className = "credentialPassword")
    private List<WebElement> credentialPasswords;

    public Home(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void userCreatesNote(String title, String description) {
        this.addNote.click();

        this.noteModalTitle.clear();
        this.noteModalDescription.clear();

        this.noteModalTitle.sendKeys(title);
        this.noteModalDescription.sendKeys(description);

        this.noteModalSave.click();

    }

    public void userEditNote(String title, String description) {

        this.noteModalTitle.clear();
        this.noteModalDescription.clear();

        this.noteModalTitle.sendKeys(title);
        this.noteModalDescription.sendKeys(description);

        this.noteModalSave.click();
    }

    public void userCreatesCredential(String url, String username, String password) {
        this.addCredential.click();

        this.credentialModalUrl.sendKeys(url);
        this.credentialModalUsername.sendKeys(username);
        this.credentialModalPassword.sendKeys(password);

        this.credentialModalSave.click();
    }

    public void userEditCredential(String url, String username, String password) {

        this.credentialModalUrl.clear();
        this.credentialModalUsername.clear();
        this.credentialModalPassword.clear();

        this.credentialModalUrl.sendKeys(url);
        this.credentialModalUsername.sendKeys(username);
        this.credentialModalPassword.sendKeys(password);

        this.credentialModalSave.click();
    }

    public List<WebElement> getNavBar() { return this.navBar; }

    public List<WebElement> getActiveNavBars() { return this.activeNavBars; }

    public WebElement getLogout() { return this.logout; }

    public WebElement getNoteTab() { return this.noteTab; }

    public WebElement getAddNote() { return this.addNote; }

    public WebElement getNavContentDiv() { return this.navContentDiv; }

    public List<WebElement> getNoteTitles() { return this.noteTitles; }

    public List<WebElement> getNoteDescriptions() { return this.noteDescriptions; }

    public List<WebElement> getEditButtons() { return this.editButtons; }

    public List<WebElement> getDeleteButtons() { return this.deleteButtons; }

    public WebElement getCredentialTab() { return this.credentialTab; }

    public WebElement getAddCredential() { return this.addCredential; }

    public WebElement getCredentialContentDiv() { return this.credentialContentDiv; }

    public WebElement getCredentialModalPassword() { return this.credentialModalPassword; }

    public List<WebElement> getCredentialUrls() { return this.credentialUrls; }

    public List<WebElement> getCredentialUsernames() { return this.credentialUsernames; }

    public List<WebElement> getCredentialPasswords() { return this.credentialPasswords; }
}
