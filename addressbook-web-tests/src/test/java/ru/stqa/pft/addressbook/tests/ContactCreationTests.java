package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToAddNewContactPage();
    app.getContactHelper().fillContactForm(new ContactData("Vasya", "Pupkin", "www leningrad", "1234567", "+48123456789", "vasyapupkin@gmail.com"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
  }
}
