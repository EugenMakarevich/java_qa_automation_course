package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.testng.AssertJUnit.assertTrue;

public class AddContactToGroupTest extends TestBase {
  Contacts contacts;
  ContactData contact;
  Groups groups;
  GroupData group;

  @BeforeMethod
  public void ensurePreconditions() {
    contacts = app.db().contacts();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Vasya").withLastName("Pupkin").withAddress("www leningrad")
              .withHomePhone("1234567").withMobilePhone("+48123456789").withWorkPhone("666-666-666")
              .withEmail("vasyapupkin@gmail.com").withEmail2("pukinVasya@gmail.com").withEmail3("vasyaDestroyer@gmail.com")
              .withPhoto(new File("src/test/resources/stru.png"))
              //.withGroups(new HashSet<>(Arrays.asList(new GroupData().withName("new_group"))))
      );
      contacts = app.db().contacts();
    }
    contact = app.contact().getRandomContact(contacts);
    groups = app.contact().getAvailableGroupsForContact(contact);
    if (groups.size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test group for contact with id = " + contact.getId()));
      app.goTo().homePage();
      groups = app.contact().getAvailableGroupsForContact(contact);
    }
    group = app.group().getRandomGroup(groups);
  }

  @Test
  public void testAddContactToGroup() {
    app.contact().selectContactById(contact.getId());
    app.contact().addToGroup(group);
    assertTrue(verifyContactIsAddedToGroup(contact, group));
  }

  private boolean verifyContactIsAddedToGroup(ContactData contact, GroupData group) {
    //To Clear cache
    Contacts contacts = app.db().contacts();
    ContactData cacheContact = findIfPresent(contact, contacts);
    Groups groups = cacheContact.getGroups();

    boolean result = false;
    for (GroupData contactGroup : groups) {
      if (contactGroup.getId().equals(group.getId())) {
        result = true;
        break;
      }
    } return result;
  }

  ContactData findIfPresent(ContactData contact, Contacts contacts) {
    if (contacts.contains(contact)) {
      for (ContactData cont : contacts) {
        if (cont.equals(contact))
          return cont;
      }
    }
    return null;
  }
}
