package ch.overney.springBootWatering.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {
    private int x;
    private int y;

    // Needed by Jackson
    public Point() { }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    // --

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return (other.x == x) && (other.y == y);
        }
        return false;
    }

    public String toJSON() {
        return "{" + "\"x\": " + x + ", \"y\": " + y + '}';
    }

    public String prettyPrint() {
        return "(" + x + ", " + y + ")";
    }
}
