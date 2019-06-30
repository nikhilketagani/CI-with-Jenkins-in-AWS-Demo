package artifactory.test;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.Assert.*;


public class SeleniumTestIT {
	private static WebDriver driver;
	ChromeOptions chromeOptions;

	public SeleniumTestIT() {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless","--no-sandbox","--disable-dev-shm-usage");
	}
	@Test
	public void setup() throws InterruptedException {
		driver = new ChromeDriver(chromeOptions);
		driver.get("http://34.93.73.76/project-1.0-RAMA/");
		driver.manage().window().maximize();
		
		
		Thread.sleep(5000); // Let the user actually see something!
		
	}
	  @Test
  public void checkImageDisplayed(){
	  Boolean isDisplayed =driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/img")).isDisplayed();
	  assertTrue(isDisplayed, "image is not displayed on page");
  }
	 @Test
  public void checkText(){
	  WebElement textelement =driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/h1"));
	  String text =textelement.getText();
	assertEquals(text,"Hello DevOps Engineers and Architects!");
	    driver.quit();
  }
	
	/* @Test
	public void isPageTitleCorrect() throws InterruptedException {
		driver = new ChromeDriver(chromeOptions);		
		driver.get(System.getProperty("app.url"));
		driver.manage().window().maximize();
		Thread.sleep(5000);
		String actualTitle = driver.getTitle();	
		String expectedTitle = "DevOps Case Study - Calculator";
		assertEquals(expectedTitle, actualTitle);
		driver.quit();
	} */

}
