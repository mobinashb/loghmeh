package loghmeh.domain.foreignServiceObjects;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Food{
    private Integer count = null;
    @JsonAlias("foodName")
    protected String name;
    protected String description;
    protected float popularity;
    protected String image;
    protected String restaurantId;
    protected float price;
    private Float oldPrice = null;

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public float getPopularity() {return popularity;}
    public void setPopularity(float popularity) {this.popularity = popularity;}

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRestaurantId() { return restaurantId;}
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId;}

    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}

    public Float getOldPrice() {return oldPrice;}
    public void setOldPrice(Float price) {this.oldPrice = price;}

}