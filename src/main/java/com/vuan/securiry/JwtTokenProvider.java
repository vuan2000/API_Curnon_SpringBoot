package com.vuan.securiry;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.vuan.exception.JwtCustomException;
import com.vuan.model.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//tao component de khong phai tao bean nua
@Component
public class JwtTokenProvider {
	/**
	 * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key
	 * here. Ideally, in a microservices environment, this key would be kept on a
	 * config-server.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${security.jwt.token.secret-key:anfeed}")
	private String secretKey;

	public static long validityInMilliseconds = 86400000; // 1day

	@Autowired
	private UserDetailsService myUserDetails;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	// tao token tu user
	public String createToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		String accessToken = Jwts.builder()//
				.setClaims(claims)//
				// set thoi gian hien tai
				.setIssuedAt(now)//
				// set thoi gian hieu luc token
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)
				// dong lai
				.compact();
//		TokenDTO authenDTO = new TokenDTO();
//		authenDTO.setExpirationTime(validityInMilliseconds);
//		authenDTO.setAccessToken(accessToken);
		return accessToken;
	}

	// lay user tu jwt
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	// check token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("JWT token invalid", e);
//			throw new JwtCustomException("JWT token invalid", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MalformedJwtException e) {
			logger.error("JWT token invalid format", e);
//			throw new JwtCustomException("JWT token invalid format", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExpiredJwtException e) {
			logger.error("JWT token expired", e);
//			throw new JwtCustomException("JWT token expired", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// loi khong ho tro token day
		catch (UnsupportedJwtException e) {
			logger.error("Unsupport JWT token", e);
//			throw new JwtCustomException("Unsupport JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// loi co ki tu trong
		catch (IllegalArgumentException e) {
			logger.error("JWT token claims string empty", e);
//			throw new JwtCustomException("JWT token claims string empty", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return false;
	}
}