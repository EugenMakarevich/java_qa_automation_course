package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withFirstName("Vasya").withLastName("Pupkin").withAddress("www leningrad").withHomePhone("1234567")
            .withMobilePhone("+48123456789").withEmail("vasyapupkin@gmail.com").withGroup("test1").withPhoto(photo);
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(before.size() + 1, equalTo(after.size()));
    assertThat(after, equalTo(before.withAdded(
            contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
