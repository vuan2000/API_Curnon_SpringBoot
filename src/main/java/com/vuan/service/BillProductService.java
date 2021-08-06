package com.vuan.service;

import java.util.List;

import com.vuan.model.BillProductDTO;
import com.vuan.model.SearchBillProductDTO;


public interface BillProductService {
	void add(BillProductDTO billProductDTO);

	void update(BillProductDTO billProductDTO);

	void delete(int id);

	BillProductDTO get(int id);
	
	List<BillProductDTO> getAll(SearchBillProductDTO searchBillProductDTO);
	
	List<BillProductDTO> searchByIdBill(int id);
	
	long countSearchByIdBill(int id);
}
