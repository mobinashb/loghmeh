package ie.projects.phase7.domain.foreignServiceObjects;

public class DeliveryMan {
    private String id;
    private double velocity;
    private GeoLocation location;
    private double timeToReach;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public GeoLocation getLocation() { return location; }

    public double getVelocity() { return velocity; }

    public double getTimeToReach() {
        return timeToReach;
    }

    public void setTimeToReach(double timeToReach) {
        this.timeToReach = timeToReach;
    }
}
