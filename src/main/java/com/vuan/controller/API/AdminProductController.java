package com.vuan.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuan.model.ProductDTO;
import com.vuan.model.ResponseDTO;
import com.vuan.model.SearchProductDTO;
import com.vuan.service.ProductService;
import com.vuan.service.StorageService;
import com.vuan.utils.FileStore;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
    private StorageService storageService;
	
	@PostMapping("/admin/product")
	public ProductDTO add(@ModelAttribute ProductDTO productDTO) {
		System.out.println("add product");
		System.out.println(productDTO);
		if(productDTO.getImageFile() != null) {
			String fileName = storageService.uploadFile(productDTO.getImageFile());
			productDTO.setImage(fileName);
		}
		productService.add(productDTO);
		return productDTO;
	}
	
	@DeleteMapping("/admin/product/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		ProductDTO productDTO = productService.get(id);
		if(productDTO.getImage() != null)
			storageService.deleteFile(productDTO.getImage());
		productService.delete(id);
	}
	
	@PutMapping("/admin/product/{id}")
	public void edit(@ModelAttribute ProductDTO productDTO) {
		System.out.println("update product");
		System.out.println(productDTO);
		ProductDTO preProductDTO = productService.get(productDTO.getId());
		if(productDTO.getImageFile() != null) {
			System.out.println("co anh");
			String fileName = storageService.uploadFile(productDTO.getImageFile());
			storageService.deleteFile(preProductDTO.getImage());
			productDTO.setImage(fileName);
		}else {
			System.out.println("khong co anh");
		}
		productService.update(productDTO);
	}
	
	@GetMapping("/admin/product/{id}")
	public ProductDTO get(@PathVariable(name = "id") int id) {
		return productService.get(id);
	}
	
	@PostMapping("/admin/product/search")
	public ResponseDTO<ProductDTO> search(@RequestBody SearchProductDTO searchProductDTO){
		ResponseDTO<ProductDTO> responseDTO = new ResponseDTO<ProductDTO>();
		responseDTO.setData(productService.search(searchProductDTO));
		responseDTO.setRecordsFiltered(productService.countSearch(searchProductDTO));
		responseDTO.setRecordsTotal(productService.countGetAll(searchProductDTO));
		
		return responseDTO;
	}

}
