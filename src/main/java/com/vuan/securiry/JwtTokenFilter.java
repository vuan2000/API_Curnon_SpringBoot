package com.vuan.securiry;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vuan.exception.JwtCustomException;



//kiem tra request cua user toi dich
//no lay Header Authorization ra và kiểm tra xem chuỗi JWT người dùng gửi lên có hợp lệ không
public class JwtTokenFilter extends OncePerRequestFilter {
	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(httpServletRequest);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (JwtCustomException ex) {
			System.out.println("co loi filter token");
			// this is very important, since it guarantees the user is not authenticated at
			// all
			SecurityContextHolder.clearContext();
			httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
