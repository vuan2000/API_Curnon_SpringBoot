package com.vuan.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuan.exception.JwtCustomException;
import com.vuan.model.ResponseErrorDTO;
import com.vuan.model.TokenDTO;
import com.vuan.model.UserDTO;
import com.vuan.securiry.JwtTokenProvider;
import com.vuan.securiry.principal.UserPrincipal;
import com.vuan.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login-admin")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		// xac thuc user
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		if(userPrincipal.getRole().equals("ROLE_MEMBER")) {
			return new ResponseEntity<>(new ResponseErrorDTO("account not permission access") ,HttpStatus.FORBIDDEN);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.createToken(userDTO.getUsername());
		return ResponseEntity.ok(new TokenDTO(token, JwtTokenProvider.validityInMilliseconds));
	}
}
