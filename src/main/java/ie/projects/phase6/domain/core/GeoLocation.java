package ie.projects.phase6.domain.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.Math;

public class GeoLocation {
    private float x;
    private float y;

    @JsonCreator
    public GeoLocation(@JsonProperty("x") float x, @JsonProperty("y") float y){
        this.x = x;
        this.y = y;
    }

    public float getx() {return x;}
    public void setx(float x) {this.x = x;}

    public float gety() {return y;}
    public void sety(float y) {this.y = y;}

    public float distanceCalculator(float x, float y) {
        Double temp = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
        return (float) Math.sqrt(temp);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
