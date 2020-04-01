package ie.projects.phase1.core;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Food{
    private Integer count = null;
    @JsonAlias("foodName")
    protected String name;
    protected String description;
    protected double popularity;
    protected String image;
    protected String restaurantId;
    protected double price;
    private Double oldPrice = null;

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public double getPopularity() {return popularity;}
    public void setPopularity(double popularity) {this.popularity = popularity;}

    public String getImage() { return image; }

    public String getRestaurantId() { return restaurantId;}
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public Double getOldPrice() {return oldPrice;}
    public void setOldPrice(Double price) {this.oldPrice = price;}

}