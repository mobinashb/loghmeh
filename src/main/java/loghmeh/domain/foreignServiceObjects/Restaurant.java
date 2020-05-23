package loghmeh.domain.foreignServiceObjects;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonFilter("restaurant")
public class Restaurant
{
    private String id;
    private String name;
    private GeoLocation location;
    private String logo;
    private ArrayList<Food> menu;

    @JsonCreator
    public Restaurant(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("location") GeoLocation location, @JsonProperty("logo") String logo, @JsonProperty("menu")ArrayList<Food> menu){
        this.id = id;
        this.name = name;
        this.location = location;
        this.logo = logo;
        this.menu = menu;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {this.name = name;}

    public GeoLocation getLocation() { return location; }

    public String getLogo() { return logo; }

    public ArrayList<Food> getMenu() { return menu; }
}