package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Vasya").withLastName("Pupkin").withAddress("www leningrad").withHomephone("1234567")
            .withMobilephone("+48123456789").withEmail("vasyapupkin@gmail.com").withGroup("test1");
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();

    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }
}
