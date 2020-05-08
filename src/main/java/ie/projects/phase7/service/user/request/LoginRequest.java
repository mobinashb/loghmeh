package ie.projects.phase7.service.user.request;

public class LoginRequest {
    String email;
    String password;
    boolean isGoogleAuth;
    String id_token;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsGoogleAuth() {
        return isGoogleAuth;
    }

    public String getId_token() {
        return id_token;
    }
}
