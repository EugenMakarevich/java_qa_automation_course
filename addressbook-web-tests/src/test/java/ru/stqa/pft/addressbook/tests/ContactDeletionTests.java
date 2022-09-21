package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {
  @Test
  public void testContactDeletion() {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Vasya", "Pupkin", "www leningrad", "1234567", "+48123456789", "vasyapupkin@gmail.com", "test1"));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteContact();
    app.getContactHelper().acceptContactDelition();
    app.getNavigationHelper().goToHomePage();
  }
}
