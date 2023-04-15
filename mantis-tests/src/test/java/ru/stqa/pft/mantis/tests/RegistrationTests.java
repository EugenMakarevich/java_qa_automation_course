package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class RegistrationTests extends TestBase {
  //@BeforeMethod
  public void StartMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost", now);
    app.james().createUser(user, password);
    app.registration().start(user, email);
    //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
    String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }



  //@AfterMethod(alwaysRun = true)
  public void StopMailServer() {
    app.mail().stop();
  }
}
