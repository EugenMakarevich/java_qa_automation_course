package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class DeleteContactFromGroupTest extends TestBase {
  Contacts contacts;
  ContactData contact;
  Groups groups;
  GroupData group;

  @BeforeMethod
  public void ensurePreconditions() {
    contacts = app.db().contacts();
    contact = app.contact().getRandomContact(contacts);
    groups = contact.getGroups();
    if (groups.size() == 0) {
      Groups allGroups = app.db().groups();
      if (allGroups.size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("Test group for contact with id = " + contact.getId()));
        app.goTo().homePage();
      }
      allGroups = app.db().groups();
      group = app.group().getRandomGroup(allGroups);
      app.contact().selectContactById(contact.getId());
      app.contact().addToGroup(group);
      app.goTo().homePage();
      assertTrue(verifyContactIsAddedToGroup(contact, group));
    } else {
      group = app.group().getRandomGroup(groups);
    }
  }

  @Test
  public void ContactFromGroupDeletionTest() {
    app.contact().filterGroup(group);
    app.contact().selectContactById(contact.getId());
    app.contact().removeFromGroup();
    assertFalse(verifyContactIsAddedToGroup(contact, group));
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
