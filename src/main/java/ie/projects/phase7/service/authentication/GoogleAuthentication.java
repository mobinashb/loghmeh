package ie.projects.phase7.service.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import ie.projects.phase7.configs.GoogleAuthenticationConfig;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleAuthentication {
    public static String verifyGoogleToken(String token) {

        try {
            String clientId = GoogleAuthenticationConfig.GOOGLE_CLIENT_ID;

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList(clientId)).build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken == null) {
                return null;
            }
            GoogleIdToken.Payload payload = idToken.getPayload();
            if(!Boolean.valueOf(payload.getEmailVerified())){
                return null;
            }

            return payload.getEmail();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (GeneralSecurityException e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
