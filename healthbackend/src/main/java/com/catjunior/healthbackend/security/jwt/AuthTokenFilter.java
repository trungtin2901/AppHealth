package com.catjunior.healthbackend.security.jwt;

import com.catjunior.healthbackend.services.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.catjunior.healthbackend.security.SecurityConstants.SIGN_UP_URL;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    private static final String LOGIN_PATH = SIGN_UP_URL;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userService;

    private final AntPathRequestMatcher loginPathMatcher = new AntPathRequestMatcher(LOGIN_PATH);

    /**
     * Filters incoming requests and checks for the presence of a valid JWT token in the Authorization header.
     * If a valid token is found, it sets the user authentication in the SecurityContextHolder.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws IOException      If an I/O error occurs while processing the request.
     * @throws ServletException If a servlet-related error occurs while processing the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        if (loginPathMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = parseJwt(request);
            LOGGER.error("AuthTokenFilter | doFilterInternal | jwt: {}");

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, Collections.emptyList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            LOGGER.error("AuthTokenFilter | doFilterInternal | Cannot set user authentication: {}");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Parses the JWT token from the Authorization header of the HTTP request.
     *
     * @param request The HTTP request.
     * @return The parsed JWT token, or null if it cannot be parsed.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            return headerAuth.substring(AUTHORIZATION_HEADER_PREFIX.length());
        }

        return null;
    }
}
