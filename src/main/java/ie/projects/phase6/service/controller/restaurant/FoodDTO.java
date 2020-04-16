package ie.projects.phase6.service.controller.restaurant;

public class FoodDTO {
    protected String name;
    protected String description;
    protected float popularity;
    protected String image;
    protected float price;

    public FoodDTO(String name, String description, float popularity, String image, float price){
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.image = image;
        this.price = price;
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
}
