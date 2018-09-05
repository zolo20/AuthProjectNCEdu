package userservice.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Clock;
import org.springframework.security.core.userdetails.User;
import userservice.model.Role;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static userservice.common.Constants.SECRET;

public final class JWTUtils {

    private final static Clock clock = Date::new;

    public static String generateToken(Long id, String login, String name, String surname, Role role) {
        final Date createDate = clock.getToday();
        return JWT.create()
                .withAudience(
                        id == null ? null : id.toString(),
                        login,
                        name,
                        surname,
                        role == null ? null : role.toString()
                )
                .withExpiresAt(new Date(createDate.getTime() + Constants.TOKEN_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public static List<String> getAudience(String token) {
        return JWT.decode(token).getAudience();
    }

    public static Date getExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public static boolean isTokenExpired(String token) {
        return clock.getToday().after(getExpirationDate(token));
    }
}
