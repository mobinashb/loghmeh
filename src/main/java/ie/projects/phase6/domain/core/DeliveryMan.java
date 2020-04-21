package ie.projects.phase6.domain.core;

public class DeliveryMan {
    private String id;
    private double velocity;
    private GeoLocation location;
    private double timeToReach;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public GeoLocation getLocation() { return location; }

    public double getVelocity() { return velocity; }

    public double calcReceiveToUserTime(GeoLocation restaurantLocation, double restaurantToUserDistance){
        double restaurantToDeliveryManDistance = Math.sqrt(Math.pow(restaurantLocation.getx() - this.location.getx(), 2) + Math.pow(restaurantLocation.gety() - this.location.gety(), 2));
        double distance = restaurantToDeliveryManDistance + restaurantToUserDistance;
        return distance / this.velocity;
    }

    public double getTimeToReach() {
        return timeToReach;
    }

    public void setTimeToReach(double timeToReach) {
        this.timeToReach = timeToReach;
    }
}
