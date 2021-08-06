package com.vuan.model;

import lombok.Data;

@Data
public class TokenDTO {
	private String accessToken;
	private Long expirationTime;
}
