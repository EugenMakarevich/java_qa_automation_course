package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {
  String newPassword = "newpassword";

  UserData user;

  @BeforeMethod
  public void ensurePreconditions() {
    app.mail().start();
    Users users = app.db().users();
    user = app.user().getRandomUser(users);
  }

  @Test
  public void TestChangePassword() throws IOException {
    app.goTo().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.goTo().manage();
    app.goTo().manageUsers();
    app.user().selectUserById(user.getId());
    app.user().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, user.getEmail());
    app.user().setNewPassword(confirmationLink, newPassword);
    assertTrue(app.newSession().login(user.getUsername(), newPassword));
  }

  @AfterMethod(alwaysRun = true)
  public void StopMailServer() {
    app.mail().stop();
  }
}