package com.vuan.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuan.model.CategoryDTO;
import com.vuan.model.CouponDTO;
import com.vuan.model.ProductDTO;
import com.vuan.model.ResponseDTO;
import com.vuan.model.ResponseErrorDTO;
import com.vuan.model.SearchCategoryDTO;
import com.vuan.model.SearchProductDTO;
import com.vuan.model.UserDTO;
import com.vuan.securiry.JwtTokenProvider;
import com.vuan.service.CategoryService;
import com.vuan.service.CouponService;
import com.vuan.service.ProductService;
import com.vuan.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class ClientController {
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@GetMapping("/category/{id}")
	public CategoryDTO getCate(@PathVariable(name = "id") int id) {
		System.out.println("id "+id);
		return categoryService.get(id);
	}
	
	@PostMapping("/category/search")
	public ResponseDTO<CategoryDTO> search(@RequestBody SearchCategoryDTO searchCategoryDTO){
		ResponseDTO<CategoryDTO> responseDTO = new ResponseDTO<CategoryDTO>();
		responseDTO.setData(categoryService.search(searchCategoryDTO));
		responseDTO.setRecordsFiltered(categoryService.countSearch(searchCategoryDTO));
		responseDTO.setRecordsTotal(categoryService.countShowAll(searchCategoryDTO));
		
		return responseDTO;
	}
	
	@GetMapping("/product/{id}")
	public ProductDTO get(@PathVariable(name = "id") int id) {
		return productService.get(id);
	}
	
	@PostMapping("/product/search")
	public ResponseDTO<ProductDTO> search(@RequestBody SearchProductDTO searchProductDTO){
		ResponseDTO<ProductDTO> responseDTO = new ResponseDTO<ProductDTO>();
		responseDTO.setData(productService.search(searchProductDTO));
		responseDTO.setRecordsFiltered(productService.countSearch(searchProductDTO));
		responseDTO.setRecordsTotal(productService.countGetAll(searchProductDTO));
		
		return responseDTO;
	}
	
	@GetMapping("/coupon/{code}")
	public CouponDTO getCoupon(@PathVariable(name = "code") String code) {
		System.out.println("code "+code); 
		return couponService.getByCode(code);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@ModelAttribute UserDTO userDTO) {
		if(userService.exitsUsername(userDTO.getUsername())) {
			return new ResponseEntity<>(new ResponseErrorDTO("The username exits! Please try again") ,HttpStatus.CONFLICT );
		}
		userDTO.setEnabled(true);
		userService.add(userDTO);
		return new ResponseEntity<>(new ResponseErrorDTO("Create user successfull!") ,HttpStatus.OK);
	}
	
}
