package com.vuan.controller.API;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuan.exception.JwtCustomException;
import com.vuan.model.BillDTO;
import com.vuan.model.BillProductDTO;
import com.vuan.model.ProductDTO;
import com.vuan.model.TokenDTO;
import com.vuan.model.UserDTO;
import com.vuan.model.UserPrincipal;
import com.vuan.securiry.JwtTokenProvider;
import com.vuan.service.BillProductService;
import com.vuan.service.BillService;
import com.vuan.service.ProductService;
import com.vuan.service.UserService;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class MemberController {
	@Autowired
	BillService billService;
	
	@Autowired
	BillProductService billProductService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/member/bill")
	public BillDTO addBill(@ModelAttribute BillDTO billDTO) {
		billService.add(billDTO);
		return billDTO;
	}
	
	@GetMapping(value = "/member/me")
	private UserDTO me() {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userService.get(currentUser.getId());
	}
	
	@PostMapping("/login")
	public TokenDTO login(@RequestBody UserDTO userDTO) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
			return jwtTokenProvider.createToken(userDTO.getUsername());
		} catch (AuthenticationException e) {
			throw new JwtCustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@PostMapping("/member/order")
	public String addBill(@RequestBody Map<String, Object> order) throws NoSuchFieldException, SecurityException {
		//convert LinkedHashMap to BillDTO
		ObjectMapper mapper = new ObjectMapper();
		BillDTO billDTO = mapper.convertValue(order.get("billDTO"), BillDTO.class);
		
		
		billService.add(billDTO);
		List<Object> list = (List<Object>) order.get("billProductDTO");
		for (Object object : list) {
			System.out.println(object);
			
			//convert LinkedHashMap to BillProductDTO
			BillProductDTO billProductDTO = mapper.convertValue(object, BillProductDTO.class);
			billProductDTO.setBillDTO(billDTO);
			
			billProductService.add(billProductDTO);
			
			ProductDTO productDTO = productService.get(billProductDTO.getProductDTO().getId());
			productDTO.setQuantity(productDTO.getQuantity() - billProductDTO.getQuantity());
			
			productService.update(productDTO);
		}
		return "order done";
	}
}	
