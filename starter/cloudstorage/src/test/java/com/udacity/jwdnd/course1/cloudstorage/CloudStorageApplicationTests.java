package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.Home;
import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.Login;
import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.Result;
import com.udacity.jwdnd.course1.cloudstorage.PageTestModels.SignUp;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private SignUp signUp;
	private Login login;
	private Result result;
	private Home home;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();
		signUp = new SignUp(driver);
		login = new Login(driver);
		result = new Result(driver);
		home = new Home(driver);

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
	public void userSignUpActions() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		signUp.submitSignUp("John", "Smith", "jsmith", "12345");

		assertEquals("Login", driver.getTitle());
		assertEquals("You've signed up successfully. Please login to continue",
								login.getSignUpSuccess().getText());

		Thread.sleep(5000);
		login.loginUser("jsmith", "12345");
		assertEquals("Home", driver.getTitle());

		Thread.sleep(5000);
		home.logoutUser();
		
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());

	}

	@Test
	public void createNote() throws InterruptedException {

		driver.get("http://localhost:" + this.port);

		login.getClickToSignUp().click();
		signUp.submitSignUp("John", "Smith", "jsmith", "12345");

		Thread.sleep(5000);
		login.loginUser("jsmith", "12345");

		Thread.sleep(5000);
		home.getNoteTab().click();
		home.userCreatesNote("Today's Task",
				"1. Take the morning exercise. \n2. Walk the dog. \n3. Take the trash out.");

		Thread.sleep(5000);
		assertEquals("Success", result.getSuccessFlash().getText());
		assertEquals("Result", driver.getTitle());


		result.getSuccessContinue().click();

		Thread.sleep(10000);
		assertEquals("Notes", home.getActiveNavBars().get(0).getText());
		assertEquals(home.getNavContentDiv(), home.getActiveNavBars().get(1));

		assertEquals("Today's Task", home.getNoteTitle().getText());

		assertEquals("1. Take the morning exercise. 2. Walk the dog. 3. Take the trash out.",
				home.getNoteDescription().getText());
	}

}
