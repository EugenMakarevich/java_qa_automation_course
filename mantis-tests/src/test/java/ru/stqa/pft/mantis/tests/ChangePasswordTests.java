package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {
  String username = "user1681591532259";
  String password = "password";
  String email = "user1681591532259@localhost";
  String newPassword = "newpassword";

  @Test
  public void TestChangePassword() throws MessagingException, IOException {
    app.goTo().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.goTo().manage();
    app.goTo().manageUsers();
    app.baseHelper().click(By.xpath("//a[contains(text(),'user1681506995577')]"));
    app.baseHelper().click(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input"));
    if (!app.james().doesUserExists(username)) {
      app.james().createUser(username, password);
    }
    //app.james().drainEmail(username, password);
    List<MailMessage> mailMessages = app.james().waitForMail(username, password, 30000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
    finish(confirmationLink, password);
    //assertTrue(app.newSession().login(username, password));
  }

  public void finish(String confirmationLink, String password) {
    app.getDriver().get(confirmationLink);
  }
}