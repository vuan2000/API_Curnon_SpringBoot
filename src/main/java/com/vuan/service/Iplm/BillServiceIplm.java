package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.BillDao;
import com.vuan.entity.Bill;
import com.vuan.entity.User;
import com.vuan.model.BillDTO;
import com.vuan.model.SearchBillDTO;
import com.vuan.model.UserDTO;
import com.vuan.service.BillService;

@Service
@Transactional
public class BillServiceIplm implements BillService{
	@Autowired
	BillDao billDao;
	
	@Override
	public void add(BillDTO billDTO) {
		Bill bill = new Bill();
		
		bill.setBuyDate(billDTO.getBuyDate());
		bill.setBuyer(new User(billDTO.getUserDTO().getId()));
		bill.setStatus(billDTO.getStatus());
		bill.setPriceTotal(billDTO.getPriceTotal());
		bill.setPay(billDTO.getPay());
		bill.setCoupon(billDTO.getCoupon());
		bill.setCouponPresent(billDTO.getCouponPresent());

		billDao.add(bill);
		billDTO.setId(bill.getId()); 
	}

	@Override
	public void update(BillDTO billDTO) {
		Bill bill=billDao.get(billDTO.getId());
		if(bill != null) {
			bill.setStatus(billDTO.getStatus());
		}
			billDao.update(bill);
	}

	@Override
	public void delete(int id) {
		Bill bill=billDao.get(id);
		if(bill != null) {
			System.out.println("co bill id:"+id);
			billDao.delete(bill);
		}else {
			System.out.println("khong co bill id:"+id);
		}
		
	}

	@Override
	public BillDTO get(int id) {
		Bill bill=billDao.get(id);
		return convertDTO(bill);
	}

	@Override
	public List<BillDTO> searchByNameBuyer(SearchBillDTO searchBillDTO) {
		List<Bill> listBills = billDao.searchByNameBuyer(searchBillDTO);
		List<BillDTO> listBillDTOs =new ArrayList<BillDTO>();
		for (Bill bill : listBills) {
			listBillDTOs.add(convertDTO(bill));
		}
		return listBillDTOs;
	}
	
	@Override
	public List<BillDTO> showAll(SearchBillDTO searchBillDTO) {
		List<Bill> listBills = billDao.showAllBill(searchBillDTO);
		List<BillDTO> listBillDTOs =new ArrayList<BillDTO>();
		for (Bill bill : listBills) {
			listBillDTOs.add(convertDTO(bill));
		}
		return listBillDTOs;
	}
	
	private BillDTO convertDTO(Bill bill) {
		BillDTO billDTO = new BillDTO();
		billDTO.setId(bill.getId());
		billDTO.setBuyDate(bill.getBuyDate());
		billDTO.setPriceTotal(bill.getPriceTotal());
		billDTO.setCoupon(bill.getCoupon());
		billDTO.setCouponPresent(bill.getCouponPresent());
		billDTO.setPay(bill.getPay());
		billDTO.setStatus(bill.getStatus());

		UserDTO userDTO = new UserDTO();
		userDTO.setId(bill.getBuyer().getId());
		userDTO.setAddress(bill.getBuyer().getAddress());
		userDTO.setName(bill.getBuyer().getName());
		userDTO.setPhone(bill.getBuyer().getPhone());
		
		billDTO.setUserDTO(userDTO);

		return billDTO;
	}

	@Override
	public long countSearchByNameBuyer(SearchBillDTO searchBillDTO) {
		long count=billDao.countSearchByNameBuyer(searchBillDTO);
		return count;
	}

	@Override
	public long countShowAll(SearchBillDTO searchBillDTO) {
		long count=billDao.countShowAllBill(searchBillDTO);
		return count;
	}

}
