package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.addressbook.appmanager.HelperBase;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void login(String username, String password) {
    app.getDriver().get(app.getProperty("web.baseUrl") + "login_page.php");
    app.baseHelper().type(By.id("username"), username);
    app.baseHelper().click(By.cssSelector("input[type='submit']"));
    app.baseHelper().type(By.id("password"), password);
    app.baseHelper().click(By.cssSelector("input[type='submit']"));
  }

  public void manage() {
    app.baseHelper().click(By.xpath("//div[@id='sidebar']/ul/li[last()]/a/i"));
  }

  public void manageUsers() {
    app.baseHelper().click(By.xpath("//div[@id='main-container']/div[2]/div[2]/div/ul/li[2]/a"));
  }
}
