package userservice.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import userservice.common.Constants;
import userservice.common.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(Constants.AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(Constants.HEADER_PREFIX)) {
            final String token = authHeader.substring(7);
            if (JWTUtils.isTokenExpired(token)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            } else {
                final String login = JWTUtils.getSubject(token);
                if (login != null) {
                    final UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(login, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        filterChain.doFilter(request, response);
    }
}