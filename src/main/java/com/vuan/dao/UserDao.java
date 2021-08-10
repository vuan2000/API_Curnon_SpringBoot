package com.vuan.dao;

import java.util.List;

import com.vuan.entity.User;
import com.vuan.model.SearchUserDTO;


public interface UserDao {
	void add(User user);

	void update(User user);

	void delete(User user);

	User get(int id);

	User getByUserName(String username);
	
	Boolean exitsUsername(String username);

	List<User> searchUser(SearchUserDTO searchUserDTO);
	
	List<User> searchAdmin(SearchUserDTO searchUserDTO);
	
	Long countSearchUser(SearchUserDTO searchUserDTO);
	
	Long countSearchAdmin(SearchUserDTO searchUserDTO);
	
	Long countTotal(SearchUserDTO searchUserDTO);
}