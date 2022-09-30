package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.List;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Vasya").withLastName("Pupkin").withAddress("www leningrad").withHomephone("1234567")
              .withMobilephone("+48123456789").withEmail("vasyapupkin@gmail.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactDeletion() {
    List<ContactData> before = app.contact().list();
    app.contact().delete();
    List<ContactData> after = app.contact().list();

    before.remove(0);
    Assert.assertEquals(before, after);
  }

}
