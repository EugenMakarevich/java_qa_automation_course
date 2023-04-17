package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class IssueResolutionTest extends TestBase {
  @Test
  public void testIssueResolution() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(1);
    System.out.println("Test has been run");
  }
}
