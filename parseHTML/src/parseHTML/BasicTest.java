package parseHTML;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicTest {
	public String testUrl = "https://github.com/egis/handbook/blob/master/Tech-Stack.md";
	public WebDriver driver;

	@BeforeTest
	public void setUrl() {
		driver = new ChromeDriver();
		driver.get(testUrl);
	}

	@Test
	public void titleTest() {
		// use Chrome to check if title is as expected
		WebDriverManager.chromedriver().setup();

		String expectedTitle = "handbook/Tech-Stack.md at master · egis/handbook";
		String actualTitle = driver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@AfterTest
	public void closeUrl() {
		driver.quit();
	}
}
