package ie.projects.phase1;

import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ie.projects.phase1.core.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AppTest
{
    private static Loghmeh loghmeh;

    @BeforeClass
    public static void setup() throws IOException
    {
        loghmeh = Loghmeh.getInstance();
        String restaurant0 = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestData0.json")));
        String restaurant1 = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestData1.json")));
        String restaurant2 = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestData2.json")));
        String restaurant3 = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestData3.json")));
        String restaurant4 = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestData4.json")));
        loghmeh.addRestaurant(restaurant0);
        loghmeh.addRestaurant(restaurant1);
        loghmeh.addRestaurant(restaurant2);
        loghmeh.addRestaurant(restaurant3);
        loghmeh.addRestaurant(restaurant4);

        String partyRestaurant0 = new String(Files.readAllBytes(Paths.get("src/test/resources/partyRestaurant0.json")));
        String partyRestaurant1 = new String(Files.readAllBytes(Paths.get("src/test/resources/partyRestaurant1.json")));
        String partyRestaurant2 = new String(Files.readAllBytes(Paths.get("src/test/resources/partyRestaurant2.json")));

        loghmeh.addToParty(partyRestaurant0);
        loghmeh.addToParty(partyRestaurant1);
        loghmeh.addToParty(partyRestaurant2);

        String food0 = new String(Files.readAllBytes(Paths.get("src/test/resources/foodTestData0.json")));
        loghmeh.addFood(food0);
        String food1 = new String(Files.readAllBytes(Paths.get("src/test/resources/foodTestData1.json")));
        loghmeh.addFood(food1);
        String food2 = new String(Files.readAllBytes(Paths.get("src/test/resources/foodTestData2.json")));
        loghmeh.addFood(food2);
    }

    @Test
    public void testAddRestaurant()
    {
        ArrayList<Restaurant> restaurants = loghmeh.getRestaurants();
        assertEquals(restaurants.get(0).getName(), "Asatid");
        assertEquals(restaurants.get(1).getName(), "Sib360");
        assertEquals(restaurants.get(0).getMenu().get(1).getName(), "ZereshkPoloBaMorgh");
        assertEquals(restaurants.get(1).getMenu().get(0).getName(), "Pizza");
        assertEquals(restaurants.get(1).getMenu().size(), 4);
        assertEquals((int) restaurants.get(1).getMenu().get(3).getPrice(), 10000);
    }

    @Test
    public void testAddRestaurantDup() throws IOException
    {
        String restaurantDup = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestData4.json")));
        ArrayList<Restaurant> before = loghmeh.getRestaurants();
        loghmeh.addRestaurant(restaurantDup);
        ArrayList<Restaurant> after = loghmeh.getRestaurants();
        assertEquals(after, before);
    }

    @Test
    public void testAddRestaurantWithDupFood() throws IOException
    {
        String restaurantWrong = new String(Files.readAllBytes(Paths.get("src/test/resources/restaurantTestDataWrong0.json")));
        loghmeh.addRestaurant(restaurantWrong);
        int siz = loghmeh.getRestaurants().get(7).getMenu().size();
        assertEquals(siz, 1);
        assertEquals((int)(loghmeh.getRestaurants().get(7).getMenu().get(0).getPrice()), 130000);
    }


    @Test
    public void testAddFood()
    {
        ArrayList<Restaurant> restaurants = loghmeh.getRestaurants();
        assertEquals(restaurants.get(0).getMenu().get(3).getName(), "Soup");
        assertEquals(restaurants.get(4).getMenu().get(3).getName(), "Cheesecake");
        assertEquals(restaurants.get(3).getMenu().get(2).getName(), "FrenchFries");
    }

    @Test
    public void testAddFoodDup() throws IOException
    {
        String foodDup = new String(Files.readAllBytes(Paths.get("src/test/resources/foodTestData2.json")));
        ArrayList<Food> before = loghmeh.getRestaurants().get(3).getMenu();
        loghmeh.addFood(foodDup);
        ArrayList<Food> after = loghmeh.getRestaurants().get(3).getMenu();
        assertEquals(before, after);
    }

    @Test
    public void testAddFoodWrong() throws IOException
    {
        String foodWrong = new String(Files.readAllBytes(Paths.get("src/test/resources/foodTestDataWrong.json")));
        ArrayList<Restaurant> before = loghmeh.getRestaurants();
        loghmeh.addFood(foodWrong);
        ArrayList<Restaurant> after = loghmeh.getRestaurants();
        assertEquals(before, after);
    }

//    @Test
//    public void testAddToCart() throws IOException
//    {
//        if (loghmeh.getCart().getOrders().isEmpty()) {
//            loghmeh.addToCart("Cheeseburger", "1");
//            loghmeh.addToCart("gharch", "6");
//            assertEquals(loghmeh.getCart().getRestaurantId(), null);
//            assertEquals(loghmeh.getPartyCart().getRestaurantId(), "1");
//            loghmeh.addToCart("Cheeseburger", "1");
//            loghmeh.addToCart("Pizza", "1");
//
//            assertEquals(loghmeh.getCart().getOrders().size(), 0);
//            assertEquals(loghmeh.getPartyCart().getOrders().size(), 1);
//
//            loghmeh.addToCart("Gharch", "1");
//            assertEquals(loghmeh.getPartyCart().getOrders().size(), 2);
//        }
//    }


    @Test
    public void testFinalizeOrderInsufficientCredit()
    {
        User user = loghmeh.getUsers().get(0);
        Cart cartBefore = loghmeh.getCart();
        user.addCredit(-80000);
        double creditBefore = user.getCredit();
        loghmeh.finalizeOrder();
        double creditAfter = user.getCredit();
        assertEquals((int)(creditBefore), (int)(creditAfter));

    }

    @Test
    public void testFinalizeOrderSufficientCredit()
    {
        if (loghmeh.getCart().getOrders().isEmpty()) {
            loghmeh.addToCart("Cheeseburger", "1");
            loghmeh.addToCart("gharch", "6");
            assertEquals(loghmeh.getCart().getRestaurantId(), "1");
            loghmeh.addToCart("Cheeseburger", "1");
            loghmeh.addToCart("Pizza", "1");

            assertEquals(loghmeh.getCart().getOrders().size(), 0);
            assertEquals(loghmeh.getCart().getPartyOrders().size(), 1);

            loghmeh.addToCart("Gharch", "1");
            assertEquals(loghmeh.getCart().getPartyOrders().size(), 2);
            loghmeh.addToCart("Morgh", "1");
            assertEquals(loghmeh.getCart().getOrders().size(), 1);
        }

        Cart cart = loghmeh.getCart();
        User user = loghmeh.getUsers().get(0);
        user.addCredit(100000);

        System.out.println(loghmeh.getRestaurantsInParty().get(2).getMenu().get(2).getPrice());

        double creditBefore = user.getCredit();
        assertEquals(loghmeh.finalizeOrder(), "Your order has been submitted successfully");
        double creditAfter = user.getCredit();
        assertEquals((int)(creditBefore - 47000), (int)(creditAfter));
        assertEquals(cart.getId(), 0);
        loghmeh.finalizeOrder();

    }
//
//
//    @Test
//    public void testRecommended()
//    {
//        Utils util = new Utils();
//        ArrayList<String> topRestaurants = util.top3InList(loghmeh.getRestaurants(), 0, 0);
//        assertEquals(topRestaurants.get(0), "Asatid");
//        assertEquals(topRestaurants.get(1), "H&H");
//        assertEquals(topRestaurants.get(2), "Sib360");
//    }
//
//    @Test
//    public void testGetRestaurantsInArea()
//    {
//        ArrayList<Restaurant> restaurantsInArea = loghmeh.getRestaurantsInArea(20);
//        ArrayList<String> resNames = new ArrayList<>();
//        for (Restaurant res : restaurantsInArea)
//            resNames.add(res.getName());
//        assertTrue(resNames.contains("Asatid"));
//        assertFalse(resNames.contains("Lamiz"));
//        assertFalse(resNames.contains("Sib360"));
//        assertTrue(resNames.contains("Sabalan"));
//        assertTrue(resNames.contains("H&H"));
//    }
//
//    @AfterClass
//    public static void tearDown()
//    {
//        loghmeh = null;
//        assertNull(loghmeh);
//    }
}
