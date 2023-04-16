package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("78.8.14.29");
    assertEquals(ipLocation, "<GeoIP><Country>PL</Country><State>72</State></GeoIP>");
  }

  @Test
  public void testInvalidIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("78.8.14.xxx");
    assertEquals(ipLocation, "<GeoIP><Country>PL</Country><State>72</State></GeoIP>");
  }
}
