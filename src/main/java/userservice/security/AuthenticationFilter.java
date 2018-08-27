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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import userservice.common.JWTUtils;
import userservice.model.UserEntity;
import userservice.service.UserDetailsServiceImpl;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static userservice.common.Constants.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String role;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity userEntity = objectMapper
                    .readValue(request.getInputStream(), UserEntity.class);
            role = userDetailsService.getRole(userEntity.getLogin());
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userEntity.getLogin(), userEntity.getPassword()
            );

            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new InternalAuthenticationServiceException("Error while sign in", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = JWTUtils.generateToken(((User) authResult.getPrincipal()).getUsername());
        response.addHeader(AUTH_HEADER, HEADER_PREFIX  + token);
        response.addHeader(ROLE, role);
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
