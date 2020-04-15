package ie.projects.phase6.domain.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.Math;

public class GeoLocation {
    private double x;
    private double y;

    @JsonCreator
    public GeoLocation(@JsonProperty("x") double x, @JsonProperty("y") double y){
        this.x = x;
        this.y = y;
    }

    public double getx() {return x;}
    public void setx(double x) {this.x = x;}

    public double gety() {return y;}
    public void sety(double y) {this.y = y;}

    public double distanceCalculator(double x, double y) {
        double temp = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
        return Math.sqrt(temp);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
