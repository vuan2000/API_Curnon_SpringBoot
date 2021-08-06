package com.vuan.service;

import java.util.List;

import com.vuan.model.BillDTO;
import com.vuan.model.SearchBillDTO;

public interface BillService {
	void add(BillDTO billDTO);

	void update(BillDTO billDTO);

	void delete(int id);

	BillDTO get(int id);

	List<BillDTO> searchByNameBuyer(SearchBillDTO searchBillDTO);
	
	List<BillDTO> showAll(SearchBillDTO searchBillDTO);
	
	long countSearchByNameBuyer(SearchBillDTO searchBillDTO);
	
	long countShowAll(SearchBillDTO searchBillDTO);
}
