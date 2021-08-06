package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CategoryDao;
import com.vuan.dao.ProductDao;
import com.vuan.entity.Category;
import com.vuan.entity.Product;
import com.vuan.model.CategoryDTO;
import com.vuan.model.ProductDTO;
import com.vuan.model.SearchProductDTO;
import com.vuan.service.ProductService;

@Service
@Transactional
public class ProductServiceIplm implements ProductService{
	@Autowired
	ProductDao productDao;
	
	@Autowired 
	CategoryDao categoryDao;
	
	@Override
	public void add(ProductDTO productDTO) {
		Product product=new Product();
		Category category=new Category();
		
		product.setName(productDTO.getName());
		product.setQuantity(productDTO.getQuantity());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		
		category = categoryDao.get(productDTO.getCategoryDTO().getId());
		
		product.setCategory(category);
		
		productDao.add(product);
		productDTO.setId(product.getId());
	}

	@Override
	public void update(ProductDTO productDTO) {
		Category category=new Category();
		
		Product product=productDao.get(productDTO.getId());
		if(product != null) {
			product.setId(productDTO.getId());
			product.setName(productDTO.getName());
			product.setQuantity(productDTO.getQuantity());
			product.setPrice(productDTO.getPrice());
			product.setDescription(productDTO.getDescription());
			product.setImage(productDTO.getImage());
			
			category.setId(productDTO.getCategoryDTO().getId());
			product.setCategory(category);
			
			productDao.update(product);
		}
		
	}

	@Override
	public void delete(int id) {
		Product product=productDao.get(id);
		if(product != null) {
			productDao.delete(product);
		}
	}

	@Override
	public ProductDTO get(int id) {
		Product product=productDao.get(id);
		return convert(product);
	}

	@Override
	public List<ProductDTO> search(SearchProductDTO searchProductDTO) {
		List<Product> listProducts=productDao.search(searchProductDTO);
		List<ProductDTO> listProductDTOs=new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			listProductDTOs.add(convert(product));
		}
		return listProductDTOs;
	}
	
	private ProductDTO convert(Product product) {
		ProductDTO productDTO=new ProductDTO();
		CategoryDTO categoryDTO=new CategoryDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setPrice(product.getPrice());
		productDTO.setImage(product.getImage());
		productDTO.setDescription(product.getDescription());
		
		categoryDTO.setId(product.getCategory().getId());
		categoryDTO.setName(product.getCategory().getName());
		
		productDTO.setCategoryDTO(categoryDTO);
		return productDTO;
	}

	@Override
	public long countGetAll(SearchProductDTO searchProductDTO) {
		Long count = productDao.countGetAll(searchProductDTO);
		return count;
	}

	@Override
	public long countSearch(SearchProductDTO searchProductDTO) {
		Long count = productDao.countSearch(searchProductDTO);
		return count;
	}

}
