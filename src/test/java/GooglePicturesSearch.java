import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class GooglePicturesSearch {
    private WebDriver driver;
    private String result;
    private static String PATH_FOR_SAVING_SCREENSHOT = "D:/screenShoots/screenshot1.png";

    @BeforeTest
    public void webDriverProperty() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void browserConfiguration() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com.ua/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test
    public void checkImgVisibility() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement search = driver.findElement(By.xpath("//input[contains(@name, 'q')]"));
        search.sendKeys("Sing2" + Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@data-hveid, 'CAIQAw')]")));
        WebElement searchButton = driver.findElement(By.xpath("//a[contains(@data-hveid, 'CAIQAw')]"));
        searchButton.click();
        WebElement expectedIMG = driver.findElement(By.xpath("//img[contains(@alt, 'Sing 2 - Wikipedia')]"));
        assertTrue(expectedIMG.isDisplayed());
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(scrFile, new File(PATH_FOR_SAVING_SCREENSHOT));
            result = "Screenshot saved to " + PATH_FOR_SAVING_SCREENSHOT;
        } catch (IOException e) {
            result = "Failed to save screenshot to Disc " + e.getMessage();
        }
        System.out.println(result);
    }
}
