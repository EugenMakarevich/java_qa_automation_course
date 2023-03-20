package ru.stqa.pft.addressbook.tests;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ContactDataTest extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Vasya").withLastName("Pupkin").withAddress("www leningrad")
              .withHomePhone("1234567").withMobilePhone("+48123456789").withWorkPhone("666-666-666")
              .withEmail("vasyapupkin@gmail.com").withEmail2("pukinVasya@gmail.com").withEmail3("vasyaDestroyer@gmail.com")
              .withGroup("test1"));
    }
  }

  @Test()
  public void testContactData() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    SoftAssertions.assertSoftly(softly -> {
      softly.assertThat(contact.getAddress().equals(contactInfoFromEditForm.getAddress())).isTrue();
      softly.assertThat(contact.getAllEmails().equals(mergeEmails(contactInfoFromEditForm))).isTrue();
      softly.assertThat(contact.getAllPhones().equals(mergePhones(contactInfoFromEditForm))).isTrue();
    });
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .collect(Collectors.joining("\n"));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactDataTest::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }
}
