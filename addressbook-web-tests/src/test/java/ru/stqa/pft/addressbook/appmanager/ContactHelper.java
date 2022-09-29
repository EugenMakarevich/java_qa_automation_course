package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
  private final ApplicationManager manager;

  public ContactHelper(ApplicationManager manager) {
    super(manager.wd);
    this.manager = manager;
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    if (creation && !isThereAGroup(contactData)) {
      manager.goTo().groupPage();
      manager.group().create(new GroupData().withName("test1"));
      manager.goTo().addNewContactPage();
    }
      type(By.name("firstname"), contactData.getFirstName());
      type(By.name("lastname"), contactData.getLastName());
      type(By.name("address"), contactData.getAddress());
      type(By.name("home"), contactData.getHomephone());
      type(By.name("mobile"), contactData.getMobilephone());
      type(By.name("email"), contactData.getEmail());
      if (creation) {
          new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      } else {
        Assert.assertFalse(isElementPresent(By.name("new_group")));
      }
  }

  public void modify(ContactData contact) {
    initContactModification();
    fillContactForm(contact, false);
    submitContactModification();
    manager.goTo().homePage();
  }

  public void delete() {
    selectContact();
    deleteContact();
    acceptContactDeletion();
    manager.goTo().homePage();
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void deleteContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptContactDeletion() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("//input[22]"));
  }

  public void create(ContactData contact) {
    manager.goTo().addNewContactPage();
    fillContactForm(contact, true);
    submitContactCreation();
    manager.goTo().homePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public boolean isThereAGroup(ContactData contactData) {
    Select selectGroup = new Select(wd.findElement(By.xpath("//select[@name='new_group']")));
    List<WebElement> allOptions = selectGroup.getOptions();
    for (WebElement option : allOptions) {
      String optionText = option.getText();
      if (optionText.equals(contactData.getGroup())) {
        return true;
      }
    }
    return false;
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String firstname = cells.get(2).getText();
      String lastname = cells.get(1).getText();
      ContactData contact = new ContactData(id, firstname, lastname);
      contacts.add(contact);
    }
    return contacts;
  }
}
