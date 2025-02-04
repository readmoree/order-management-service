package com.readmoree.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		
		// 1. Check authorization header from incoming request
		String authHeader=request.getHeader("Authorization");
		System.out.println(authHeader);

		 if (path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs")) {
	            filterChain.doFilter(request, response);
	            return;
	        }
		
		System.out.println("jwt filter: "+ authHeader);
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			System.out.println("token invalid");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        JwtUtils jwtUtils = new JwtUtils();
        System.out.println("in jwtfilter "+token);
        Claims claims = jwtUtils.validateJwtToken(token);
        String role =null;
        

        if (!jwtUtils.validateToken(token)) {
        	System.out.println("token not valid");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }
       
     
        if (claims != null) {
         role = claims.get("role", String.class); // Assuming the role is in a claim named "role"
            Integer customerId = claims.get("customerId", Integer.class);
            
            System.out.println("customerId:  "+customerId);
            System.out.println("Role:  "+role);
            
            
            // Make the customerId available in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(customerId, null, null)
            );
            
            if (role != null) {
                String roleWithPrefix = "ROLE_" + role.toUpperCase(); // Ensure "ROLE_ADMIN" or "ROLE_CUSTOMER"

                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(customerId, null, AuthorityUtils.createAuthorityList(roleWithPrefix))
                );
            } else {
                response.setStatus(403);  // Forbidden if role is not admin
                response.getWriter().write("Access Denied: You do not have ADMIN role");
                return;
            }
        } else {
            response.setStatus(401);  // Unauthorized if the token is invalid
            response.getWriter().write("Invalid Token");
            return;
        }
        

		//continue with remaining filter chain.
		filterChain.doFilter(request, response);

	}

}
