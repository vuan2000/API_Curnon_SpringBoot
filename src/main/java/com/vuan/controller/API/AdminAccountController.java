
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

import com.vuan.model.ResponseDTO;
import com.vuan.model.SearchUserDTO;
import com.vuan.model.UserDTO;
import com.vuan.service.StorageService;
import com.vuan.service.UserService;
import com.vuan.utils.FileStore;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminAccountController {
	@Autowired
	UserService userService;
	
	@Autowired
    private StorageService storageService;

	@PostMapping("/admin/account")
	public UserDTO add(@ModelAttribute UserDTO userDTO) {
		userDTO.setRole("ROLE_ADMIN");
		userDTO.setAvatar((FileStore.getFilePaths(userDTO.getImageFile())) );
		userService.add(userDTO);
		return userDTO;
	} 
	
	@PutMapping("/admin/account/{id}")
	public UserDTO update(@ModelAttribute UserDTO userDTO) {
		if(userDTO.getImageFile() != null) {
			String fileName = storageService.uploadFile(userDTO.getImageFile());
			userDTO.setAvatar(fileName);
		}
		userDTO.setEnabled(true);
		userService.update(userDTO);
		return userDTO;
	}
	
	@DeleteMapping("/admin/account/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		UserDTO userDTO = userService.get(id);
		if(userDTO.getAvatar() != null)
			storageService.deleteFile(userDTO.getAvatar());
		userService.delete(id);
	}
	
	@GetMapping("/admin/account/{id}")
	public UserDTO get(@PathVariable(name = "id") int id) {
		return userService.get(id);
	}

	@PostMapping("/admin/account/search")
	public ResponseDTO<UserDTO> search(@RequestBody SearchUserDTO searchUserDTO) {
		System.out.println("search admin");
		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
		responseDTO.setData(userService.searchAdmin(searchUserDTO));
		responseDTO.setRecordsFiltered(userService.countSearchAdmin(searchUserDTO));
		responseDTO.setRecordsTotal(userService.countGetAll(searchUserDTO));

		return responseDTO;
	}
}
