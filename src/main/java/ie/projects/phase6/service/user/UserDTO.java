package ie.projects.phase6.service.user;

import java.util.ArrayList;

public class UserDTO {
    String id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    float credit;

    public UserDTO(String id, String firstName, String lastName, String phoneNumber, String email, float credit){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.credit = credit;
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
}
