package kg.devcats.coffee_shop.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class CustomJwtHelper {
    @Value("${jwt.accessToken.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private long refreshTokenExpiration;

    private final UserDetailsService userDetailsService;

    public CustomJwtHelper( UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public  String createToken(String subject, Map<String, Object> claims, Long exp) {
        try{
            String header = initHeader();
            String payload = initPayload(subject, claims, exp);
            String signature = hmacSha256(header + "." + payload, "77f3b28fa56882973a9d0cd76bb47436b1593a41a1a437a37045a27037b");

            return header + "." + payload + "." + signature;
        } catch (Exception e) {
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

        if((Long) claims.get("exp") > System.currentTimeMillis()) {
            throw new RuntimeException("Expired JWT token");
        }

        return claims;
    }

    public Map<String, String> validateToken(String token) throws Exception {
        Map<String, Object> claims = verifyToken(token);
        String access_token = createToken(getUsernameFromToken(token), claims, accessTokenExpiration);
        String refresh_token = createToken(getUsernameFromToken(token), claims, refreshTokenExpiration);

        Map<String, String> res = new HashMap<>();
        res.put("access_token", access_token);
        res.put("Expires in : ", accessTokenExpiration / 60 / 60 + " hours");
        res.put("refresh_token", refresh_token);
        res.put("Expires in : ", refreshTokenExpiration / 60 / 60 / 24 + " days");
        return res;
    }

    public String getUsernameFromToken(String token) throws Exception {
        Map<String, Object> claims = verifyToken(token);
        return  (String) claims.get("sub");
    }

    private String initHeader() throws Exception{
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return encodeToString(header);
    }

    private String initPayload(String username, Map<String, Object> claims, Long exp) throws Exception{
        claims.put("sub", username);
        claims.put("exp", System.currentTimeMillis() / 1000 + exp);
        claims.put("iat", System.currentTimeMillis() / 1000);
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
