package kg.devcats.coffee_shop.service;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String content);
    Boolean verifyToken(String token);
}
