package com.vuan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TokenDTO {
	private String accessToken;
	private Long expirationTime;
	
	public TokenDTO(String accessToken, Long expirationTime) {
		super();
		this.accessToken = accessToken;
		this.expirationTime = expirationTime;
	}
	
}
