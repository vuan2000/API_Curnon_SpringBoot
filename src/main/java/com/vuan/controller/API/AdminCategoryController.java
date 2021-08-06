package com.vuan.controller.API;

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

import com.vuan.model.CategoryDTO;
import com.vuan.model.ResponseDTO;
import com.vuan.model.SearchCategoryDTO;
import com.vuan.service.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminCategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/admin/category")
	public CategoryDTO add(@RequestBody CategoryDTO categoryDTO) {
		System.out.println("add category");
		System.out.println(categoryDTO);
		categoryService.add(categoryDTO);
		return categoryDTO;
	}

	@PutMapping(value = "/admin/category/{id}")
	public CategoryDTO update(@RequestBody CategoryDTO categoryDTO ,@PathVariable int id) {
		System.out.println("update");
		categoryDTO.setId(id);
		System.out.println(categoryDTO.getId()+categoryDTO.getName());
		categoryService.update(categoryDTO);
		return categoryDTO;
	}

	@DeleteMapping(value = "/admin/category/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		System.out.println("admin get category");
		categoryService.delete(id);
	}

	@GetMapping(value = "/admin/category/{id}")
	public CategoryDTO get(@PathVariable(name = "id") int id) {
		return categoryService.get(id);
	}

	@PostMapping(value = "/admin/category/search")
	public ResponseDTO<CategoryDTO> find(@RequestBody SearchCategoryDTO searchCategoryDTO) {
		ResponseDTO<CategoryDTO> responseDTO = new ResponseDTO<CategoryDTO>();
		responseDTO.setData(categoryService.search(searchCategoryDTO));
		responseDTO.setRecordsFiltered(categoryService.countSearch(searchCategoryDTO));
		responseDTO.setRecordsTotal(categoryService.countShowAll(searchCategoryDTO));
		return responseDTO;
	}
}