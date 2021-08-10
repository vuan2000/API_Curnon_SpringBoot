package com.vuan.controller.API;

import java.util.List;

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

import com.vuan.model.BillDTO;
import com.vuan.model.BillProductDTO;
import com.vuan.model.ResponseDTO;
import com.vuan.model.SearchBillDTO;
import com.vuan.service.BillProductService;
import com.vuan.service.BillService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminBillController {
	@Autowired
	private BillService billService;
	
	@Autowired
	private BillProductService billProductService;

	@GetMapping(value = "/admin/bill/{id}")
	public BillDTO get(@PathVariable(name = "id") int id) {
		return billService.get(id);
	}
	
	@DeleteMapping(value = "/admin/bill/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		billService.delete(id);
	}
	
	@PutMapping(value = "/admin/bill/{id}")
	public void update(@PathVariable(name = "id") int id ,@RequestBody BillDTO billDTO) {
		System.out.println(billDTO);
		BillDTO billDTO2 = billService.get(id);
		billDTO2.setStatus(billDTO.getStatus());
		System.out.println(billDTO2);
		billService.update(billDTO2);;
	}
	
	@GetMapping(value = "/admin/bill/detail/{id}")
	public List<BillProductDTO> getBillProduct(@PathVariable(name = "id") int id) {
		return billProductService.searchByIdBill(id);
	}

	@PostMapping("/admin/bill/search")
	public ResponseDTO<BillDTO> search(@RequestBody SearchBillDTO searchBillDTO){
		ResponseDTO<BillDTO> responseDTO = new ResponseDTO<BillDTO>();
		responseDTO.setData(billService.searchByNameBuyer(searchBillDTO));
		responseDTO.setRecordsFiltered(billService.countSearchByNameBuyer(searchBillDTO));
		responseDTO.setRecordsTotal(billService.countShowAll(searchBillDTO));
		
		return responseDTO;
	}
}