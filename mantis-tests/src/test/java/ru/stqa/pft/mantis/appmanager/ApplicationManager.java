package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.addressbook.appmanager.HelperBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
  private final Properties properties;
  private WebDriver wd;
  JavascriptExecutor js;
  private String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private HelperBase baseHelper;
  private NavigationHelper navigationHelper;
  private DbHelper dbHelper;
  private UserHelper userHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target","local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  protected boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public WebDriver getDriver() {
    if(wd == null) {
      if (browser.equals(BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
      } else if (browser.equals(BrowserType.CHROME)) {
        //Hack to fix issue with ChromeDriver 111
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        wd = new ChromeDriver(co);
      } else if (browser.equals(BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }
      wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
      js = (JavascriptExecutor) wd;
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }

  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }

  public HelperBase baseHelper() {
    if (baseHelper == null) {
      baseHelper = new HelperBase(this);
    }
    return baseHelper;
  }

  public NavigationHelper goTo() {
    if (navigationHelper == null) {
      navigationHelper = new NavigationHelper(this);
    }
    return navigationHelper;
  }

  public DbHelper db() {
    if (dbHelper == null) {
      dbHelper = new DbHelper(this);
    }
    return dbHelper;
  }

  public UserHelper user() {
    if (userHelper == null) {
      userHelper = new UserHelper(this);
    }
    return userHelper;
  }
}
