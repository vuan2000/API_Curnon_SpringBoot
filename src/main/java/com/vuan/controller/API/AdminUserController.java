
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
import com.vuan.service.UserService;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class AdminUserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/admin/user/update/{id}/{enabled}")
	public void update(@PathVariable(name = "id") int id ,@PathVariable(name = "enabled") boolean enabled) {
		System.out.println("boolean "+enabled);
		UserDTO userDTO = userService.get(id);
		userDTO.setEnabled(enabled);
		userService.update(userDTO);
	}
	
	@GetMapping("/admin/user/{id}")
	public UserDTO get(@PathVariable(name = "id") int id) {
		return userService.get(id);
	}

	@DeleteMapping("/admin/user/{id}")
	public void delete(@PathVariable(name = "id") int id) {
		userService.delete(id);
	}

	@PostMapping("/admin/user/search")
	public ResponseDTO<UserDTO> search(@RequestBody SearchUserDTO searchUserDTO) {
		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
		responseDTO.setData(userService.searchUser(searchUserDTO));
		responseDTO.setRecordsFiltered(userService.countSearchUser(searchUserDTO));
		responseDTO.setRecordsTotal(userService.countGetAll(searchUserDTO));

		return responseDTO;
	}
}
