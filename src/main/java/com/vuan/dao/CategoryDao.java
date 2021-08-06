package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Category;
import com.vuan.model.SearchCategoryDTO;


public interface CategoryDao {
	void add(Category category);

	void update(Category category);

	void delete(Category category);

	Category get(int id);

	List<Category> search(SearchCategoryDTO searchCategoryDTO);
	
	Long countSearch(SearchCategoryDTO searchCategoryDTO);
	
	Long countTotal(SearchCategoryDTO searchCategoryDTO);
}