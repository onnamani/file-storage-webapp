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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private UserService userService;

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
	public void beforeEach() {

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

		if (!userService.isUsernameAvailable("jsmith")) {
			userService.deleteUser("jsmith");
		}

		signUp.submitSignUp("John", "Smith", "jsmith", "12345");

		assertEquals("Login", driver.getTitle());
		assertEquals("You've signed up successfully. Please login to continue",
								login.getSignUpSuccess().getText());

		login.loginUser("jsmith", "12345");
		assertEquals("Home", driver.getTitle());

		home.logoutUser();
		unauthorizedUserRestrictions();

		try {
			Thread.sleep(3000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
