package ie.projects.phase7.repository.user;

public class UserDAO {
    String firstName;
    String lastName;
    String email;
    String password;
    float credit;

    public UserDAO(String firstName, String lastName, String email, String password, float credit){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
