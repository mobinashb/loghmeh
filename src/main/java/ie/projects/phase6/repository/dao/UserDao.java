package ie.projects.phase6.repository.dao;

public class UserDao {
    String id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    float credit;
    float locationX;
    float locationY;

    public UserDao(String id, String firstName, String lastName, String phoneNumber, String email, float credit, float locationX, float locationY){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.credit = credit;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getCredit() {
        return credit;
    }

    public float getLocationX() {
        return locationX;
    }

    public float getLocationY() {
        return locationY;
    }
}
