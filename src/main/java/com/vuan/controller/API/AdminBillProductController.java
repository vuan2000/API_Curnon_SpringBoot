package com.vuan.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuan.model.BillProductDTO;
import com.vuan.service.BillProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminBillProductController {
	@Autowired
	private BillProductService billProductService;

	@GetMapping(value = "/admin/bill-product/{id}")
	public BillProductDTO get(@PathVariable(name = "id") int id) {
		BillProductDTO billProductDTO = billProductService.get(id);	
		return billProductDTO;
	}

}