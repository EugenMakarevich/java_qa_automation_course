package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {
  String username;
  String password;
  String email;
  String newPassword = "newpassword";

  UserData user;

  @BeforeMethod
  public void ensurePreconditions() throws MessagingException {
    Users users = app.db().users();
    user = app.user().getRandomUser(users);
    username = user.getUsername();
    password = user.getPassword();
    email = user.getEmail();
   /* if (!app.james().doesUserExists(username)) {
      app.james().createUser(username, password);
    } else {
      app.james().drainEmail(username, password);
    }*/
  }

  @Test
  public void TestChangePassword() throws MessagingException, IOException {
    app.goTo().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.goTo().manage();
    app.goTo().manageUsers();
    app.user().selectUserById(user.getId());
    app.user().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
    app.getDriver().get(confirmationLink);
    //Steps to change password
    //assertTrue(app.newSession().login(username, newPassword));
  }
}