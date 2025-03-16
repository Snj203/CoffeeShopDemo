package kg.devcats.coffee_shop.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CustomJwtHelper {


    public CustomJwtHelper() {
    }

    public static String createToken(String subject, Map<String, Object> claims) {
        try{
            String header = initHeader();
            String payload = initPayload(subject, claims);
            String signature = hmacSha256(header + "." + payload, "77f3b28fa56882973a9d0cd76bb47436b1593a41a1a437a37045a27037b");

            return header + "." + payload + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> verifyToken(String token) throws Exception {
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
        return objectMapper.readValue(payloadJson, Map.class);
    }

    private static String initHeader() throws Exception{
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return encodeToString(header);
    }

    private static String initPayload(String username, Map<String, Object> claims) throws Exception{
        claims.put("sub", username);
        claims.put("exp", System.currentTimeMillis() / 1000 + 3600);
        claims.put("iat", System.currentTimeMillis() / 1000);
        return encodeToString(claims);
    }

    private static String encodeToString(Map<String, Object> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String myJson = objectMapper.writeValueAsString(map);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(myJson.getBytes(StandardCharsets.UTF_8));
    }

    private static String hmacSha256(String data, String secret) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

}
