package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginPage {
    private WebDriver driver;
    private By emailField = By.id("username");
    private By passwordField = By.id("password");
    private By signinButton = By.xpath("//*[@id=\"loginForm\"]/div[2]/form/button");

    public loginPage(WebDriver driver){
        this.driver = driver;
    }

    public void setEmailField(String email){
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPasswordField(String pass){
        driver.findElement(passwordField).sendKeys(pass);
    }

    public void clickSignIn(){
        driver.findElement(signinButton).click();
    }
}
