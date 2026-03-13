package org.example.vidyavriti;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.vidyavriti.Services.JwtService;
import org.example.vidyavriti.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String token = null;
        if(authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
        }
          if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              UserDetails user = userDetailsService.loadUserByUsername(userName);
              if (jwtService.validateToken(token, user)) {
                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

              }
          }

        filterChain.doFilter(request,response);

    }
}
