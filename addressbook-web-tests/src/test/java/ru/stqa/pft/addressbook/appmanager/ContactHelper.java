package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    manager.goTo().homePage();
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id="+ id +"']")).click();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteContact();
    acceptContactDeletion();
    manager.goTo().homePage();
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='"+ id +"']")).click();
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

  public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<>();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String firstname = cells.get(2).getText();
      String lastname = cells.get(1).getText();
      contacts.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname));
    }
    return contacts;
  }

}
