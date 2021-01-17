package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.Home;
import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.Login;
import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.Result;
import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.SignUp;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private EncryptionService encryptionService;

	private SignUp signUp;
	private Login login;
	private Result result;
	private Home home;

	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);

		signUp = new SignUp(driver);
		login = new Login(driver);
		result = new Result(driver);
		home = new Home(driver);

		createUserHelper();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void unauthorizedUserRestrictions() {

		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());

	}

	@Test
	public void userSignUpActions() {

		wait.until(ExpectedConditions.titleIs("Login"));
		assertEquals("Login", driver.getTitle());
		assertEquals("You've signed up successfully. Please login to continue",
								login.getSignUpSuccess().getText());

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		assertEquals("Home", driver.getTitle());
		clickElement(home.getLogout());

		wait.until(ExpectedConditions.visibilityOf(login.getClickToSignUp()));
		assertEquals("Login", driver.getTitle());

	}

	@Test
	public void userCreateNote() {

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		wait.until(ExpectedConditions.visibilityOfAllElements(home.getNavBar()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getNoteTab()));
		clickElement(home.getNoteTab());

		wait.until(ExpectedConditions.visibilityOf(home.getAddNote()));
		home.userCreatesNote("Today's Task",
				"1. Take the morning exercise. \n2. Walk the dog. \n3. Take the trash out.");


		wait.until(ExpectedConditions.titleIs("Result"));
		assertEquals("Success", result.getSuccessFlash().getText());
		assertEquals("Result", driver.getTitle());

		wait.until(ExpectedConditions.titleIs("Result"));
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.titleIs("Home"));
		assertEquals("Notes", home.getActiveNavBars().get(0).getText());
		assertEquals(home.getNavContentDiv(), home.getActiveNavBars().get(1));

		assertEquals("Today's Task", home.getNoteTitles().get(0).getText());

		assertEquals("1. Take the morning exercise. 2. Walk the dog. 3. Take the trash out.",
				home.getNoteDescriptions().get(0).getText());
	}

	@Test
	public void userEditNote() {

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		wait.until(ExpectedConditions.visibilityOfAllElements(home.getNavBar()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getNoteTab()));
		clickElement(home.getNoteTab());

		wait.until(ExpectedConditions.visibilityOf(home.getAddNote()));
		home.userCreatesNote("Today's Task",
				"1. Take the morning exercise. \n2. Walk the dog. \n3. Take the trash out.");

		wait.until(ExpectedConditions.titleIs("Result"));
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.visibilityOfAllElements(home.getEditButtons()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getEditButtons().get(0)));
		clickElement(home.getEditButtons().get(0));
		home.userEditNote("Today's Task!!!",
				"1. Take the morning exercise. \n2. Walk the dog. \n3. Make breakfast.");

		wait.until(ExpectedConditions.titleIs("Result"));
		assertEquals("Success", result.getSuccessFlash().getText());
		assertEquals("Result", driver.getTitle());

		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.titleIs("Home"));
		assertEquals("Today's Task!!!", home.getNoteTitles().get(0).getText());
		assertEquals("1. Take the morning exercise. 2. Walk the dog. 3. Make breakfast.",
				home.getNoteDescriptions().get(0).getText());
	}

	@Test
	public void userDeleteNote() {

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		wait.until(ExpectedConditions.visibilityOfAllElements(home.getNavBar()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getNoteTab()));
		clickElement(home.getNoteTab());

		wait.until(ExpectedConditions.elementToBeClickable(home.getAddNote()));
		home.userCreatesNote("Today's Task",
				"1. Take the morning exercise. \n2. Walk the dog. \n3. Take the trash out.");

		wait.until(ExpectedConditions.titleIs("Result"));
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.visibilityOfAllElements(home.getDeleteButtons()));
		clickElement(home.getDeleteButtons().get(0));

		wait.until(ExpectedConditions.titleIs("Result"));
		assertEquals("Success", result.getSuccessFlash().getText());
		clickElement(result.getSuccessContinue());


		assertEquals(0, home.getDeleteButtons().size());
	}

	@Test
	public void userCreateCredential() throws InterruptedException {

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		wait.until(ExpectedConditions.visibilityOfAllElements(home.getNavBar()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getCredentialTab()));
		clickElement(home.getCredentialTab());

		wait.until(ExpectedConditions.visibilityOf(home.getAddCredential()));
		home.userCreatesCredential(
				"www.gmail.com",
				"smithJ",
				"password"
		);

		wait.until(ExpectedConditions.titleIs("Result"));
		assertEquals("Success", result.getSuccessFlash().getText());
		assertEquals("Result", driver.getTitle());
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.titleIs("Home"));
		assertEquals("Credentials", home.getActiveNavBars().get(0).getText());
		assertEquals(home.getCredentialContentDiv(), home.getActiveNavBars().get(1));

		assertEquals("www.gmail.com", home.getCredentialUrls().get(0).getText());
		assertEquals("smithJ", home.getCredentialUsernames().get(0).getText());

		List<Credential> userCredentials = credentialService.getUserCredentials(
				userService.getUser("jsmith").getUserId()
		);

		String key = userCredentials.get(0).getKey();

		assertEquals(encryptionService.encryptValue("password", key),
							home.getCredentialPasswords().get(0).getText());

	}

	@Test
	public void userEditCredential() {

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		wait.until(ExpectedConditions.visibilityOfAllElements(home.getNavBar()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getCredentialTab()));
		clickElement(home.getCredentialTab());

		wait.until(ExpectedConditions.visibilityOf(home.getAddCredential()));
		home.userCreatesCredential(
				"www.gmail.com",
				"smithJ",
				"password"
		);

		wait.until(ExpectedConditions.titleIs("Result"));
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.visibilityOfAllElements(home.getEditButtons()));
		home.getEditButtons().get(0).click();

		assertEquals("password", home.getCredentialModalPassword().getAttribute("value"));
		home.userEditCredential(
				"www.yahoomail.com",
				"josmith",
				"password12345"
		);

		wait.until(ExpectedConditions.titleIs("Result"));
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.titleIs("Home"));
		assertEquals("www.yahoomail.com", home.getCredentialUrls().get(0).getText());
		assertEquals("josmith", home.getCredentialUsernames().get(0).getText());

		List<Credential> userCredentials = credentialService.getUserCredentials(
				userService.getUser("jsmith").getUserId()
		);

		String key = userCredentials.get(0).getKey();

		assertEquals(encryptionService.encryptValue("password12345", key),
				home.getCredentialPasswords().get(0).getText());

	}

	@Test
	public void userDeleteCredential() {

		loginUserHelper();

		wait.until(ExpectedConditions.titleIs("Home"));
		wait.until(ExpectedConditions.visibilityOfAllElements(home.getNavBar()));
		wait.until(ExpectedConditions.elementToBeClickable(home.getCredentialTab()));
		clickElement(home.getCredentialTab());

		wait.until(ExpectedConditions.visibilityOf(home.getAddCredential()));
		home.userCreatesCredential(
				"www.gmail.com",
				"smithJ",
				"password"
		);

		wait.until(ExpectedConditions.titleIs("Result"));
		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.visibilityOfAllElements(home.getDeleteButtons()));
		clickElement(home.getDeleteButtons().get(0));

		wait.until(ExpectedConditions.titleIs("Result"));
		assertEquals("Success", result.getSuccessFlash().getText());
		assertEquals("Result", driver.getTitle());

		clickElement(result.getSuccessContinue());

		wait.until(ExpectedConditions.titleIs("Home"));
		assertEquals(0, home.getCredentialUrls().size());
	}

	public void createUserHelper() {
		driver.get("http://localhost:" + this.port);
		driver.manage().window().maximize();

		wait.until(ExpectedConditions.visibilityOf(login.getClickToSignUp()));
		login.getClickToSignUp().click();

		wait.until(ExpectedConditions.visibilityOfAllElements(signUp.getPageElements()));
		signUp.submitSignUp("John", "Smith", "jsmith", "12345");
	}

	public void loginUserHelper() {
		wait.until(ExpectedConditions.titleIs("Login"));
		wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(
				login.getUsername(),
				login.getPassword(),
				login.getSignUpSuccess(),
				login.getLoginSubmit()
		)));
		login.loginUser("jsmith", "12345");
	}

	public void clickElement(WebElement webElement) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webElement);
	}

}
