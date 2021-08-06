package com.vuan.model;

import lombok.Data;

@Data
public class ResponseErrorDTO {
	private int code;
	private String message;

	public ResponseErrorDTO(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResponseErrorDTO() {

	}
}
