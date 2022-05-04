package com.example.gradleinitial.filter;

import com.example.gradleinitial.model.UserToken;
import com.example.gradleinitial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
@Transactional
public class ApiKeyFilter extends OncePerRequestFilter {
    @Autowired
    UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(request.getRequestURI().equals("/api/user/login"))return true;
        if(request.getRequestURI().equals("/api/user/register"))return true;
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authrorization = request.getHeader("Authorization");
        if(authrorization!=null && authrorization.startsWith("Bearer ")){

            String token = authrorization.substring(7);
            log.info("serviceToken={}",token);

            UserToken userToken = userService.getUserTokenByToken(token);
            if(userToken != null) {
                boolean isExpire = userService.checkExpireToken(userToken);
                boolean isActive = userService.checkActiveToken(userToken);
                if(!isExpire && isActive) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

  

  
}
