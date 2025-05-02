package kg.devcats.coffee_shop.annotations.rate_limiting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {
    int upperBound() default 10;
    int refillTokens() default 10;
    long duration() default 60;
}