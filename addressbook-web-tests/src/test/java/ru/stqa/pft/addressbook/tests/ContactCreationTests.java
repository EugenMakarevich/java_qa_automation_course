package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Vasya", "Pupkin", "www leningrad", "1234567", "+48123456789", "vasyapupkin@gmail.com", "test1");
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();

    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    for (ContactData contacts : before) {
      System.out.println(contact);
    }
    System.out.println("");
    for (ContactData contacts : after) {
      System.out.println(contact);
    }

    Assert.assertEquals(before, after);
  }
}
