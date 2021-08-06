package com.vuan.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuan.model.CategoryDTO;
import com.vuan.model.CouponDTO;
import com.vuan.model.ResponseDTO;
import com.vuan.model.SearchCategoryDTO;
import com.vuan.model.SearchCouponDTO;
import com.vuan.service.CouponService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminCouponController {
	@Autowired
	private CouponService couponService;

	@PostMapping("/admin/coupon")
	public CouponDTO add(@RequestBody CouponDTO couponDTO) {
		couponService.add(couponDTO);
		return couponDTO;
	}

	@PutMapping(value = "/admin/coupon/{id}")
	public CouponDTO update(@RequestBody CouponDTO couponDTO ,@PathVariable int id) {
		couponDTO.setId(id);
		couponService.edit(couponDTO);
		return couponDTO;
	}

	@DeleteMapping(value = "/admin/coupon/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		couponService.delete(id);
	}

	@GetMapping(value = "/admin/coupon/{id}")
	public CouponDTO get(@PathVariable(name = "id") int id) {
		return couponService.get(id);
	}

	@PostMapping(value = "/admin/coupon/search")
	public ResponseDTO<CouponDTO> find(@RequestBody SearchCouponDTO searchCouponDTO) {
		ResponseDTO<CouponDTO> responseDTO = new ResponseDTO<CouponDTO>();
		responseDTO.setData(couponService.showAll(searchCouponDTO));
		responseDTO.setRecordsFiltered(couponService.countShowAll(searchCouponDTO));
		responseDTO.setRecordsTotal(couponService.countShowAll(searchCouponDTO));
		return responseDTO;
	}
}