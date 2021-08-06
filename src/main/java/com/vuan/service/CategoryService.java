package com.vuan.service;

import java.util.List;

import com.vuan.model.CategoryDTO;
import com.vuan.model.SearchCategoryDTO;

public interface CategoryService {
	void add(CategoryDTO categoryDTO);

	void update(CategoryDTO categoryDTO);

	void delete(int id);

	CategoryDTO get(int id);

	List<CategoryDTO> search(SearchCategoryDTO searchCategoryDTO);
	
	long countSearch(SearchCategoryDTO searchCategoryDTO);
	
	long countShowAll(SearchCategoryDTO searchCategoryDTO);
}
