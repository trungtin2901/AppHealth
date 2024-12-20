package com.catjunior.healthbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * JsonAuthenticationFilter is responsible for handling authentication requests with JSON payload.
 * It extracts the username and password from the JSON payload and performs authentication.
 */
public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructs a new JsonAuthenticationFilter with the specified requiresAuthenticationRequestMatcher.
     *
     * @param requiresAuthenticationRequestMatcher the RequestMatcher that determines if authentication is required
     */
    public JsonAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    /**
     * Attempts authentication by extracting the username and password from the JSON payload.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return an Authentication object if the authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (HttpMethod.POST.matches(request.getMethod())) {
            try (InputStream requestBody = request.getInputStream()) {
                Map<String, String> authenticationData = objectMapper.readValue(requestBody, Map.class);
                String username = authenticationData.get("username");
                String password = authenticationData.get("password");

                if (username == null || password == null) {
                    throw new IllegalArgumentException("Username or password is missing");
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
                return getAuthenticationManager().authenticate(authentication);
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method");
        }
    }
}
