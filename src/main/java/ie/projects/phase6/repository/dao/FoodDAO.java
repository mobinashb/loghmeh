package ie.projects.phase6.repository.dao;

public class FoodDAO {
    String restaurantId;
    String name;
    String description;
    float popularity;
    String image;
    float price;

    public FoodDAO(String restaurantId, String nam, String description, float popularity, String image, float price){
        this.restaurantId = restaurantId;
        this.name = nam;
        this.description = description;
        this.popularity = popularity;
        this.image = image;
        this.price = price;
    }
}
