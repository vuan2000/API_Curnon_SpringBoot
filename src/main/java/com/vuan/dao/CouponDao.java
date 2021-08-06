package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Coupon;
import com.vuan.model.SearchBillProductDTO;
import com.vuan.model.SearchCouponDTO;

public interface CouponDao {
	void add(Coupon coupon);
	
	void update(Coupon coupon);

	void delete(Coupon coupon);
	
	Coupon get(int id);
	
	Coupon getByCode(String code);
	
	List<Coupon> showAll(SearchCouponDTO searchCouponDTO);
	
	long countShowAll(SearchCouponDTO searchCouponDTO);
}
