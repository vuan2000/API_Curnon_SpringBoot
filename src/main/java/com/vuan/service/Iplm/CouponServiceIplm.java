package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vuan.dao.CouponDao;
import com.vuan.entity.Coupon;
import com.vuan.model.CouponDTO;
import com.vuan.model.SearchCouponDTO;
import com.vuan.service.CouponService;
import com.vuan.utils.DateTimeUtils;

@Service
public class CouponServiceIplm implements CouponService{
	@Autowired
	CouponDao couponDao;
	
	@Override
	public void add(CouponDTO couponDTO) {
		Coupon coupon=new Coupon();
		coupon.setCode(couponDTO.getCode());
		coupon.setPresent(couponDTO.getPresent());
		coupon.setExpiredDate(DateTimeUtils.parseDate(couponDTO.getExpiredDate(), DateTimeUtils.DD_MM_YYYY));
		
		couponDao.add(coupon);
	}

	@Override
	public void edit(CouponDTO couponDTO) {
		Coupon coupon=couponDao.get(couponDTO.getId());
		if(coupon != null) {
			coupon.setId(couponDTO.getId());
			coupon.setCode(couponDTO.getCode());
			coupon.setPresent(couponDTO.getPresent());
			coupon.setExpiredDate(DateTimeUtils.parseDate(couponDTO.getExpiredDate(), DateTimeUtils.DD_MM_YYYY));
			couponDao.update(coupon);
		}		
	}

	@Override
	public void delete(int id) {
		Coupon coupon=couponDao.get(id);
		if(coupon != null) {			
			couponDao.delete(coupon);
		}		
	}

	@Override
	public CouponDTO get(int id) {
		Coupon coupon=couponDao.get(id);
		return convert(coupon);
	}

	@Override
	public CouponDTO getByCode(String code) {
		Coupon coupon = couponDao.getByCode(code);
		return convert(coupon);
	}

	@Override
	public List<CouponDTO> showAll(SearchCouponDTO searchCouponDTO) {
		List<Coupon> listCoupons=couponDao.showAll(searchCouponDTO);
		List<CouponDTO> listCouponDTOs=new ArrayList<CouponDTO>();
		
		for (Coupon coupon : listCoupons) {
			listCouponDTOs.add(convert(coupon));
		}	
		return listCouponDTOs;
	}
	
	private CouponDTO convert(Coupon coupon) {
		CouponDTO couponDTO=new CouponDTO();
		couponDTO.setId(coupon.getId());
		couponDTO.setCode(coupon.getCode());
		couponDTO.setPresent(coupon.getPresent());
		couponDTO.setExpiredDate(DateTimeUtils.formatDate(coupon.getExpiredDate(), DateTimeUtils.DD_MM_YYYY));
		return couponDTO;
	}

	@Override
	public long countShowAll(SearchCouponDTO searchCouponDTO) {
		long count = couponDao.countShowAll(searchCouponDTO);
		return count;
	}

}
