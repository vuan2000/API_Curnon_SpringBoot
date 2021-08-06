package com.vuan.service;

import java.util.List;


import com.vuan.model.CouponDTO;
import com.vuan.model.SearchCouponDTO;

public interface CouponService {
	
	void add(CouponDTO couponDTO);
	
	void edit(CouponDTO couponDTO);

	void delete(int id);
	
	CouponDTO get(int id);
	
	CouponDTO getByCode(String code);
	
	List<CouponDTO> showAll(SearchCouponDTO searchCouponDTO);
	
	long countShowAll(SearchCouponDTO searchCouponDTO);
}
