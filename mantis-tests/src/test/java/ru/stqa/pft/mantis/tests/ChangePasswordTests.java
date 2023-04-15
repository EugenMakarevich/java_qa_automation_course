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
  String adminUsername = "administrator";
  String adminPassword = "root";
  String username = "user1681576339890";
  String password = "password";
  String email = "user1681576339890@localhost";
  String newPassword = "newpassword";

  //@BeforeMethod
  public void StartMailServer() {
    app.mail().start();
  }

  @Test
  public void TestChangePassword() throws MessagingException, IOException {
    app.getDriver().get(app.getProperty("web.baseUrl") + "login_page.php");
    app.baseHelper().type(By.id("username"), adminUsername);
    app.baseHelper().click(By.cssSelector("input[type='submit']"));
    app.baseHelper().type(By.id("password"), adminPassword);
    app.baseHelper().click(By.cssSelector("input[type='submit']"));
    app.baseHelper().click(By.xpath("//div[@id='sidebar']/ul/li[last()]/a/i"));
    app.baseHelper().click(By.xpath("//div[@id='main-container']/div[2]/div[2]/div/ul/li[2]/a"));
    app.baseHelper().click(By.xpath("//a[contains(text(),'user1681506995577')]"));
    app.baseHelper().click(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input"));
    if (!app.james().doesUserExists(username)) {
      app.james().createUser(username, password);
    }
    // I can't login because password is incorrect
    List<MailMessage> mailMessages = app.james().waitForMail(username, password, 60000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
    finish(confirmationLink, adminPassword);
    //assertTrue(app.newSession().login(username, password));
  }

  public void finish(String confirmationLink, String password) {
    app.getDriver().get(confirmationLink);
  }

  //@AfterMethod(alwaysRun = true)
  public void StopMailServer() {
    app.mail().stop();
  }
}
