//package ie.projects.phase6.domain.core;
//
//import com.fasterxml.jackson.annotation.JsonFilter;
//import ie.projects.phase1.exceptions.CartValidationException;
//import ie.projects.phase1.exceptions.FoodPartyExpiration;
//import ie.projects.phase1.exceptions.NegativeCreditAmount;
//import ie.projects.phase1.server.jsonCreator.JSONStringCreator;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//@JsonFilter("user")
//public class User {
//    private String id;
//    private String firstName;
//    private String lastName;
//    private String phoneNumber;
//    private String email;
//    private GeoLocation location;
//    private double credit;
//    private ArrayList<Cart> orders;
//    private ArrayList<Cart> undeliveredOrders;
//    private Cart cart;
//    private int cartId;
//
//    private static final String ORDERDONE = "just has been finalized";
//    private static final String DELIVERYMANFINDING = "finding delivery";
//    private static final String DELIVERYMANCOMING = "delivering";
//    private static final String ORDERDELIVERED = "delivered";
//
//    public User(String id, String firstName, String lastName, String phoneNumber, String email, double credit){
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.credit = credit;
//        this.orders = new ArrayList<Cart>();
//        this.undeliveredOrders = new ArrayList<Cart>();
//        this.cart = new Cart();
//        this.location = new GeoLocation(0, 0);
//        cartId = 0;
//    }
//
//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
//
//    public String getFirstName() { return firstName; }
//
//    public String getLastName() { return lastName; }
//
//    public String getPhoneNumber() { return phoneNumber; }
//
//    public String getEmail() { return email; }
//
//    public double getCredit() { return credit; }
//
//    public ArrayList<Cart> getAllOrders() {
//        ArrayList<Cart> temp = new ArrayList<>();
//        temp.addAll(orders);
//        temp.addAll(undeliveredOrders);
//        return temp;
//    }
//
//    public void addCredit(double amount) throws NegativeCreditAmount{
//        if(amount <= 0)
//            throw new NegativeCreditAmount(new JSONStringCreator().msgCreator("برای افزایش اعتبار مقدار مثبتی را وارد نمایید."));
//        credit += amount;
//    }
//
//    public Cart getCart() { return cart; }
//
//    public Cart findCartById(String cartId){
//        for(Cart cart : this.orders){
//            if(cart != null){
//                if(Integer.toString(cart.getId()).equals((cartId)))
//                    return cart;
//            }
//        }
//        for(Cart cart : this.undeliveredOrders){
//            if(cart != null){
//                if(Integer.toString(cart.getId()).equals((cartId)))
//                    return cart;
//            }
//        }
//        return null;
//    }
//
//    public void checkPartyExpiration(String restaurantId) throws FoodPartyExpiration{
//        if(cart.getRestaurantId() == null)
//            return;
//        if(cart.getRestaurantId().equals(restaurantId)){
//            cart.clearOrders();
//            throw new FoodPartyExpiration(new JSONStringCreator().msgCreator("زمان جشن غذا برای غذی انتخاب‌شده به اتمام رسیده‌است. جشن غذاهای افزوده‌شده به سبد خرید پاک می‌شوند."));
//        }
//    }
//
//    public void addToCart(String foodName, int number, double price, String restaurantId, String restaurantName, Boolean isParty, boolean isNew) throws CartValidationException {
//        String cartResId = cart.getRestaurantId();
//        if((cartResId == null) || (cartResId.equals(restaurantId))) {
//            cart.addToCart(foodName, number, price, restaurantId, restaurantName, isParty, isNew);
//            return;
//        }
//        throw new CartValidationException(new JSONStringCreator().msgCreator("امکان ثبت سفارش از دو رستوران مجزا در یک سبد خرید وجود ندارد."));
//    }
//
//    public void deleteCart(){
//        cart.clearOrders();
//    }
//
//    private boolean validateCart() throws CartValidationException {
//        Restaurant restaurant;
//        if(cart.getRestaurantId() == null)
//            throw new CartValidationException(new JSONStringCreator().msgCreator("برای سبدخرید شما، رستورانی ثبت نشده‌است."));
//
//        restaurant = Loghmeh.getInstance().findRestaurantInPartyById(cart.getRestaurantId());
//        if(restaurant != null){
//            if (!restaurant.checkPartyFoodNum(cart.getPartyOrders()))
//                throw new CartValidationException(new JSONStringCreator().msgCreator("غذا از جشن غذا برداشته شده‌است."));
//        }
//        return true;
//    }
//
//    private double checkUserCredit(){
//        double totalPrice = 0;
//
//        for (Order order: cart.getPartyOrders())
//            totalPrice += order.getPrice() * order.getFoodNum();
//        for (Order order: cart.getOrders())
//            totalPrice += order.getPrice() * order.getFoodNum();
//
//        if(totalPrice > this.credit)
//            totalPrice = 0.0;
//        return totalPrice;
//    }
//
//    private void doOrder(double totalPrice){
//        credit = credit - totalPrice;
//        if(cart.getRestaurantId() != null)
//            cart.setId(this.cartId);
//        this.cartId++;
//        cart.setOrderStatus(ORDERDONE);
//    }
//
//    public void finalizeOrder() throws CartValidationException, FoodPartyExpiration{
//        Restaurant partyRestaurant = Loghmeh.getInstance().findRestaurantInPartyById(cart.getRestaurantId());
//        if(cart.getPartyOrders().size() != 0){
//            if(partyRestaurant == null){
//                cart.clearOrders();
//                throw new FoodPartyExpiration(new JSONStringCreator().msgCreator("زمان جشن غذا برای غذی انتخاب‌شده به اتمام رسیده‌است. جشن غذاهای افزوده‌شده به سبد خرید پاک می‌شوند."));
//            }
//        }
//        if (cart.getOrderStatus() != null)
//            return;
//        if (validateCart() == false)
//            return;
//        double price = checkUserCredit();
//        if (price > 0) {
//            doOrder(price);
//            if (cart.getPartyOrders().size() != 0)
//                partyRestaurant.setPartyFoodNum(cart.getPartyOrders());
//            Cart newCart = new Cart(cart.getId(), new ArrayList<Order>(cart.getOrders()), new ArrayList<Order>(cart.getPartyOrders()), cart.getRestaurantId(), cart.getRestaurantName(),
//                    cart.getDeliveryManId(), DELIVERYMANFINDING, cart.getDeliveryManFoundedTime(), cart.getDeliveryManTimeToReach());
//
//            this.undeliveredOrders.add(newCart);
//            cart.clearOrders();
//        }
//        else
//            throw new CartValidationException(new JSONStringCreator().msgCreator("موجودی برای نهایی کردن سفارش کافی نمی‌باشد."));
//
//    }
//
//    public void checkStates(){
//        Iterator<Cart> cartItr = this.undeliveredOrders.iterator();
//        while (cartItr.hasNext()) {
//            Cart cart = cartItr.next();
//            orderState(cart, cartItr);
//        }
//    }
//
//    private void orderState(Cart cart, Iterator cartItr){
//        if(cart.getOrderStatus() == DELIVERYMANFINDING){
//            Loghmeh loghmeh = Loghmeh.getInstance();
//            if(!loghmeh.addAllToLoghmeh("http://138.197.181.131:8080/deliveries", "deliveryMan"))
//                return;
//            else {
//                Restaurant restaurant = loghmeh.findRestaurantById(cart.getRestaurantId());
//                DeliveryMan deliveryMan = loghmeh.selectBestDeliveryMan(restaurant, location);
//
//                if(deliveryMan == null) {
//                    System.out.println("There isn't any delivery service available now");
//                    return;
//                }
//
//                cart.setOrderStatus(DELIVERYMANCOMING);
//
//                cart.setDeliveryManId(deliveryMan.getId());
//                cart.setDeliveryManTimeToReach(deliveryMan.calcReceiveToUserTime(restaurant.getLocation(), restaurant.getDistance(location.getx(), location.gety())));
//                cart.setDeliveryManFoundedTime(System.currentTimeMillis());
//                return;
//            }
//        }
//        else if(cart.getOrderStatus() == DELIVERYMANCOMING) {
//            cart.setRemainingTimeToDeliver(cart.getDeliveryManTimeToReach() - ((System.currentTimeMillis() - cart.getDeliveryManFoundedTime()) / 1000));
//            if ((System.currentTimeMillis() - cart.getDeliveryManFoundedTime()) > (cart.getDeliveryManTimeToReach() * 1000))
//                cart.setOrderStatus(ORDERDELIVERED);
//        }
//        else if(cart.getOrderStatus() == ORDERDELIVERED) {
//            Cart newCart = new Cart(cart.getId(), cart.getOrders(), cart.getPartyOrders(), cart.getRestaurantId(), cart.getRestaurantName(), cart.getDeliveryManId(), cart.getOrderStatus(), cart.getDeliveryManFoundedTime(), cart.getDeliveryManTimeToReach());
//            orders.add(newCart);
//            cartItr.remove();
//        }
//        return;
//    }
//}
