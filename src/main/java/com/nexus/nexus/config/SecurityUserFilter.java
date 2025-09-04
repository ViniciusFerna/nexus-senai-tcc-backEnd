package com.nexus.nexus.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nexus.nexus.provider.JWTProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityUserFilter extends OncePerRequestFilter{

	@Autowired
	private JWTProvider jwtProvider;
	
		
		private String getTokenFromHeader(HttpServletRequest request) {
			String header = request.getHeader("Authorization");
			
			if (header == null || !header.startsWith("Bearer ")) {
				return null;
			}
			
			return header.replace("Bearer ", "").trim();		
			}
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws IOException, ServletException {
			String token = getTokenFromHeader(request);
			
			if(token == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			DecodedJWT decodedJWT = jwtProvider.validateToken(token);
			if(decodedJWT == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Inválido");
				return;
			}
			
			String role = decodedJWT.getClaim("roles").asString();
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					decodedJWT.getSubject(),
					null,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
				);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
			
		}	
	
}