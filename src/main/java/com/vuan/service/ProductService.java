package com.vuan.service;

import java.util.List;

import com.vuan.model.ProductDTO;
import com.vuan.model.SearchProductDTO;

public interface ProductService {
	void add(ProductDTO productDTO);

	void update(ProductDTO productDTO);

	void delete(int id);

	ProductDTO get(int id);

	List<ProductDTO> search(SearchProductDTO searchProductDTO);
	
	long countSearch(SearchProductDTO searchProductDTO);
	
	long countGetAll(SearchProductDTO searchProductDTO);
	
	
}
