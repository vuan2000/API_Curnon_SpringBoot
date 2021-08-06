package com.vuan.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProductDTO {
	private int id;
	private String name;
	private int quantity;
	private Long price;
	private String image;
	private String description;
	private CategoryDTO categoryDTO;
	
	//khong phai doi tuong cua ProductDTO ma la cua file Json
	@JsonIgnore
	private MultipartFile imageFile;
}
