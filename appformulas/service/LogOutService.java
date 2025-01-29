package org.example.appformulas.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.appformulas.essence.repository.TokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private final ObjectMapper mapper;
    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        response.setContentType("application/json");
        ObjectNode json = mapper.createObjectNode();


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            json.put("timeStamp", String.valueOf(LocalDateTime.now()));
            json.put("message", "Log out error.");
            json.put("status", String.valueOf(HttpStatus.FORBIDDEN));
            json.put("statusCode", HttpStatus.FORBIDDEN.value());
            mapper.writeValue(response.getWriter(), json);
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        json.put("timeStamp", String.valueOf(LocalDateTime.now()));
        json.put("message", "User logged out.");
        json.put("status", String.valueOf(HttpStatus.OK).substring(4));
        json.put("statusCode", HttpStatus.OK.value());
        mapper.writeValue(response.getWriter(), json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
