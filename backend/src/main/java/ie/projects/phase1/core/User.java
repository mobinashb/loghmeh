package ie.projects.phase1.core;

import ie.projects.phase1.exceptions.DifferentRestaurantsForCart;
import ie.projects.phase1.exceptions.NegativeCreditAmount;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private GeoLocation location;
    private double credit;
    private ArrayList<Cart> orders;
    private ArrayList<Cart> undeliveredOrders;
    private Cart cart;
    private int orderId;

    private static final String ORDERDONE = "just has been finalized";
    private static final String DELIVERYMANFINDING = "finding delivery";
    private static final String DELIVERYMANCOMING = "delivering";
    private static final String ORDERDELIVERED = "delivered";

    public User(String id, String firstName, String lastName, String phoneNumber, String email, double credit){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.credit = credit;
        this.orders = new ArrayList<Cart>();
        this.undeliveredOrders = new ArrayList<Cart>();
        this.cart = new Cart();
        this.location = new GeoLocation(0, 0);
        orderId = 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getEmail() { return email; }

    public double getCredit() { return credit; }

    public ArrayList<Cart> getAllOrders() {
        ArrayList<Cart> temp = new ArrayList<>();
        temp.addAll(orders);
        temp.addAll(undeliveredOrders);
        return temp;
    }

    public void addCredit(double amount) throws NegativeCreditAmount{
        if(amount <= 0)
            throw new NegativeCreditAmount("Amount of add credit should be positive");
        credit += amount;
    }

    public Cart getCart() { return cart; }

    public Cart findOrderById(String cartId){
        for(Cart cart : this.orders){
            if(cart != null){
                if(Integer.toString(cart.getId()).equals((cartId)))
                    return cart;
            }
        }
        for(Cart cart : this.undeliveredOrders){
            if(cart != null){
                if(Integer.toString(cart.getId()).equals((cartId)))
                    return cart;
            }
        }
        return null;
    }

    public void addToCart(String foodName, int number, String restaurantId, Boolean isParty) throws DifferentRestaurantsForCart {
        String cartResId = cart.getRestaurantId();
        if((cartResId == null) || (cartResId.equals(restaurantId))) {
            cart.addNewOrder(foodName, number, restaurantId, isParty);
            return;
        }

        throw new DifferentRestaurantsForCart("{\"msg\": " + "\"You have ordered from another restaurant first" + "\"}");
    }

    private String validateCart(){
        Restaurant restaurant;
        if(cart.getRestaurantId() == null)
            return "Your cart's restaurant name isn't registered";

        restaurant = Loghmeh.getInstance().findRestaurantInPartyById(cart.getRestaurantId());
        if(restaurant != null){
            if (!restaurant.checkPartyFoodNum(cart.getPartyOrders()))
                return "This food isn't in party anymore";
        }
        return "";
    }

    private double checkUserCredit(){
        double totalPrice = 0;
        Loghmeh loghmeh = Loghmeh.getInstance();

        if(cart.getPartyOrders().isEmpty() == false)
            totalPrice = loghmeh.findRestaurantInPartyById(cart.getRestaurantId()).getFoodPrices(cart.getPartyOrders());

        if(cart.getOrders().isEmpty() == false)
            totalPrice += loghmeh.findRestaurantById(cart.getRestaurantId()).getFoodPrices(cart.getOrders());

        if(totalPrice > this.credit)
            totalPrice = 0.0;
        return totalPrice;
    }

    private void doOrder(double totalPrice){
        credit = credit - totalPrice;
        if(cart.getRestaurantId() != null)
            cart.setId(this.orderId);
        this.orderId++;
        cart.setOrderStatus(ORDERDONE);
    }

    public String finalizeOrder() {
        if (cart.getOrderStatus() == null) {
            String cartValidation = validateCart();
            if (cartValidation == "") {
                double price = checkUserCredit();
                if (price > 0) {
                    doOrder(price);
                    if (cart.getPartyOrders().isEmpty() == false){
                        Restaurant restaurant = Loghmeh.getInstance().findRestaurantInPartyById(cart.getRestaurantId());
                        restaurant.setPartyFoodNum(cart.getPartyOrders());
                    }
                    cart.setOrderStatus(DELIVERYMANFINDING);

                    Cart newCart = new Cart(cart.getId(), cart.getOrders(), cart.getPartyOrders(), cart.getRestaurantId(), cart.getDeliveryManId(), cart.getOrderStatus(), cart.getDeliveryManFoundedTime(), cart.getDeliveryManTimeToReach());
                    this.undeliveredOrders.add(newCart);
                    cart.clearOrders();
                    return "";
                }
                else
                    return "You don't have enough credit";
            }
            else
                return cartValidation;
        }
        return "There isn't order to finalize";
    }

    public void checkStates(){
        Iterator<Cart> cartItr = this.undeliveredOrders.iterator();
        while (cartItr.hasNext()) {
            Cart cart = cartItr.next();
            orderState(cart, cartItr);
        }
    }

    private boolean orderState(Cart cart, Iterator cartItr){
        if(cart.getOrderStatus() == DELIVERYMANFINDING){
            Loghmeh loghmeh = Loghmeh.getInstance();
            if(!loghmeh.addAllToLoghmeh("http://138.197.181.131:8080/deliveries", "deliveryMan"))
                return false;
            else {
                Restaurant restaurant = loghmeh.findRestaurantById(cart.getRestaurantId());
                DeliveryMan deliveryMan = loghmeh.selectBestDeliveryMan(restaurant, location);

                if(deliveryMan == null) {
                    System.out.println("There isn't any delivery service available now");
                    return false;
                }

                cart.setOrderStatus(DELIVERYMANCOMING);

                cart.setDeliveryManId(deliveryMan.getId());
                cart.setDeliveryManTimeToReach(deliveryMan.calcReceiveToUserTime(restaurant.getLocation(), restaurant.getDistance(location.getx(), location.gety())));
                cart.setDeliveryManFoundedTime(System.currentTimeMillis());
                return true;
            }
        }
        else if(cart.getOrderStatus() == DELIVERYMANCOMING) {
            cart.setRemainingTimeToDeliver(cart.getDeliveryManTimeToReach() - ((System.currentTimeMillis() - cart.getDeliveryManFoundedTime()) / 1000));
            if ((System.currentTimeMillis() - cart.getDeliveryManFoundedTime()) > (cart.getDeliveryManTimeToReach() * 1000))
                cart.setOrderStatus(ORDERDELIVERED);
        }
        else if(cart.getOrderStatus() == ORDERDELIVERED) {
            Cart newCart = new Cart(cart.getId(), cart.getOrders(), cart.getPartyOrders(), cart.getRestaurantId(), cart.getDeliveryManId(), cart.getOrderStatus(), cart.getDeliveryManFoundedTime(), cart.getDeliveryManTimeToReach());
            orders.add(newCart);
            cartItr.remove();
        }
        return false;
    }
}
