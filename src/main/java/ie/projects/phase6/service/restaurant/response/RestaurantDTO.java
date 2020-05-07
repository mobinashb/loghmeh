package ie.projects.phase6.service.restaurant.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import ie.projects.phase6.service.restaurant.response.FoodDTO;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDTO {
    private String id;
    private String name;
    private String logo;

    ArrayList<FoodDTO> menu;

    public RestaurantDTO(String id, String name, String logo, ArrayList<FoodDTO> menu){
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public ArrayList<FoodDTO> getMenu() {
        return menu;
    }
}
