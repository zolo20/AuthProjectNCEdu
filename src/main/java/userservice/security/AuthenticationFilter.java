package userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import userservice.common.JWTUtils;
import userservice.model.UserEntity;
import userservice.repository.UserRepository;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static userservice.common.Constants.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String login;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity userEntity = objectMapper
                    .readValue(request.getInputStream(), UserEntity.class);
            login = userEntity.getEmail();
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userEntity.getEmail(), userEntity.getPassword()
            );
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new InternalAuthenticationServiceException("Error while sign in", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        UserEntity userEntity = userRepository.getUserByEmail(login);
        String token = JWTUtils.generateToken(userEntity.getUserID(), userEntity.getEmail(), userEntity.getName(), userEntity.getSurname(),userEntity.getRole());
        response.addHeader(AUTH_HEADER, HEADER_PREFIX  + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        if (failed instanceof BadCredentialsException || failed.getCause() instanceof BadCredentialsException ||
                failed instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
