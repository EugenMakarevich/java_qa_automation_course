package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Vasya").withLastName("Pupkin").withAddress("www leningrad").withHomePhone("1234567")
              .withMobilePhone("+48123456789").withEmail("vasyapupkin@gmail.com")); //.withGroup("test1")
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.without(deletedContact).size()));
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}
