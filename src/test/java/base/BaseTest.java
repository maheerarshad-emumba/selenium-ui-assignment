package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import pages.fraZTApage;
import pages.loginPage;

import java.time.Duration;

public class BaseTest {
    private WebDriver driver;
    protected loginPage loginpage;
    protected static fraZTApage ztahomepage;

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://sso.extremecloudiq.com/login");
        loginpage = new loginPage(driver);

        loginpage.setEmailField("testuser06-ztna@emumba.com");
        loginpage.setPasswordField("Aerohive123");
        loginpage.clickSignIn();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://fra.extremecloudiq.com/"));

        driver.get("https://fra-uz.extremecloudiq.com/zta");
        driver.manage().window().maximize();
        ztahomepage = new fraZTApage(driver);
    }
}
