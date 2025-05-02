package kg.devcats.coffee_shop.aspect;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.devcats.coffee_shop.exceptions.RateLimitExceededException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kg.devcats.coffee_shop.annotations.rate_limiting.RateLimiter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RateLimiterAspect {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimiter)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        String key = getClientIP();
        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket(rateLimiter));

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            throw new RateLimitExceededException("Too many requests!");
        }
    }

    private Bucket createBucket(RateLimiter rateLimiter) {

        Bandwidth limit = Bandwidth.classic(rateLimiter.upperBound(), Refill.intervally(rateLimiter.refillTokens(), Duration.ofSeconds(rateLimiter.duration())));
        return Bucket4j.builder().addLimit(limit).build();
    }

    private String getClientIP() {
        HttpServletRequest request = (
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()
        ).getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}

