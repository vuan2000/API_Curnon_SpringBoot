package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.BillProductDao;
import com.vuan.entity.Bill;
import com.vuan.entity.BillProduct;
import com.vuan.entity.Product;
import com.vuan.model.BillDTO;
import com.vuan.model.BillProductDTO;
import com.vuan.model.ProductDTO;
import com.vuan.model.SearchBillProductDTO;
import com.vuan.service.BillProductService;

@Transactional
@Service
public class BillProductServiceIplm implements BillProductService{
	
	@Autowired
	BillProductDao billProductDao;
	
	@Override
	public void add(BillProductDTO billProductDTO) {
		BillProduct billProduct=new BillProduct();
		
		billProduct.setQuantity(billProductDTO.getQuantity());
		billProduct.setUnitPrice(billProductDTO.getUnitPrice());
		
		Bill bill=new Bill();
		bill.setId(billProductDTO.getBillDTO().getId());
		
		Product product=new Product();
		product.setId(billProductDTO.getProductDTO().getId());
		
		billProduct.setBill(bill);
		billProduct.setProduct(product);
		
		billProductDao.add(billProduct);
		billProductDTO.setId(billProduct.getId());
	}

	@Override
	public void update(BillProductDTO billProductDTO) {
		BillProduct billProduct=new BillProduct();
		
		billProduct.setQuantity(billProductDTO.getQuantity());
		billProduct.setUnitPrice(billProductDTO.getUnitPrice());
		
		Bill bill=new Bill();
		bill.setId(billProductDTO.getBillDTO().getId());
		
		Product product=new Product();
		product.setId(billProductDTO.getProductDTO().getId());
		
		billProduct.setBill(bill);
		billProduct.setProduct(product);
		
		billProductDao.update(billProduct);
	}

	@Override
	public void delete(int id) {
		BillProduct billProduct=billProductDao.get(id);
		if(billProduct != null) {
			billProductDao.delete(billProduct);
		}
		
	}

	@Override
	public BillProductDTO get(int id) {
		BillProduct billProduct=billProductDao.get(id);
		return convert(billProduct);
	}

	@Override
	public List<BillProductDTO> getAll(SearchBillProductDTO searchBillProductDTO) {
		List<BillProduct> listBillProducts = billProductDao.getAll(searchBillProductDTO);
		List<BillProductDTO> listBillProductDTOs = new ArrayList<BillProductDTO>();
		for (BillProduct billProduct : listBillProducts) {
			listBillProductDTOs.add(convert(billProduct));
		}
		return listBillProductDTOs;
	}
	
	private BillProductDTO convert(BillProduct billProduct) {
		BillProductDTO billProductDTO=new BillProductDTO();
		
		billProductDTO.setId(billProduct.getId());
		billProductDTO.setQuantity(billProduct.getQuantity());
		billProductDTO.setUnitPrice(billProduct.getUnitPrice());
		
		BillDTO billDTO=new BillDTO();
		billDTO.setId(billProduct.getBill().getId());
		
		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(billProduct.getProduct().getId());
		productDTO.setName(billProduct.getProduct().getName());
		
		billProductDTO.setBillDTO(billDTO);
		billProductDTO.setProductDTO(productDTO);
		
		return billProductDTO;
	}

	@Override
	public List<BillProductDTO> searchByIdBill(int id) {
		List<BillProduct> listBillProducts = billProductDao.searchByIdBill(id);
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();
		for (BillProduct billProduct : listBillProducts) {
			billProductDTOs.add(convert(billProduct));
		}
		return billProductDTOs;
	}

	@Override
	public long countSearchByIdBill(int id) {
		long count = billProductDao.countSearcgByIdBill(id);
		return count;
	}
}
