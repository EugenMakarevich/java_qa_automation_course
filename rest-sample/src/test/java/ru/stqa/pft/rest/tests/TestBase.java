package ru.stqa.pft.rest.tests;

import com.google.gson.JsonElement;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;

import java.io.IOException;

public class TestBase {

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
  }
  protected static final ApplicationManager app = new ApplicationManager();

  public boolean isIssueOpen(int issueId) throws IOException {
    JsonElement issue = app.rest().getIssue(issueId);
    String status = issue.getAsJsonObject().get("state_name").getAsString();
    return status.equals("Open");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
