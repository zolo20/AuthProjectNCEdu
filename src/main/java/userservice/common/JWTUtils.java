package userservice.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Clock;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.function.Function;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static userservice.common.Constants.SECRET;

public final class JWTUtils {

    private final static Clock clock = Date::new;

    public static String generateToken(String login) {
        final Date createDate = clock.getToday();
        return JWT.create()
                .withSubject(login)
                .withExpiresAt(new Date(createDate.getTime() + Constants.TOKEN_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public static String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public static Date getExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public static boolean isTokenExpired(String token) {
        return clock.getToday().after(getExpirationDate(token));
    }
}
