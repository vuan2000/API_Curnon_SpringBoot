package com.vuan.dao;

import java.util.List;

import com.vuan.entity.BillProduct;
import com.vuan.model.SearchBillProductDTO;

public interface BillProductDao {
	void add(BillProduct billProduct);

	void update(BillProduct billProduct);

	void delete(BillProduct billProduct);

	BillProduct get(int id);
	
	List<BillProduct> searchByIdBill(int id);
	
	List<BillProduct> getAll(SearchBillProductDTO searchBillProductDTO);
	
	long countShowAll(SearchBillProductDTO searchBillProductDTO);
	
	long countSearcgByIdBill(int id);
}
