import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;

public class GoogleDemoTest {

    private static final String seleniumHubUrl = System.getenv("SELENIUM_HUB_URL");
    private static final String isHeadless = System.getenv("HEADLESS");
    private WebDriver driver;

    /**
     * The beforeClass method is called before any test in the class is called.
     * It is used to set up the WebDriver instance.
     * If the SELENIUM_HUB_URL environment variable is not set or is empty, the code will use the local ChromeDriver.
     * Otherwise it will use the Selenium Grid Hub at the given URL.
     */
    @BeforeTest
    public void beforeClass() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--proxy-server='direct://'", "--proxy-bypass-list=*");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-features=AutomaticTabCreation");
            chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            chromeOptions.setExperimentalOption("useAutomationExtension", false);
            chromeOptions.addArguments("disable-infobars");
            if (Objects.equals(isHeadless, "true")) {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--disable-gpu");
            }
            chromeOptions.addArguments("--window-size=1920,1080");

            if (Objects.isNull(seleniumHubUrl) || seleniumHubUrl.isEmpty()) {
                driver = new ChromeDriver(chromeOptions);
            } else {
                try {
                    driver = new RemoteWebDriver(
                            new URL(seleniumHubUrl),
                            chromeOptions);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Tests the browser by opening Google, printing its title, waiting 5 seconds,
     * clicking on "Images", waiting 5 seconds, printing the current URL and title,
     * and then quitting the browser.
     *
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
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
        driver.quit();
    }

//    @Test(priority = 2)
//    public void UnifiedPortalLogin() throws InterruptedException {
////        WebDriver driver;
////        WebDriverManager.chromedriver().setup();
////        driver = new ChromeDriver();
//        driver.get("https://dev-ufportal.thecareerlabs.info/login/user");
//        System.out.println(driver.getTitle());
//        Thread.sleep(5000L);
//        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("sribalajivelan@gmail.com");
//        Thread.sleep(5000L);
//        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("sribalajivelan");
////        Thread.sleep(10000L);
//        driver.findElement(By.xpath("//button[text()='Login']")).click();
////        Thread.sleep(2000L);
//        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        WebElement error = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Fail -> Invalid Provider or Password!']")));
////        WebElement error =  driver.findElement(By.xpath("//div[text()='Fail -> Invalid Provider or Password!']"));
//        if (error != null) {
//            System.out.println("Error on " + error.toString());
//        }
//    }
}
