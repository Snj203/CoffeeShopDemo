package kg.devcats.coffee_shop.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.devcats.coffee_shop.entity.h2.User;
import kg.devcats.coffee_shop.repository.h2.UserRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomJwtHelper {
    private final UserRepositoryJPA userRepositoryJPA;
    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpiration;

    private final Logger log = LoggerFactory.getLogger(CustomJwtHelper.class);

    public CustomJwtHelper(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    public  String createToken(String subject, Map<String, Object> claims, Long exp) {
        try{
            String header = initHeader();
            String payload = initPayload(subject, claims, exp);
            String signature = hmacSha256(header + "." + payload, "77f3b28fa56882973a9d0cd76bb47436b1593a41a1a437a37045a27037b");

            return header + "." + payload + "." + signature;
        } catch (Exception e) {
            log.error("Error while creating token: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public  Map<String, Object> verifyToken(String token) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT format");
        }

        String jwtHeader = parts[0];
        String jwtPayload = parts[1];
        String receivedSignature = parts[2];

        String expectedSignature = hmacSha256(jwtHeader + "." + jwtPayload, "77f3b28fa56882973a9d0cd76bb47436b1593a41a1a437a37045a27037b");
        if (!expectedSignature.equals(receivedSignature)) {
            throw new SecurityException("Invalid token signature");
        }

        String payloadJson = new String(Base64.getUrlDecoder().decode(jwtPayload), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> claims = objectMapper.readValue(payloadJson, Map.class);

        long exp = ((Number) claims.get("exp")).longValue();

        if(exp < System.currentTimeMillis() / 1000) {
            throw new RuntimeException("Expired JWT token");
        }

        User user = userRepositoryJPA.findById((String)claims.get("sub")).get();
        if(!user.getEmailVerified()){
            throw new RuntimeException("User email is not verified");
        }

        return claims;
    }

    public Map<String, String> validateToken(String token) throws Exception {
        Map<String, Object> claims = verifyToken(token);

        User user = userRepositoryJPA.findById((String)claims.get("sub")).get();
        if(!user.getRefreshToken().equals(token)) {
            throw new SecurityException("Invalid JWT token");
        }

        String access_token = createToken((String) claims.get("sub"), claims, accessTokenExpiration);
        String refresh_token = createToken((String) claims.get("sub"), claims, refreshTokenExpiration);

        user.setRefreshToken(refresh_token);
        userRepositoryJPA.save(user);

        Map<String, String> res = new HashMap<>();
        res.put("access_token", access_token);
        res.put("Access token Expires in : ", accessTokenExpiration / 60L / 60L + " hours");
        res.put("refresh_token", refresh_token);
        res.put("Refresh Token Expires in : ", refreshTokenExpiration / 60L / 60L / 24L + " days");
        return res;
    }

    private String initHeader() throws Exception{
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return encodeToString(header);
    }

    private String initPayload(String username, Map<String, Object> claims, Long exp) throws Exception{
        claims.put("sub", username);
        claims.put("exp", System.currentTimeMillis() / 1000L + exp);
        claims.put("iat", System.currentTimeMillis() / 1000L);
        return encodeToString(claims);
    }

    private String encodeToString(Map<String, Object> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String myJson = objectMapper.writeValueAsString(map);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(myJson.getBytes(StandardCharsets.UTF_8));
    }

    private String hmacSha256(String data, String secret) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

}
