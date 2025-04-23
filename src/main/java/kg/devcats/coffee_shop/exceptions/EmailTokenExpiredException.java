package kg.devcats.coffee_shop.exceptions;

public class EmailTokenExpiredException extends RuntimeException {
  public EmailTokenExpiredException(String message) {
    super(message);
  }
}
