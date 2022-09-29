package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData("Vasya", "Pupkin", "www leningrad", "1234567", "+48123456789", "vasyapupkin@gmail.com", "test1"));
    }
  }
  @Test
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    //Здесь я могу выделить переменную index и положить туда индекс? Не надо,
    //потому что в след. лекции будет замена на объект Contact
    ContactData contact = new ContactData(before.get(0).getId(), "Vasya_1", "Pupkin_1");
    app.contact().modify(contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(0);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
