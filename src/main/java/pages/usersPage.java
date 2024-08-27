package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class usersPage {
    private WebDriver driver;
    List<WebElement> rows;
    String email;
    WebElement specificRow;
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

    public usersPage(WebDriver driver){
        this.driver = driver;
    }

    public int getRows() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ag-center-cols-container']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ag-center-cols-container']/div[@role='row']")));
        rows = driver.findElements(By.xpath("//div[@class='ag-center-cols-container']/div[@role='row']"));
//        System.out.println("Number of rows found: " + rows.size());
//        for (WebElement row : rows) {
//            System.out.println("Row with row-index: " + row.getAttribute("row-index") + ", class: " + row.getAttribute("class"));
//        }
        return rows.size();
    }

    public String getEmailofRow(int index){
        specificRow = rows.get(index-1);
        WebElement emailCol = specificRow.findElement(By.cssSelector("[col-id='email']"));
        WebElement emailSpan = emailCol.findElement(By.xpath(".//span[contains(@id, 'cell-email')]"));
        String spanID = emailSpan.getAttribute("id");
        String emailXPath = "//*[@id='" + spanID + "']/div/en-text-passage-0-1-15/span";
        WebElement parentSpan = emailSpan.findElement(By.xpath(emailXPath));
        email = parentSpan.getText();
        return email;
    }

    public String getStatusviaIndex(int index){
        specificRow = rows.get(index-1);
        WebElement statusCol = specificRow.findElement(By.cssSelector("[col-id='status']"));
        String status = statusCol.findElement(By.xpath(".//en-tooltip-0-1-15/div[1]/en-chip-0-1-15")).getText();
        return status;
    }

    public String getStatusviaEmail(String mailaddress){
        for (WebElement row : rows) {
            //getting email column
            WebElement emailCol = row.findElement(By.cssSelector("[col-id='email']"));
            WebElement emailSpan = emailCol.findElement(By.xpath(".//span[contains(@id, 'cell-email')]"));

            //getting email text
            String emailXPath = "//*[@id='" + emailSpan.getAttribute("id") + "']/div/en-text-passage-0-1-15/span";
            WebElement parentSpan = emailSpan.findElement(By.xpath(emailXPath));
            String emailText = parentSpan.getText();

            if (emailText.equalsIgnoreCase(mailaddress)) {
                WebElement statusCol = row.findElement(By.cssSelector("[col-id='status']"));
                String status = statusCol.findElement(By.xpath(".//en-tooltip-0-1-15/div[1]/en-chip-0-1-15")).getText();
                return status;
            }
        }
        return null;
    }

    public void clickButtonDots(String mailaddress){
        for (WebElement row : rows) {
            WebElement emailCol = row.findElement(By.cssSelector("[col-id='email']"));
            WebElement emailSpan = emailCol.findElement(By.xpath(".//span[contains(@id, 'cell-email')]"));
            String emailXPath = "//*[@id='" + emailSpan.getAttribute("id") + "']/div/en-text-passage-0-1-15/span";
            WebElement parentSpan = emailSpan.findElement(By.xpath(emailXPath));
            String emailText = parentSpan.getText();

            if (emailText.equalsIgnoreCase(mailaddress)) {
                WebElement col3 = row.findElement(By.cssSelector("[col-id='1']"));
                //*[@id="root"]/div/main/div[2]/div[2]/div[2]/div/div/div[2]/div[2]/div[4]/div[1]/div[2]/div/div[4]/div[5]
                //*[@id="root"]/div/main/div[2]/div[2]/div[2]/div/div/div[2]/div[2]/div[4]/div[1]/div[2]/div/div[4]/div[5]/en-menu-0-1-15/div[1]/en-tooltip-0-1-15/div[1]/en-button-0-1-15//button
                WebElement shadowHost = col3.findElement(By.xpath(".//en-menu-0-1-15/div[1]/en-tooltip-0-1-15/div[1]/en-button-0-1-15"));
                SearchContext shadowRoot = shadowHost.getShadowRoot();
                WebElement button = shadowRoot.findElement(By.cssSelector("button"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", button);

                button.click();
                System.out.println("Button clicked for email: " + mailaddress);
                return;
            }
        }
        System.out.println("Email not found: " + mailaddress);
    }

    public boolean checkEmailexists(String mailaddress) {
        boolean emailFound = false;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        int expectedRowCount = rows.size() + 1;
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='ag-center-cols-container']/div[@role='row']"), expectedRowCount));

        for (int i = 0; i < rows.size(); i++) {
            try {
                WebElement row = rows.get(i);

                WebElement emailCol = row.findElement(By.cssSelector("[col-id='email']"));
                WebElement emailSpan = emailCol.findElement(By.xpath(".//span[contains(@id, 'cell-email')]"));

                String emailXPath = "//*[@id='" + emailSpan.getAttribute("id") + "']/div/en-text-passage-0-1-15/span";
                WebElement parentSpan = emailSpan.findElement(By.xpath(emailXPath));
                String emailText = parentSpan.getText();
                System.out.println(emailText);

                if (emailText.contains(mailaddress)) {
                    emailFound = true;
                    break;
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException: Element went stale. Re-attempting to locate.");
                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='ag-center-cols-container']/div[@role='row']"), expectedRowCount));
                rows = driver.findElements(By.xpath("//div[@class='ag-center-cols-container']/div[@role='row']"));
                System.out.println(rows.size());
                i--;
            }
        }
        return emailFound;
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


