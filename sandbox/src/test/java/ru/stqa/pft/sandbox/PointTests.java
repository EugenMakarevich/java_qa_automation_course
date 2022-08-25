package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance1 () {
    Point p1 = new Point(4, 8);
    Point p2 = new Point(1, 5);
    Assert.assertEquals(p1.distance(p2), 4.242640687119285);
  }

  @Test
  public void testDistance2 () {
    Point p1 = new Point(-3, 5);
    Point p2 = new Point(2, -5);
    Assert.assertEquals(p1.distance(p2), 11.180339887498949);
  }
}
