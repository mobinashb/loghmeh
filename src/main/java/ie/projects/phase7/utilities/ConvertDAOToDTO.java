package ie.projects.phase7.utilities;

import ie.projects.phase7.domain.RestaurantManager;
import ie.projects.phase7.repository.cart.CartDAO;
import ie.projects.phase7.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase7.repository.food.FoodDAO;
import ie.projects.phase7.repository.order.OrderDAO;
import ie.projects.phase7.repository.restaurant.RestaurantDAO;
import ie.projects.phase7.repository.user.UserDAO;
import ie.projects.phase7.service.cart.response.CartDTO;
import ie.projects.phase7.service.cart.response.FinalizedCartsDTO;
import ie.projects.phase7.service.cart.response.OrderDTO;
import ie.projects.phase7.service.restaurant.response.FoodDTO;
import ie.projects.phase7.service.restaurant.response.RestaurantDTO;
import ie.projects.phase7.service.user.response.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ConvertDAOToDTO {

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

    public static ArrayList<String> getRestaurantsNameForFoods(ArrayList<FoodDAO> foods) throws SQLException{
        ArrayList<String> restaurantsId = new ArrayList<>();
        for(FoodDAO foodDAO: foods)
            restaurantsId.add(foodDAO.getRestaurantId());

        return RestaurantManager.getInstance().getRestaurantsName(restaurantsId);
    }

    public static ArrayList<FoodDTO> foodpartyDAO_DTO(ArrayList<FoodDAO> foodList) throws SQLException {
        ArrayList<String> restaurantsName = getRestaurantsNameForFoods(foodList);
        ArrayList<FoodDTO> foodListDTO = new ArrayList<>();
        int i = 0;
        for(FoodDAO foodDAO: foodList) {
            foodListDTO.add(new FoodDTO(foodDAO.getName(), foodDAO.getDescription(), foodDAO.getPopularity(), foodDAO.getImage(),
                    foodDAO.getPrice(), foodDAO.getRestaurantId(), restaurantsName.get(i), foodDAO.getCount(), foodDAO.getOldPrice()));
            i += 1;
        }
        return foodListDTO;
    }

    public static UserDTO userDAO_DTO(UserDAO userDao){
        return new UserDTO(userDao.getFirstName(), userDao.getLastName(), userDao.getEmail(), userDao.getCredit());
    }

    public static CartDTO cartDAO_DTO(CartDAO cart, ArrayList<OrderDAO> orders) throws SQLException{
        if(cart == null)
            return new CartDTO(null, null, null, null);
        ArrayList<OrderDTO> resultOrders = new ArrayList<>();
        for(OrderDAO order : orders)
            resultOrders.add(new OrderDTO(order.getFoodName(), order.getFoodNum(), order.getPrice(), order.getIsParty()));
        return new CartDTO(cart.getCartId(), cart.getRestaurantId(), RestaurantManager.getInstance().getRestaurantsName(new ArrayList<String>(Arrays.asList(cart.getRestaurantId()))).get(0), resultOrders);
    }

    public static ArrayList<FinalizedCartsDTO> finalizedCartsDAO_DTO(ArrayList<FinalizedCartDAO> orders) throws SQLException{

        ArrayList<String> restaurantsId = new ArrayList<>();
        for(FinalizedCartDAO cart: orders)
            restaurantsId.add(cart.getRestaurantId());

        ArrayList<String> restaurantsName = RestaurantManager.getInstance().getRestaurantsName(restaurantsId);

        ArrayList<FinalizedCartsDTO> result = new ArrayList<>();
        for(int i=0; i<orders.size(); i++)
            result.add(new FinalizedCartsDTO(orders.get(i).getCartId(), orders.get(i).getRestaurantId(), restaurantsName.get(i), orders.get(i).getOrderStatus()));
        return result;
    }
}
