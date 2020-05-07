package ie.projects.phase6.service.restaurant.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodDTO {
    private String name;
    private String description;
    private float popularity;
    private String image;
    private float price;
    private String restaurantId;
    private String restaurantName;
    private Integer count = null;
    private Float oldPrice = null;


    public FoodDTO(String name, String description, float popularity, String image, float price){
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.image = image;
        this.price = price;
    }

    public FoodDTO(String name, String description, float popularity, String image, float price, String restaurantId, String restaurantName, Integer count, Float oldPrice){
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.image = image;
        this.price = price;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.count = count;
        this.oldPrice = oldPrice;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
