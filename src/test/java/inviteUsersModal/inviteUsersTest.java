package inviteUsersModal;

import base.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.usersPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;

public class inviteUsersTest extends BaseTest {

    private usersPage page;

    @BeforeClass
    public void setUpPage(){
        page = ztahomepage.navigateToUsers();
    }

    @Test(priority = 1)
    public void smokeTestingInviteUserModalOpening() throws InterruptedException {
        page.openInviteUsers();
        assertTrue(page.modalIsDisplayed(), "Modal not displayed");
    }

    @Test(priority = 2)
    public void smokeTestingCancelButtonWorking(){
        page.clickCancel();
        assertFalse(page.modalIsDisplayed(), "Modal not closed from cancel button");
    }

    @Test(priority = 3)
    public void smokeTestingCrossButtonWorking(){
        page.openInviteUsers();
        page.clickCross();
        assertFalse(page.modalIsDisplayed(), "Modal is not closed from cross button");
    }

    @Test(priority = 4)
    public void smokeTestingEmailField(){
        page.openInviteUsers();

        page.enterEmail("maheer.arshad@emumba.com");
        assertEquals(page.getEmailValue(), "maheer.arshad@emumba.com", "Email address field did not take input");
        page.clickCross();
    }

    @Test(priority = 5)
    public void smokeTestingInviteUserButton(){
        page.openInviteUsers();
        page.enterEmail("");
        assertEquals(page.getEmailValue(), "", "Email address field not empty on giving empty string");
        assertFalse(page.inviteUserButtonstatus(), "Invite user button not disabled on empty email address field");

        page.enterEmail("maheer.arshad@emumba.com");
        assertEquals(page.getEmailValue(), "maheer.arshad@emumba.com", "Email address field did not take input");
        assertTrue(page.inviteUserButtonstatus(), "Invite user button not enabled on entering email address");
    }

    @Test(priority = 6)
    public void flowTestInviteUser() throws InterruptedException {
        page.clickToInviteUsers();
        assertTrue(page.checkEmailadded("maheer.arshad"), "Email address not added");
    }

//    @Test
//    public void gettingLocatorsTest() throws InterruptedException {
//        var page = ztahomepage.navigateToUsers();
//        page.getRows();
//        String email = page.getEmailofRow(3);
//        assertEquals(email, "iman.aslam@emumba.com");
//
//        String status = page.getStatusviaIndex(2);
//        assertEquals(status, "Active");
//
//        status = page.getStatusviaEmail("enduseriman@gmail.com");
//        assertEquals(status, "Active");
//
//        page.clickButtonDots("iman.aslam@emumba.com");
//
//    }
}
