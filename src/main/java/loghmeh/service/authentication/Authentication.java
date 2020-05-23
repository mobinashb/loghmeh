package loghmeh.service.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import loghmeh.configs.AuthenticationConfig;

import java.util.Date;

public class Authentication {
    public static final long EXP_MILLIS = AuthenticationConfig.JWT_EXP_MILLIS;
    public static final String SECRET = AuthenticationConfig.SECRET;

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

    public static String verify(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("localhost").build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("id").asString();
    }
}
