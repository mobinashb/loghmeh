package ie.projects.phase6.service.user.response;

public class UserDTO {
    String firstName;
    String lastName;
    String email;
    float credit;

    public UserDTO(String firstName, String lastName, String email, float credit){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credit = credit;
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

    public float getCredit() {
        return credit;
    }
}
