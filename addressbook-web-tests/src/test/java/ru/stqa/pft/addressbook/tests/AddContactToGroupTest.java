package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Collections;
import java.util.List;

public class AddContactToGroupTest extends TestBase {

  @Test
  public void testAddContactToGroup() {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData contact = before.iterator().next();
    System.out.println(contact);
    Groups availableGroups = getAvailableGroups(contact);
    if(availableGroups.size() > 0) {
      GroupData group = availableGroups.iterator().next();
      System.out.println(group);
      app.contact().addToGroup(contact, group);
    } else {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test group for contact " + contact.getFirstName() + " " + contact.getLastName()));
      app.goTo().homePage();
      availableGroups = getAvailableGroups(contact);
      GroupData group = availableGroups.iterator().next();
      System.out.println(group);
      app.contact().addToGroup(contact, group);
    }
  }

  private Groups getAvailableGroups(ContactData contact) {
    Groups allGroups = app.db().groups();
    Groups contactGroups = contact.getGroups();
    allGroups.removeAll(contactGroups);
    System.out.println(allGroups);
    return allGroups;
  }
}
