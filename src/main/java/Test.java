import com.auth0.jwt.interfaces.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import userservice.common.Constants;
import userservice.common.JWTUtils;
import userservice.service.UserDetailsServiceImpl;

import java.util.Date;

public class Test {
    static String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvcmFsamliZWtAZ21haWwuY29tIiwiZXhwIjoxNTM1MTQ3MDAwfQ.F38HL_5PeFDjh6qsf9P3Cki1KQmEr-4Nq24d8HtHV-ciIm_zu74caSznIXZ2mgZ8NNifKkgyzzQH1SXevEZNFw";
    static String token_=token.substring(7);
    public static void main(String[] args) {
        System.out.println(JWTUtils.getExpirationDate(token_));
    }
}
