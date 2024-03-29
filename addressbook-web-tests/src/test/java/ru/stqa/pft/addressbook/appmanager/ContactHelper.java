package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Random;

import static java.time.Duration.ofSeconds;

public class ContactHelper extends HelperBase {
  private final ApplicationManager manager;

  public ContactHelper(ApplicationManager manager) {
    super(manager.wd);
    this.manager = manager;
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
      type(By.name("firstname"), contactData.getFirstName());
      type(By.name("lastname"), contactData.getLastName());
      type(By.name("address"), contactData.getAddress());
      type(By.name("home"), contactData.getHomePhone());
      type(By.name("mobile"), contactData.getMobilePhone());
      type(By.name("work"), contactData.getWorkPhone());
      type(By.name("email"), contactData.getEmail());
      type(By.name("email2"), contactData.getEmail2());
      type(By.name("email3"), contactData.getEmail3());
      attach(By.name("photo"), contactData.getPhoto());
      //Here I need to check that the group from file is exists in DB
      if (creation) {
        if(contactData.getGroups().size() > 0) {
          Assert.assertTrue(contactData.getGroups().size() == 1);
          new Select(wd.findElement(By.name("new_group"))).selectByVisibleText
                  (contactData.getGroups().iterator().next().getName());
        }
      } else {
        Assert.assertFalse(isElementPresent(By.name("new_group")));
      }
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    manager.goTo().homePage();
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteContact();
    acceptContactDeletion();
    manager.goTo().homePage();
  }

  public void create(ContactData contact) {
    manager.goTo().addNewContactPage();
    fillContactForm(contact, true);
    submitContactCreation();
    manager.goTo().homePage();
  }

  public void addToGroup(GroupData group) {
    new Select(wd.findElement(By.name("to_group"))).selectByValue(group.getId().toString());
    wd.findElement(By.name("add")).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void deleteContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptContactDeletion() {
    wd.switchTo().alert().accept();
  }

  public void submitContactModification() {
    click(By.xpath("//input[22]"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public boolean isThereAGroup(ContactData contactData) {
    Select selectGroup = new Select(wd.findElement(By.xpath("//select[@name='new_group']")));
    List<WebElement> allOptions = selectGroup.getOptions();
    for (WebElement option : allOptions) {
      String optionText = option.getText();
      //if (optionText.equals(contactData.getGroup())) {
      //  return true;
      //}
    }
    return false;
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String firstname = cells.get(2).getText();
      String lastname = cells.get(1).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contacts.add(new ContactData().withId(id).withFirstName(firstname)
              .withLastName(lastname).withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    manager.goTo().homePage();
    return new ContactData().withFirstName(firstName).withLastName(lastName)
            .withAddress(address).withHomePhone(home).withMobilePhone(mobile)
            .withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  public ContactData getRandomContact(Contacts contacts) {
    ContactData[] arrayContacts = contacts.toArray(new ContactData[contacts.size()]);
    return arrayContacts[new Random().nextInt(contacts.size())];
  }

  public Groups getAvailableGroupsForContact(ContactData contact) {
    Groups allGroups = manager.db().groups();
    Groups contactGroups = contact.getGroups();
    allGroups.removeAll(contactGroups);
    return allGroups;
  }

  public void filterGroup(GroupData group) {
    new Select(wd.findElement(By.name("group"))).selectByValue(group.getId().toString());
    new WebDriverWait(wd, ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.name("remove")));
  }

  public void removeFromGroup() {
    wd.findElement(By.name("remove")).click();
  }
}
