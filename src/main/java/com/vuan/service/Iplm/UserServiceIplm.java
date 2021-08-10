package com.vuan.service.Iplm;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.UserDao;
import com.vuan.entity.User;
import com.vuan.model.SearchUserDTO;
import com.vuan.model.UserDTO;
import com.vuan.securiry.principal.UserPrincipal;
import com.vuan.service.UserService;
import com.vuan.utils.PasswordGenerator;

@Service
@Transactional
public class UserServiceIplm implements UserService {
	@Autowired
	UserDao userDao;
	
	@Override
	public void add(UserDTO userDTO) {
		User user=new User();
		user.setName(userDTO.getName());
		user.setAge(userDTO.getAge());
		user.setEnabled(userDTO.getEnabled());
		user.setRole(userDTO.getRole());
		user.setUsername(userDTO.getUsername());
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()) );
		user.setGender(userDTO.getGender());
		user.setAddress(userDTO.getAddress());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		user.setAvatar(userDTO.getAvatar());
		
		userDao.add(user);
		userDTO.setId(user.getId());
	}

	@Override
	public void update(UserDTO userDTO) {
		try {
			User user = userDao.get(userDTO.getId());
			if(user != null) {
				user.setId(userDTO.getId());
				user.setName(userDTO.getName());
				user.setAge(userDTO.getAge());
				user.setEnabled(userDTO.getEnabled());
				user.setRole(userDTO.getRole());
				user.setUsername(userDTO.getUsername());
				user.setGender(userDTO.getGender());
				user.setAddress(userDTO.getAddress());
				user.setPhone(userDTO.getPhone());
				user.setEmail(userDTO.getEmail());
				user.setAvatar(userDTO.getAvatar());
				
				userDao.update(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		User user = userDao.get(id);
		if(user != null) {
			userDao.delete(user);
		}	
	}

	@Override
	public UserDTO get(int id) {
		User user = userDao.get(id);
		return convert(user);
	}

	@Override
	public UserDTO getByUserName(String username) {
		User user = userDao.getByUserName(username);
		return convert(user);
	}
	
	@Override
	public Boolean exitsUsername(String username) {
		System.out.println("service user "+userDao.exitsUsername(username));
		return userDao.exitsUsername(username) ? true : false;
	}

	@Override
	public List<UserDTO> searchUser(SearchUserDTO searchUserDTO) {
		List<User> listUsers = userDao.searchUser(searchUserDTO);
		List<UserDTO> listUserDTOs = new ArrayList<UserDTO>();
		for (User user : listUsers) {
			listUserDTOs.add(convert(user));
		}
		return listUserDTOs;
	}
	
	@Override
	public List<UserDTO> searchAdmin(SearchUserDTO searchUserDTO) {
		List<User> listUsers = userDao.searchAdmin(searchUserDTO);
		List<UserDTO> listUserDTOs = new ArrayList<UserDTO>();
		for (User user : listUsers) {
			listUserDTOs.add(convert(user));
		}
		return listUserDTOs;
	}
	
	private UserDTO convert(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setAge(user.getAge());
		userDTO.setRole(user.getRole());
		userDTO.setUsername(user.getUsername());
		userDTO.setPassword(user.getPassword());
		userDTO.setGender(user.getGender());
		userDTO.setAddress(user.getAddress());
		userDTO.setEnabled(user.getEnabled());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		userDTO.setAvatar(user.getAvatar());
		return userDTO;
	}

	@Override
	public long countSearchUser(SearchUserDTO searchUserDTO) {
		long count=userDao.countSearchUser(searchUserDTO);
		return count;
	}
	
	@Override
	public long countSearchAdmin(SearchUserDTO searchUserDTO) {
		long count=userDao.countSearchAdmin(searchUserDTO);
		return count;
	}

	@Override
	public long countGetAll(SearchUserDTO searchUserDTO) {
		long count=userDao.countTotal(searchUserDTO);
		return count;
	}

}
