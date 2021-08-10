package com.vuan.securiry;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

//kiem tra request cua user toi dich
//no lay Header Authorization ra và kiểm tra xem chuỗi JWT người dùng gửi lên có hợp lệ không
public class JwtTokenFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// lay token o request
		String token = getToken(request);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {

				// lay user tim duoc trong database gan voi user he thong
//				Authentication auth = getAuthentication(token);
				// set user vao hej thong
//				SecurityContextHolder.getContext().setAuthentication(auth);
				
				String username = jwtTokenProvider.getUsernameFromToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null ,
						userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			logger.error("Can't set user authentication", e);	
		}
		
		filterChain.doFilter(request, response);
	}

	// xac thuc bang token
	private Authentication getAuthentication(String token) {
		String username = jwtTokenProvider.getUsernameFromToken(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	// lay token trong phan header sau 'Bearer '
	private String getToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			// lay gia tri sau vi tri Bearer
			return bearerToken.substring(7);
		}
		return null;
	}
}
