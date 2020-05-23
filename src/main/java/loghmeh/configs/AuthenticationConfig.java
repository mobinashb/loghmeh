package loghmeh.configs;

public class AuthenticationConfig {
    public static final String SECRET = "loghmeh";
    public static final long JWT_EXP_MILLIS = 3600000;
    public static final String AUTHENTICATION_HEADER_KEY = "Authorization";
    public static final String AUTHENTICATION_PREFIX_KEY = "Bearer ";
}
