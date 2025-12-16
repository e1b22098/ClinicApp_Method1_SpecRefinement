package com.clinic.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                       AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        logger.error("=== AUTHENTICATION FAILURE ===");
        logger.error("Username: {}", username);
        logger.error("Error message: {}", exception.getMessage());
        logger.error("Exception class: {}", exception.getClass().getName());
        logger.error("Exception details: ", exception);
        
        response.sendRedirect("/login?error=true");
    }
}

