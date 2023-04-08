package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Collections;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class AddContactToGroupTest extends TestBase {

  @Test
  public void testAddContactToGroup() {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData contact = before.iterator().next();
    System.out.println(contact);
    Groups availableGroups = getAvailableGroups(contact);
    GroupData group;
    if (availableGroups.size() > 0) {
      group = availableGroups.iterator().next();
      System.out.println(group);
      app.contact().addToGroup(contact, group);
    } else {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test group for contact " + contact.getFirstName() + " " + contact.getLastName()));
      app.goTo().homePage();
      availableGroups = getAvailableGroups(contact);
      group = availableGroups.iterator().next();
      System.out.println(group);
      app.contact().addToGroup(contact, group);
      Groups contactGroups = contact.getGroups();
      System.out.println(contactGroups);
    }
    assertTrue(verifyContactIsAddedToGroup(group));
  }

  private Groups getAvailableGroups(ContactData contact) {
    Groups allGroups = app.db().groups();
    Groups contactGroups = contact.getGroups();
    allGroups.removeAll(contactGroups);
    System.out.println(allGroups);
    return allGroups;
  }

  private boolean verifyContactIsAddedToGroup(GroupData group) {
    Contacts before = app.db().contacts();
    ContactData contact = before.iterator().next(); //To clear cache
    Groups contactGroups = contact.getGroups();
    boolean result = false;
    for (GroupData contactGroup : contactGroups) {
      if (contactGroup.getId().equals(group.getId())) {
        result = true;
        break;
      }
    } return result;
  }
}
