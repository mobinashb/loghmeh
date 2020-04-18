package ie.projects.phase6.utilities;

import ie.projects.phase6.domain.RestaurantManager;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.restaurant.RestaurantDAO;
import ie.projects.phase6.repository.user.UserDAO;
import ie.projects.phase6.service.restaurant.FoodDTO;
import ie.projects.phase6.service.restaurant.RestaurantDTO;
import ie.projects.phase6.service.user.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_DTO {

    public static ArrayList<RestaurantDTO> restaurantDAO_DTO(ArrayList<RestaurantDAO> restaurants){
        ArrayList<RestaurantDTO> result = new ArrayList<>();
        for(RestaurantDAO restaurantDAO: restaurants)
            result.add(new RestaurantDTO(restaurantDAO.getId(), restaurantDAO.getName(), restaurantDAO.getLogo(), null));
        return result;
    }

    public static RestaurantDTO singleRestaurantDAO_DTO(RestaurantDAO restaurant, ArrayList<FoodDAO> foods){
        ArrayList<FoodDTO> foodsDTO = new ArrayList<>();
        for(FoodDAO foodDAO: foods)
            foodsDTO.add(new FoodDTO(foodDAO.getName(), foodDAO.getDescription(), foodDAO.getPopularity(), foodDAO.getImage(), foodDAO.getPrice()));

        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getLogo(), foodsDTO);
    }

    public static ArrayList<String> getRestaurantsName(ArrayList<FoodDAO> foods) throws SQLException{
        ArrayList<String> restaurantsId = new ArrayList<>();
        for(FoodDAO foodDAO: foods)
            restaurantsId.add(foodDAO.getRestaurantId());

        return RestaurantManager.getInstance().getRestaurantsName(restaurantsId);
    }

    public static ArrayList<FoodDTO> foodpartyDAO_DTO(ArrayList<FoodDAO> foods) throws SQLException {
        ArrayList<String> restaurantsName = getRestaurantsName(foods);
        ArrayList<FoodDTO> foodsDTO = new ArrayList<>();
        int i = 0;
        for(FoodDAO foodDAO: foods) {
            foodsDTO.add(new FoodDTO(foodDAO.getName(), foodDAO.getDescription(), foodDAO.getPopularity(), foodDAO.getImage(),
                    foodDAO.getPrice(), foodDAO.getRestaurantId(), restaurantsName.get(i), foodDAO.getCount(), foodDAO.getOldPrice()));
            i += 1;
        }

        return foodsDTO;
    }

    public static UserDTO userDAO_DTO(UserDAO userDao){
        return new UserDTO(userDao.getId(), userDao.getFirstName(), userDao.getLastName(), userDao.getPhoneNumber(), userDao.getEmail(), userDao.getCredit());
    }
}
