package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Product;
import com.vuan.model.SearchProductDTO;

public interface ProductDao {
	void add(Product product);

	void update(Product product);

	void delete(Product product);

	Product get(int id);

	List<Product> search(SearchProductDTO searchProductDTO);
	
	Long countSearch(SearchProductDTO searchProductDTO);
	
	Long countGetAll(SearchProductDTO searchProductDTO);
}
