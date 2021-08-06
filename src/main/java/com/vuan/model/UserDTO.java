package com.vuan.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDTO {
	private int id;
	private String name;
	private String age;
	private String role;
	private Boolean enabled;
	private String username;
	private String password;
	private String address;
	private String gender;
	private String phone;
	private String email;
	private String avatar;
	
	@JsonIgnore
	private MultipartFile imageFile;
}
