package com.rami.devoxx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {
    Logger logger =  LoggerFactory.getLogger("RobotFilter.class");
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        //Must process request ?
        if (!Collections.list(request.getHeaderNames()).contains("x-robot-password")) {
            filterChain.doFilter(request,response);
            return;
        }

      //Authentication decision
       var password = request.getHeader("x-robot-password");

       if ("beep-boop".equals(password)){
          //Update security context
           var context = SecurityContextHolder.createEmptyContext();
           context.setAuthentication(new RobotAuthentication());
           SecurityContextHolder.setContext(context);
           logger.info("Login successful");
           //next
           filterChain.doFilter(request,response);
       } else {
           //OR Deny
           logger.error("Password incorrect");
           response.setStatus(HttpStatus.FORBIDDEN.value());
           response.getWriter().println("You are not Mr Robot");
       }
    }
}
