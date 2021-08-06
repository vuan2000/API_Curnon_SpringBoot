package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CategoryDao;
import com.vuan.entity.Category;
import com.vuan.model.CategoryDTO;
import com.vuan.model.SearchCategoryDTO;
import com.vuan.service.CategoryService;

@Service
@Transactional
public class CategoryServiceIplm implements CategoryService{
	@Autowired
	CategoryDao categoryDao;
	
	@Override
	public void add(CategoryDTO categoryDTO) {
		Category category=new Category();
		category.setName(categoryDTO.getName());
		
		categoryDao.add(category);
		categoryDTO.setId(category.getId());
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryDao.get(categoryDTO.getId());
		if(category != null) {
			category.setId(categoryDTO.getId());
			category.setName(categoryDTO.getName());
			
			categoryDao.update(category);
		}
	}

	@Override
	public void delete(int id) {
		Category category = categoryDao.get(id);
		if(category != null) {
			categoryDao.delete(category);
		}	
	}

	@Override
	public CategoryDTO get(int id) {
		Category category = categoryDao.get(id);
		System.out.println(category);
		return convert(category);
	}

	@Override
	public List<CategoryDTO> search(SearchCategoryDTO searchCategoryDTO) {
		System.out.println("dao search category"); 
		System.out.println(searchCategoryDTO.getKeyword());
		List<Category> listCategorys=categoryDao.search(searchCategoryDTO);
		List<CategoryDTO> listCategoryDTOs=new ArrayList<CategoryDTO>();
		for (Category category : listCategorys) {
			listCategoryDTOs.add(convert(category));
		}
		return listCategoryDTOs;
	}
	
	public CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO=new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		return categoryDTO;
	}
	
	@Override
	public long countSearch(SearchCategoryDTO searchCategoryDTO) {
		long count=categoryDao.countSearch(searchCategoryDTO);
		return count;
	}


	@Override
	public long countShowAll(SearchCategoryDTO searchCategoryDTO) {
		long count=categoryDao.countTotal(searchCategoryDTO);
		return count;
	}
}
