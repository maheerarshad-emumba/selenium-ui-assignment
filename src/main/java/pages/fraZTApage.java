package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class fraZTApage {
    private WebDriver driver;
    private By IAM = By.cssSelector("li > button > span");
    private By userButton = By.linkText("Users");
    private By host = By.xpath("//*[@id=\"en-ztna-NavCollapseItem-IAM\"]");

    public fraZTApage(WebDriver driver){
        this.driver = driver;
    }

    public usersPage navigateToUsers(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(host));
//
//        WebElement outerHost = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/en-side-menu-0-1-15/div/en-list-0-1-15"));
//        SearchContext outerRoot = outerHost.getShadowRoot();
//        outerHost = outerRoot.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/en-side-menu-0-1-15/div/en-list-0-1-15"));
//        outerRoot = outerHost.getShadowRoot();
        WebElement shadowHost = driver.findElement(host);
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(IAM).click();
        shadowHost = driver.findElement(By.xpath("//*[@id=\"en-ztna-NavItem-Users-iam--users\"]"));
        shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(By.cssSelector("li > button")).click();
//        driver.findElement(userButton).click();
        return new usersPage(driver);
    }







}
