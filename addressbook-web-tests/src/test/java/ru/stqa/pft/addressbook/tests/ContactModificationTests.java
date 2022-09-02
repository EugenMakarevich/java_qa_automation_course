package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
  @Test
  public void testContactModification() {
    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData("Vasya_1", "Pupkin_1", "www leningrad", "1234567", "+48123456789", "vasyapupkin@gmail.com"));
    app.getContactHelper().updateContact();
    app.getNavigationHelper().goToHomePage();
  }
}
