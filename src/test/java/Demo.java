import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class Demo {

    private WebDriver driver;

    @BeforeTest
    public void beforeClass() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
    }

    @Test(priority = 1)
    public void testBrowser() throws InterruptedException {
        driver.get("https://google.com");
        String title = driver.getTitle();
        Thread.sleep(5000L);
        System.out.println(title);
        driver.findElement(By.xpath("//*[text()='Images']")).click();
        Thread.sleep(5000L);
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
//        driver.quit();
    }

    @Test(priority = 2)
    public void UnifiedPortalLogin() throws InterruptedException {
//        WebDriver driver;
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
        driver.get("https://dev-ufportal.thecareerlabs.info/login/user");
        System.out.println(driver.getTitle());
        Thread.sleep(5000L);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("sribalajivelan@gmail.com");
        Thread.sleep(5000L);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("sribalajivelan");
//        Thread.sleep(10000L);
        driver.findElement(By.xpath("//button[text()='Login']")).click();
//        Thread.sleep(2000L);
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement error = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Fail -> Invalid Provider or Password!']")));
//        WebElement error =  driver.findElement(By.xpath("//div[text()='Fail -> Invalid Provider or Password!']"));
        if (error != null) {
            System.out.println("Error on " + error.toString());
        }
    }
}
