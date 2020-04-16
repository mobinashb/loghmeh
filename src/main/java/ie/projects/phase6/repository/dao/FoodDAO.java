package ie.projects.phase6.repository.dao;

public class FoodDAO {
    String restaurantId;
    String name;
    String description;
    float popularity;
    String image;
    float price;

    public FoodDAO(String restaurantId, String name, String description, float popularity, String image, float price){
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.image = image;
        this.price = price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
