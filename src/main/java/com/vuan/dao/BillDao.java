package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Bill;
import com.vuan.model.SearchBillDTO;

public interface BillDao {
	void add(Bill bill);

	void update(Bill bill);

	void delete(Bill bill);

	Bill get(int id);

	List<Bill> searchByNameBuyer(SearchBillDTO searchBillDTO);
	
	List<Bill> showAllBill(SearchBillDTO searchBillDTO);
	
	long countSearchByNameBuyer(SearchBillDTO searchBillDTO);
	
	long countShowAllBill(SearchBillDTO searchBillDTO);
}
