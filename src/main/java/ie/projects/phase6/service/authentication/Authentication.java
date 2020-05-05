package ie.projects.phase6.service.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class Authentication {
    public static final long EXP_MILLIS = 2400000;
    public static final String SECRET = "loghme";

    public static String createToken(String email){
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + EXP_MILLIS;
        Date now = new Date(nowMillis);
        Date exp = new Date(expMillis);
        return JWT.create()
                .withIssuer("localhost")
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .withClaim("id", email)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static void verify(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("localhost").build();
        DecodedJWT jwt = verifier.verify(token);
        System.out.println(jwt.getHeader());
        System.out.println(jwt.getPayload());
        System.out.println(jwt.getSignature());
        System.out.println(jwt.getClaim("id").asString());
        System.out.println(jwt.getExpiresAt());
        System.out.println(jwt.getIssuedAt());
        System.out.println(jwt.getIssuer());
    }

    public static void main(String[] args) {
        String token = Authentication.createToken("omid.bodaghi79@gmail.com");
        System.out.println(token);
        verify(token);
    }
}
