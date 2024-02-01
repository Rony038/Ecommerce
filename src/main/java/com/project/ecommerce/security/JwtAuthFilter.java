package com.project.ecommerce.security;

import com.project.ecommerce.model.User;
import com.project.ecommerce.service.UserService;
import com.project.ecommerce.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    //variables

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");
        
        System.out.println(requestToken);
       
        String userName = null;
        String jwtToken = null;
        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            jwtToken = requestToken.substring(7);
            //System.out.println(jwtToken);

            try {
                userName = this.jwtTokenUtil.extractUsername(jwtToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // security
//            User userDetails = this.userDetailsService.loadUserByUsername(userName);
            System.out.println(userName);
              
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userName);
           
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, null);  //grant authorities is null here 

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }

        filterChain.doFilter(request, response);

    }
}
