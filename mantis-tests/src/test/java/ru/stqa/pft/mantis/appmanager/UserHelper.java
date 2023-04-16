package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.addressbook.appmanager.HelperBase;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.util.Random;

public class UserHelper extends HelperBase {
  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public UserData getRandomUser(Users users) {
    UserData[] arrayUsers = users.toArray(new UserData[users.size()]);
    return arrayUsers[new Random().nextInt(users.size())];
  }

  public void selectUserById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id))).click();
  }

  public void resetPassword() {
    wd.findElement(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input")).click();
  }
}
