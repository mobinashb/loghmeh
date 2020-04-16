package ie.projects.phase6.repository.dao;

public class FoodpartyDAO {
    String restaurantId;
    String name;
    float count;
    float oldPrice;

    public FoodpartyDAO(String id, String name, float count, float oldPrice){
        this.restaurantId = id;
        this.name = name;
        this.count = count;
        this.oldPrice = oldPrice;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setId(String id) {
        this.restaurantId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCount() {
        return count;
    }

    public float getOldPrice() {
        return oldPrice;
    }
}
