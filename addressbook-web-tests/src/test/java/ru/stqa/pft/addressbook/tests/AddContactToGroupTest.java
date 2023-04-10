package ru.stqa.pft.addressbook.tests;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class AddContactToGroupTest extends TestBase {
  Contacts contacts;
  ContactData contact;
  Groups groups;
  GroupData group;

  @Test
  public void ensurePreconditions() {
    contacts = app.db().contacts();
    contact = app.contact().getRandomContact(contacts);
    groups = app.contact().getAvailableGroupsForContact(contact);
    if (groups.size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test group for contact with id = " + contact.getId()));
      app.goTo().homePage();
    }
    groups = app.contact().getAvailableGroupsForContact(contact);
    group = app.group().getRandomGroup(groups);
    System.out.println(contacts);
    System.out.println(contact);
    System.out.println(groups);
    System.out.println(group);
  }

  @Test
  public void testAddContactToGroup() {
    app.contact().selectContactById(contact.getId());
    app.contact().addToGroup(group);
    assertTrue(verifyContactIsAddedToGroup(contact, group));
    System.out.println(contacts);
    System.out.println(contact);
    System.out.println(groups);
    System.out.println(group);
  }


  private boolean verifyContactIsAddedToGroup(ContactData contact, GroupData group) {
    //To Clear cache
    Contacts contacts = app.db().contacts();
    ContactData cacheContact = findIfPresent(contact, contacts);
    Groups groups = cacheContact.getGroups();
    System.out.println(cacheContact);
    System.out.println(groups);

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
