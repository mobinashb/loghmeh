package ie.projects.phase6.repository.restaurant;

public class RestaurantDAO {
    String id;
    String name;
    String logo;
    float locationX;
    float locationY;

    public RestaurantDAO(String id, String name, String logo, float locationX, float locationY){
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }
}
