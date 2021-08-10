package com.vuan.service;

import java.util.List;

import com.vuan.model.SearchUserDTO;
import com.vuan.model.UserDTO;

public interface UserService {
	void add(UserDTO userDTO);

	void update(UserDTO userDTO);

	void delete(int id);

	UserDTO get(int id);

	UserDTO getByUserName(String username);
	
	Boolean exitsUsername(String username);

	List<UserDTO> searchUser(SearchUserDTO searchUserDTO);
	
	List<UserDTO> searchAdmin(SearchUserDTO searchUserDTO);
	
	long countSearchUser(SearchUserDTO searchUserDTO);
	
	long countSearchAdmin(SearchUserDTO searchUserDTO);
	
	long countGetAll(SearchUserDTO searchUserDTO);
}
