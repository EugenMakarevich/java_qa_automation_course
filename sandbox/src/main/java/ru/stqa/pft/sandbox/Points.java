package ru.stqa.pft.sandbox;

public class Points {
  public static void main(String[] args) {
    Point p1 = new Point(4, 8);
    Point p2 = new Point(1, 5);

    System.out.println("Calculate distance by function: " + distance(p1,p2));
    System.out.println("Calculate distance by method: " + p1.distance(p2));
  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
  }
}
