package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class inviteUsersModalPage {
    private WebDriver driver;
    private By inviteUserModalOpenButton = By.name("inviteUsers");
    private WebElement inviteUsersModal;
    private WebElement cancelButton;
    private WebElement crossButton;
    private By emailField = By.cssSelector("div > div.en-c-text-field__container > input");
    private WebElement emailAddressBox;
    private WebElement inviteUsersButton;
    WebElement shadowHost;
    SearchContext shadowRoot;
    SearchContext parentShadowRoot;

    public inviteUsersModalPage(WebDriver driver){
        this.driver = driver;
    }

    public void openInviteUsers(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"en-ztna-Users-inviteUsersButton\"]")));
        shadowHost = driver.findElement(By.xpath("//*[@id=\"en-ztna-Users-inviteUsersButton\"]"));
        shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(inviteUserModalOpenButton).click();

        shadowHost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/main/div[2]/div[3]/en-dialog-0-1-15")));
        parentShadowRoot = shadowHost.getShadowRoot();
        inviteUsersModal = parentShadowRoot.findElement(By.cssSelector("div[role='dialog']"));
        shadowHost = inviteUsersModal.findElement(By.xpath("//*[@id=\"en-ztna-modal-saveButton\"]"));
        shadowRoot = shadowHost.getShadowRoot();
        inviteUsersButton = shadowRoot.findElement(By.cssSelector("button"));
    }

    public boolean modalIsDisplayed() {
        try {
            return inviteUsersModal.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }


    public void clickCancel(){
        shadowHost = inviteUsersModal.findElement(By.xpath("//*[@id=\"en-ztna-modal-cancelButton\"]"));
        shadowRoot = shadowHost.getShadowRoot();
        cancelButton = shadowRoot.findElement(By.cssSelector("button"));
        cancelButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(inviteUsersModal));
    }

    public void clickCross(){
        shadowHost = parentShadowRoot.findElement(By.cssSelector("div > div > div.en-c-dialog__header > en-button-0-1-15"));
        shadowRoot = shadowHost.getShadowRoot();
        crossButton = shadowRoot.findElement(By.cssSelector("button"));
        crossButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(inviteUsersModal));
    }

    public void enterEmail(String email){
        shadowHost = inviteUsersModal.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/div[3]/en-dialog-0-1-15/div[1]/div/div[2]/en-text-field-0-1-15"));
        shadowRoot = shadowHost.getShadowRoot();
        emailAddressBox = shadowRoot.findElement(emailField);
        emailAddressBox.sendKeys(email);
    }

    public String getEmailValue(){
        return emailAddressBox.getAttribute("value");
    }

    public boolean inviteUserButtonstatus(){
        return inviteUsersButton.isEnabled();
    }

    public void clickToInviteUsers(){
        inviteUsersButton.click();
    }

    public boolean checkEmailadded(String email) throws InterruptedException {

        usersPage page = new usersPage(driver);
        page.getRows();
        return page.checkEmailexists(email);
    }

}
