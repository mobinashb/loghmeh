package ie.projects.phase7.domain.foreignServiceObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
