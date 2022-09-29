package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.List;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Vasya", "Pupkin", "www leningrad", "1234567", "+48123456789", "vasyapupkin@gmail.com", "test1"));
    }
  }

  @Test
  public void testContactDeletion() {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteContact();
    app.getContactHelper().acceptContactDelition();
    app.goTo().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    before.remove(0);
    Assert.assertEquals(before, after);
  }
}
